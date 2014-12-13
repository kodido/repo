package name.dido.dbcompare.common.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ColumnMetaData {
	
	private String name;
	private int sqlType;
	private int precision;
	
	public ColumnMetaData(String name, int sqlType, int precision) {
		super();
		this.name = name;
		this.sqlType = sqlType;
		this.precision = precision;
	}

	public int getPrecision() {
		return precision;
	}

	public String getName() {
		return name;
	}

	public int getSqlType() {
		return sqlType;
	}
	
	public static List<ColumnMetaData> getColumnsMetadata(String tableName,
			Connection connection) throws SQLException {
		List<ColumnMetaData> result = new ArrayList<ColumnMetaData>();
		DatabaseMetaData metadata = connection.getMetaData();
        ResultSet resultSet = metadata.getColumns(null, null, tableName, null);
        while (resultSet.next()) {
        	result.add(new ColumnMetaData(resultSet.getString("COLUMN_NAME"), resultSet.getInt("DATA_TYPE"), 
        			resultSet.getInt("COLUMN_SIZE")));        
        }
        return result;
	}	
	
	
}
