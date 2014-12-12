package name.dido.dbcompare.compare.field;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CharComparator extends FieldComparator {

	
	CharComparator(String fieldName, int columnIndex) {
		super(fieldName, columnIndex);
	}

	@Override
	public boolean compare(ResultSet rs1, ResultSet rs2) throws SQLException {
		String s1 = rs1.getString(columnName);
		String s2 = rs2.getString(columnName);
		return rs1.wasNull() && rs2.wasNull() || !rs1.wasNull() && !rs2.wasNull() && s1.equals(s2);
	}

}
