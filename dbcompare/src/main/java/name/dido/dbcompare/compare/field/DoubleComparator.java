package name.dido.dbcompare.compare.field;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoubleComparator extends FieldComparator {

	DoubleComparator(String fieldName, int columnIndex) {
		super(fieldName, columnIndex);
	}

	@Override
	public boolean compare(ResultSet rs1, ResultSet rs2) throws SQLException {
		double d1 = rs1.getDouble(columnName);
		double d2 = rs2.getDouble(columnName);		
		return rs1.wasNull() && rs2.wasNull() || d1 == d2;
	}	

}
