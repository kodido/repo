package name.dido.dbcompare.generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import name.dido.dbcompare.common.ConnectionData;
import name.dido.dbcompare.common.DBConnector;
import name.dido.dbcompare.common.metadata.ColumnMetaData;
import name.dido.dbcompare.generator.field.ParameterGenerator;
import name.dido.dbcompare.generator.field.ParameterGeneratorFactory;

public class TableDataGenerator {
	
	private static final long BATCH_SIZE = 100;
	private static final long COMMIT_COUNT = 2000;
	
	public long generateData(ConnectionData connectionData, long numberOfRows,
			String insertSQL, ParameterGenerator[] generators)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		long generated = 0;
		Connection connection = DBConnector.connect(connectionData.getDriver(),
				connectionData.getUrl(), connectionData.getUser(),
				connectionData.getPasswd());
		PreparedStatement insertStatement = null;
		try {
			insertStatement = connection.prepareStatement(insertSQL);

			while (generated < numberOfRows) {
				for (int i = 0; i < generators.length; i++) {
					if (generators[i] != null)
						generators[i].generateParameter(insertStatement);
				}

				insertStatement.addBatch();

				if (generated % BATCH_SIZE == 0) {
					insertStatement.executeBatch();
				}

				if (generated % COMMIT_COUNT == 0) {
					connection.commit();
				}

				generated++;
			}
		} finally {	
			insertStatement.close();
			connection.commit();
			connection.close();
		}
		return generated;
	}
	
	public long generateData(String tableName, ConnectionData connectionData,
			long numberOfRows) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {	
		Connection connection = DBConnector.connect(connectionData.getDriver(),
				connectionData.getUrl(), connectionData.getUser(),
				connectionData.getPasswd());
		String insertSQL = null;
		ParameterGenerator[] generators = null;
		try {
			List<ColumnMetaData> columnsMetadata = ColumnMetaData.getColumnsMetadata(
					tableName, connection);
			insertSQL = generateInsertSQL(tableName, columnsMetadata);
			generators = createParmeterGenerators(columnsMetadata);
		} finally {
			connection.close();
		}
		return generateData(connectionData, numberOfRows, insertSQL, generators);
	}
	
	public static String generateInsertSQL(String tableName,
			List<ColumnMetaData> columnsMetadata) {				
		StringBuffer sbInsert = new StringBuffer("INSERT INTO ").append(tableName).append(" (").append(columnsMetadata.get(0).getName());
	    StringBuffer sbValues = new StringBuffer("VALUES (?");
		for (int i = 1; i < columnsMetadata.size(); ++i) {
			ColumnMetaData metadata = columnsMetadata.get(i);
			sbInsert.append(", ").append(metadata.getName());
			sbValues.append(", ?");
		}
		return sbInsert.append(") ").append(sbValues).append(")").toString();
	}
	
	public static ParameterGenerator[] createParmeterGenerators(
			List<ColumnMetaData> columnsMetaData) throws SQLException {
		ParameterGenerator[] result = new ParameterGenerator[columnsMetaData.size()];
		
		for (int i = 0; i < columnsMetaData.size(); i++) {
			result[i] = ParameterGeneratorFactory.getInstance().getParameterGenerator(
					columnsMetaData.get(i).getSqlType(),
					columnsMetaData.get(i).getPrecision(), 
					i + 1);
		}		
		
		return  result;
	}
	
}


