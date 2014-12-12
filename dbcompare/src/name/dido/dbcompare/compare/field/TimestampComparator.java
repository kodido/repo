package name.dido.dbcompare.compare.field;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TimestampComparator extends FieldComparator {

	TimestampComparator(String fieldName, int columnIndex) {
		super(fieldName, columnIndex);
	}

	@Override
	public boolean compare(ResultSet rs1, ResultSet rs2) throws SQLException {
		Timestamp value1 = rs1.getTimestamp(columnName);
		Timestamp value2 = rs2.getTimestamp(columnName);
		return rs1.wasNull() && rs2.wasNull() || !rs1.wasNull() && !rs2.wasNull() && value1.equals(value2);
	}	
}
