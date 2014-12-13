package name.dido.dbcompare.tablelistprovider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import name.dido.dbcompare.common.LogUtils;

public class SelectTableListProvider implements ITableListProvider {

	private static Logger getLogger() {
		return LogUtils.LOGGER;
	}
	
	
	private String tableName;
	private String columnName;
	private Connection connection;

	public SelectTableListProvider(Connection connection, String tableName, String columnName) {
		this.connection = connection;
		this.tableName = tableName;
		this.columnName = columnName;				
	}

	@Override
	public List<String> getTableNames() throws SQLException {
		Statement stmt = connection.createStatement();   
		String sql = buildSQL();
		ResultSet rs = stmt.executeQuery(sql);
		List<String> result = new ArrayList<String>();
		while (rs.next()) {
			String tablename = rs.getString(1);
			if (tablename != null)
			    result.add(rs.getString(1));
			else 
				getLogger().warning("null table name found in the execution of " + sql);				
		}
		rs.close();
		stmt.close();
		return result;
	}

	private String buildSQL() {
		return "SELECT DISTINCT " + columnName + " FROM " + tableName;
	}

}
