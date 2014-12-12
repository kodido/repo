package name.dido.dbcompare.generator.field;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IntegerGenerator extends ParameterGenerator {

	IntegerGenerator(int position) {
		super(position);
	}

	@Override
	public void generateParameter(PreparedStatement insertStatement)
			throws SQLException {
		int value = new SecureRandom().nextInt();
		insertStatement.setInt(position, value);
	}

}
