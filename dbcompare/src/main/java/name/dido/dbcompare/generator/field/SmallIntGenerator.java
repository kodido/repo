package name.dido.dbcompare.generator.field;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class SmallIntGenerator extends ParameterGenerator {

	SmallIntGenerator(int position) {
		super(position);
	}

	@Override
	public void generateParameter(PreparedStatement insertStatement)
			throws SQLException {
		short value = (short) new Random().nextInt(Short.MAX_VALUE + 1);
		insertStatement.setShort(position, value);
	}

}
