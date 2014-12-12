package name.dido.dbcompare.generator.field;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CharGenerator extends ParameterGenerator {

	private int precision;

	CharGenerator(int position, int precision) {
		super(position);
		this.precision = precision;
	}

	@Override
	public void generateParameter(PreparedStatement insertStatement) throws SQLException {
		String value = new BigInteger(130, new SecureRandom()).toString(32);
		if (value.length() >= precision)
			value = value.substring(0, precision);
		insertStatement.setString(position, value);
	}

}
