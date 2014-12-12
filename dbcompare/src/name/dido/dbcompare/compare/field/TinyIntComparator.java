package name.dido.dbcompare.compare.field;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TinyIntComparator extends FieldComparator {

	TinyIntComparator(String fieldName, int columnIndex) {
		super(fieldName, columnIndex);
	}

	@Override
	public boolean compare(ResultSet rs1, ResultSet rs2) throws SQLException {
		byte b1 = rs1.getByte(columnName);
		byte b2 = rs2.getByte(columnName);	
		return rs1.wasNull() && rs2.wasNull() || b1 == b2;
	}	
	

}
