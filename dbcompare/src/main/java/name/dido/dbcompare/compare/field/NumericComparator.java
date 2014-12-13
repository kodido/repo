package name.dido.dbcompare.compare.field;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NumericComparator extends FieldComparator {

	NumericComparator(String fieldName, int columnIndex) {
		super(fieldName, columnIndex);
	}

	@Override
	public boolean compare(ResultSet rs1, ResultSet rs2) throws SQLException {
		BigDecimal bd1 = rs1.getBigDecimal(columnName);
		BigDecimal bd2 = rs2.getBigDecimal(columnName);		
		return rs1.wasNull() && rs2.wasNull() || bd1.equals(bd2);
	}	

}
