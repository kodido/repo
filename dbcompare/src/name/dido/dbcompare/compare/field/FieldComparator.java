package name.dido.dbcompare.compare.field;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class FieldComparator {
	
	protected String columnName;
	public String getFieldName() {
		return columnName;
	}

	protected int columnNumber;

	protected FieldComparator(String columnName, int columnNumber) {
		this.columnName = columnName;
		this.columnNumber = columnNumber;
	}	
	
	public abstract boolean compare(ResultSet rs1, ResultSet rs2) throws SQLException, IOException;
}
