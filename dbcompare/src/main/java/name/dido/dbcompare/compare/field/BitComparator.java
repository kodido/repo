package name.dido.dbcompare.compare.field;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BitComparator extends FieldComparator {

	BitComparator(String fieldName, int columnIndex) {
		super(fieldName, columnIndex);
	}

	@Override
	public boolean compare(ResultSet rs1, ResultSet rs2) throws SQLException {
		boolean value1 = rs1.getBoolean(columnName);
		boolean value2 = rs2.getBoolean(columnName);		
		return rs1.wasNull() && rs2.wasNull() || value1 == value2;
	}	
}
