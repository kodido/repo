package name.dido.dbcompare.compare.field;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class BinaryComparator extends FieldComparator {

	BinaryComparator(String fieldName, int columnIndex) {
		super(fieldName, columnIndex);
	}

	@Override
	public boolean compare(ResultSet rs1, ResultSet rs2) throws SQLException {
		byte[] value1 = rs1.getBytes(columnName);
		byte[] value2 = rs2.getBytes(columnName);
		return rs1.wasNull() && rs2.wasNull() || Arrays.equals(value1, value2);
	}	

}
