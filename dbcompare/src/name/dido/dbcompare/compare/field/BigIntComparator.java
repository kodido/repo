package name.dido.dbcompare.compare.field;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BigIntComparator extends FieldComparator {

	BigIntComparator(String fieldName, int columnIndex) {
		super(fieldName, columnIndex);
	}

	@Override
	public boolean compare(ResultSet rs1, ResultSet rs2) throws SQLException {
		long l1 = rs1.getLong(columnName);
		long l2 = rs2.getLong(columnName);	
		return rs1.wasNull() && rs2.wasNull() || l1 == l2;
	}	

}
