package name.dido.dbcompare.compare.field;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class TimeComparator extends FieldComparator {

	TimeComparator(String fieldName, int columnIndex) {
		super(fieldName, columnIndex);
	}

	@Override
	public boolean compare(ResultSet rs1, ResultSet rs2) throws SQLException {
		Time value1 = rs1.getTime(columnName);
		Time value2 = rs2.getTime(columnName);
		return rs1.wasNull() && rs2.wasNull() || value1.equals(value2);
	}	
}
