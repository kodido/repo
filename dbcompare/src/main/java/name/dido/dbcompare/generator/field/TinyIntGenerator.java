package name.dido.dbcompare.generator.field;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class TinyIntGenerator extends ParameterGenerator {

	TinyIntGenerator(int position) {
		super(position);
	}

	@Override
	public void generateParameter(PreparedStatement insertStatement)
			throws SQLException {
		byte value = (byte) new Random().nextInt(Byte.MAX_VALUE + 1);		
		insertStatement.setByte(position, value);
	}

}
