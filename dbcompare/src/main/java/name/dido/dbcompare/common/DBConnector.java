package name.dido.dbcompare.common;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {
	
	public static Connection connect(String driver, String url, String user, String passwd) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return getConnection(driver, url, user, passwd);
	}
	
	public static Connection getConnection(String driverClass, String url, String user, String passwd) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {

	    Connection conn = null;
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", user);
	    connectionProps.put("password", passwd);

	    Driver driver = (Driver) Class.forName(driverClass).newInstance();
		DriverManager.registerDriver(driver);
	    conn = DriverManager.getConnection(url, user, passwd); 	    
	    return conn;
	}
	
	public static void closeConnection(Connection connection) {
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public static Connection connect(ConnectionData connectionData) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {		
		return connect(connectionData.getDriver(), connectionData.getUrl(), connectionData.getUser(), connectionData.getPasswd());
	}	
}
