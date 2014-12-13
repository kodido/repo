package name.dido.dbcompare.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableUtils {

	public static boolean isEmpty(Connection connection, String tableName) throws SQLException {
		Statement stmt = connection.createStatement();   				
		ResultSet rs = stmt.executeQuery(buildSQL(tableName));
		rs.next();
		boolean result = rs.getInt(1) == 0; 		
		rs.close();
		stmt.close();
		return result;
	}

	private static String buildSQL(String tableName) {
		return "SELECT COUNT(*) FROM " + tableName;		
	}

}
