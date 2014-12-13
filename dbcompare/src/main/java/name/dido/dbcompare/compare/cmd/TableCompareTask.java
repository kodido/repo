package name.dido.dbcompare.compare.cmd;

import java.sql.Connection;
import java.util.concurrent.Callable;

import name.dido.dbcompare.common.ConnectionData;
import name.dido.dbcompare.common.DBConnector;
import name.dido.dbcompare.compare.ITableComparator;
import name.dido.dbcompare.compare.TableCompareResult;

public class TableCompareTask implements Callable<TableCompareResult> {

	private String tableName;
	private String orderByClause;
	private ConnectionData sourceConnectionData;
	private ConnectionData targetConnectionData;
	private ITableComparator tableComparator;

	public TableCompareTask(ITableComparator tableComparator, String tableName, String orderByClause,
			ConnectionData sourceConnectionData,
			ConnectionData targetConnectionData) {
		this.tableComparator = tableComparator;
		this.tableName = tableName;
		this.orderByClause = orderByClause;
		this.sourceConnectionData = sourceConnectionData;
		this.targetConnectionData = targetConnectionData;
	}


	@Override
	public TableCompareResult call() throws Exception {
		Connection sourceConnection = DBConnector.connect(sourceConnectionData);
		Connection targetConnection = DBConnector.connect(targetConnectionData);
		TableCompareResult result = null;
		try {
		    result = tableComparator.compareTable(tableName, orderByClause,
				sourceConnection, targetConnection);
		} finally {
			if (sourceConnection != null) 
				sourceConnection.close();
			if (targetConnection != null)
				targetConnection.close();
		}
		return result;
	}

}
