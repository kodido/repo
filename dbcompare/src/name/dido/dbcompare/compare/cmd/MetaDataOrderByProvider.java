package name.dido.dbcompare.compare.cmd;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MetaDataOrderByProvider implements IOrderByClauseProvider {

	private Map<String, String> overrideMap;
	private Connection connection;

	public MetaDataOrderByProvider(Connection connection, Map<String, String> overrideMap) {
		this.connection = connection;
		this.overrideMap = overrideMap;
	}

	@Override
	public String getOrderByClause(String tableName) throws MetadataOrderByProviderException, SQLException {
		String result = overrideMap.get(tableName);
		if (result == null)
			result = getOrderByClauseFromMetadata(tableName);
		if (result == null)
			throw new MetadataOrderByProviderException("Could not get order by clause for table " + tableName);
		return result;
	}

	private String getOrderByClauseFromMetadata(String tableName) throws SQLException {
		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet rs = metaData.getPrimaryKeys(null, null, tableName);
		List<String> primaryKeys = new ArrayList<String>();
		while (rs.next()) {
			primaryKeys.add(rs.getString("COLUMN_NAME"));
		}
		
		String result = null;
		if (!primaryKeys.isEmpty())
		{
		  result = buildOrderByClause(primaryKeys);
		}
		return result;
	}

	private String buildOrderByClause(List<String> columnNames) {
		String result;
		StringBuffer sb = new StringBuffer("ORDER BY ");		  
		  String delim = "";
		  for (String primaryKey : columnNames) {
		      sb.append(delim).append(primaryKey);
		      delim = ",";
		  }
		  result = sb.toString();
		return result;
	}

}
