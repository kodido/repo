package name.dido.dbcompare.compare;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Logger;

import name.dido.dbcompare.common.LogUtils;
import name.dido.dbcompare.compare.field.FieldComparator;
import name.dido.dbcompare.compare.field.FieldComparatorFactory;

public class TraversalTableComparator implements ITableComparator {

	private static synchronized Logger getLogger() {
		return LogUtils.LOGGER;
	}		
	
	public TableCompareResult compareTable(String tableName, String orderBy, Connection sourceConnection,
			Connection targetConnection) {
		TableCompareResult result = new TableCompareResult(tableName);
	
		try {
			compareTableInternal(tableName, orderBy, sourceConnection,
					targetConnection, result);
		} catch (Throwable e) {
			result.setErrorMessage(e.getMessage());
		}
		
	    return result;
	}

	private void compareTableInternal(String tableName, String orderBy,
			Connection sourceConnection, Connection targetConnection,
			TableCompareResult result) throws SQLException, IOException {
		String selectSQL = "SELECT * FROM " + tableName + " " + orderBy;		
		long startTime = System.currentTimeMillis();		

		PreparedStatement sourceStmt = sourceConnection.prepareStatement(selectSQL);			
		ResultSet srcRS = sourceStmt.executeQuery();		
		int srcNOC = srcRS.getMetaData().getColumnCount();
						
		PreparedStatement targetStmt = targetConnection.prepareStatement(selectSQL);   				
		ResultSet tgtRS = targetStmt.executeQuery();
		int tgtNOC = tgtRS.getMetaData().getColumnCount();
												
		// TODO: compare metadata 
		if (srcNOC != tgtNOC) {
			result.getDifferences().add(result.new TableDifference("Number of columns is different"));			
		}		
		
	    /* TODO: check that the number of rows is the same */		
					
		/* compare data */		
		FieldComparator[] fieldComparers = createFieldComparerList(srcRS.getMetaData());		
		long rowCount = 0;		
		while (srcRS.next() && tgtRS.next()) { 			
			// TODO: compare row 
			for (int i = 1; i <= srcNOC; i++)
				// fieldComparers are 0-indexed 
				if (!fieldComparers[i - 1].compare(srcRS, tgtRS)) {
					String message = "Value of field " + fieldComparers[i - 1].getFieldName() + " of table " + tableName + " differs";
					result.getDifferences().add(result.new TableDifference(message));
				}			
			rowCount++;			
			if (rowCount % 100000 == 0)
				getLogger().info(tableName + ": compared so far: " + rowCount + " rows in " + (System.currentTimeMillis() - startTime) + " msec");									
		}
		srcRS.close();
		sourceStmt.close();
		tgtRS.close();
		targetStmt.close();

		/* TODO: check that number of processed rows equals total number of rows */
		/*
		if (rowCount != srcNOC) {
			String message = "Number of processed rows is " + rowCount + " which is different from number of rows " + srcNOC;
			CAT.errorT(LOC, message);								
			result.add(new TableDifference(message));
			return result;
		}
		*/				
	    		
		result.setTotalRows(rowCount);
		result.setTotalTimeMsec((System.currentTimeMillis() - startTime));
	}

	private FieldComparator[] createFieldComparerList(ResultSetMetaData metadata) throws SQLException {
		int columnCount = metadata.getColumnCount();
		FieldComparator[] result = new FieldComparator[columnCount];
		
		for (int i = 0; i < columnCount; i++) {
			result[i] = FieldComparatorFactory.getInstance().getFieldComparator(metadata.getColumnType(i + 1), 
					metadata.getColumnName(i + 1), i + 1);
		}		
		
		return  result;
	}

}
