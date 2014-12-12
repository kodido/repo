package name.dido.dbcompare.compare.field;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerComparator extends FieldComparator {

	IntegerComparator(String fieldName, int columnIndex) {
		super(fieldName, columnIndex);
	}

	@Override
	public boolean compare(ResultSet rs1, ResultSet rs2) throws SQLException {
		int i1 = rs1.getInt(columnName);
		int i2 = rs2.getInt(columnName);	
		return rs1.wasNull() && rs2.wasNull() || i1 == i2;
	}	

}
