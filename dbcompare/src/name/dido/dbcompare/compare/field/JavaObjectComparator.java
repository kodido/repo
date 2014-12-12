package name.dido.dbcompare.compare.field;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JavaObjectComparator extends FieldComparator {

	JavaObjectComparator(String fieldName, int columnIndex) {
		super(fieldName, columnIndex);
	}

	@Override
	public boolean compare(ResultSet rs1, ResultSet rs2) throws SQLException {
		Object value1 = rs1.getObject(columnName);
		Object value2 = rs2.getObject(columnName);		
		return rs1.wasNull() && rs2.wasNull() || value1.equals(value2);
	}	

}
