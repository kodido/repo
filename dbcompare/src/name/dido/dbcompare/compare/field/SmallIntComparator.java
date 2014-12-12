package name.dido.dbcompare.compare.field;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SmallIntComparator extends FieldComparator {

	SmallIntComparator(String fieldName, int columnIndex) {
		super(fieldName, columnIndex);
	}

	@Override
	public boolean compare(ResultSet rs1, ResultSet rs2) throws SQLException {
		short s1 = rs1.getShort(columnName);
		short s2 = rs2.getShort(columnName);	
		return rs1.wasNull() && rs2.wasNull() || s1 == s2;
	}	

}
