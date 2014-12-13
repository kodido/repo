package name.dido.dbcompare.specific.oracle;

import static name.dido.dbcompare.common.ConnectProperties.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import name.dido.dbcompare.common.ConnectionData;
import name.dido.dbcompare.common.DBConnector;
import name.dido.dbcompare.common.LogUtils;
import name.dido.dbcompare.common.Utils;
import name.dido.dbcompare.common.metadata.ColumnMetaData;
import name.dido.dbcompare.tablelistprovider.ITableListProvider;
import name.dido.dbcompare.tablelistprovider.TableListProviderFactory;

public class OracleLinkedServerCompare {

	private Connection connection;
	private String dbLinkName;

	public OracleLinkedServerCompare(Connection sourceConnection, String dbLinkName) {
		this.connection = sourceConnection;
		this.dbLinkName = dbLinkName;
	}

	private static Logger getLogger() {
		return LogUtils.LOGGER;
	}	
	
	public static void main(String[] args) {
		Connection sourceConnection = null;
		try {
			Properties props = Utils.loadProperties("connect.properties");

			ConnectionData srcConnectionData = new ConnectionData(
					props.getProperty(propSourceDriver),
					props.getProperty(propSourceUrl),
					props.getProperty(propSourceUser),
					props.getProperty(propSourcePasswd));
			getLogger().info("Connecting to source DB");
			sourceConnection = DBConnector.connect(srcConnectionData);						
			
			String dbLinkName = props.getProperty(propOracleDBLinkName);
			
			ITableListProvider tableListProvider = TableListProviderFactory.createTableListProvider(
					sourceConnection, props);			
			
			OracleLinkedServerCompare compare = new OracleLinkedServerCompare(sourceConnection, dbLinkName);			
			compare.compareWithLinkedServer(tableListProvider.getTableNames(), dbLinkName, sourceConnection);
			getLogger().info("All tables compared succesfully");
		} catch (Exception e) {
			getLogger().log(Level.SEVERE,
					"Exception caught: " + e.getMessage(), e);
			System.exit(1);
		} finally {
			DBConnector.closeConnection(sourceConnection);
		}
	}


	private void compareWithLinkedServer(List<String> tableNames, String linkedServer, Connection connection) throws SQLException {
		for (String tableName : tableNames) {
			compareWithLinkedServer(tableName);
		}
	}

	private void compareWithLinkedServer(String tableName) throws SQLException {
        String sql = buildCompareSQL(tableName, dbLinkName, connection);
		PreparedStatement stmt = connection.prepareStatement(sql);
		getLogger().info(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			getLogger().info(rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3));
		}
		stmt.close();
	}

	private String buildCompareSQL(String tableName, String linkedServer, Connection connection) throws SQLException {
		String linkedServerSuffix = "@" + linkedServer;
		StringBuffer selectStatement = buildSelectStatement(tableName, connection);
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT '").append(tableName).append("', 's', count(*) from (\n");
		sb.append("(").append(selectStatement).append(linkedServerSuffix).append(")\n");
		sb.append("minus\n");
		sb.append("(").append(selectStatement).append(")\n");
		sb.append(") union all \n");
		sb.append("SELECT '").append(tableName).append("', 't', count(*) from (\n");
		sb.append("(").append(selectStatement).append(")\n");
		sb.append("minus\n");
		sb.append("(").append(selectStatement).append(linkedServerSuffix).append(")\n");		
		sb.append(")");		
		return sb.toString();
	}

	private StringBuffer buildSelectStatement(String tableName, Connection connection) throws SQLException {
		List<ColumnMetaData> columnsMetadata = ColumnMetaData.getColumnsMetadata(
				tableName, connection);
		StringBuffer sb = new StringBuffer("SELECT ");
		String delim = "";
		for (ColumnMetaData columnMetaData : columnsMetadata) {
			if (!isLOB(columnMetaData.getSqlType())) {
				sb.append(delim).append(columnMetaData.getName());
				delim = ", ";
			} else {
				getLogger().warning("Lob column " + columnMetaData.getName() + " found in table " + tableName);
			}
		}
		
		sb.append(" FROM ").append(tableName);
		return sb;
	}

	private boolean isLOB(int sqlType) {
		return sqlType == Types.NCLOB || sqlType == Types.BLOB || sqlType == Types.CLOB || sqlType == Types.OTHER;
	}

}
