package name.dido.dbcompare.compare.field;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class ArrayComparator extends FieldComparator {

	ArrayComparator(String fieldName, int columnIndex) {
		super(fieldName, columnIndex);
	}

	@Override
	public boolean compare(ResultSet rs1, ResultSet rs2) throws SQLException {
		Array value1 = rs1.getArray(columnName);
		Array value2 = rs2.getArray(columnName);		
		return rs1.wasNull() && rs2.wasNull() || Arrays.equals((Object[]) value1.getArray(), (Object[]) value2.getArray());
	}	

}
