package name.dido.dbcompare.compare.field;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RealComparator extends FieldComparator {

	RealComparator(String fieldName, int columnIndex) {
		super(fieldName, columnIndex);
	}

	@Override
	public boolean compare(ResultSet rs1, ResultSet rs2) throws SQLException {
		float f1 = rs1.getFloat(columnName);
		float f2 = rs2.getFloat(columnName);		
		return rs1.wasNull() && rs2.wasNull() || f1 == f2;
	}	

}
