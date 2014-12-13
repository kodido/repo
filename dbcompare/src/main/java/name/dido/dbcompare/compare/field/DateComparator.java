package name.dido.dbcompare.compare.field;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DateComparator extends FieldComparator {

	DateComparator(String fieldName, int columnIndex) {
		super(fieldName, columnIndex);
	}

	@Override
	public boolean compare(ResultSet rs1, ResultSet rs2) throws SQLException {
		Date value1 = rs1.getDate(columnName);
		Date value2 = rs2.getDate(columnName);
		return rs1.wasNull() && rs2.wasNull() || value1.equals(value2);
	}	

}
