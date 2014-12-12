package name.dido.dbcompare.generator.field;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NumericGenerator extends ParameterGenerator {

	NumericGenerator(int position) {
		super(position);
	}

	@Override
	public void generateParameter(PreparedStatement insertStatement)
			throws SQLException {
		BigDecimal value = new BigDecimal(Math.random());
		insertStatement.setBigDecimal(position, value);	
	}

}
