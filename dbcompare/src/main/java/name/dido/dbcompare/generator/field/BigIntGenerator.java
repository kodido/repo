package name.dido.dbcompare.generator.field;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class BigIntGenerator extends ParameterGenerator {

	BigIntGenerator(int position) {
		super(position);
	}

	@Override
	public void generateParameter(PreparedStatement insertStatement)
			throws SQLException {
		long value = new Random().nextLong();	
		insertStatement.setLong(position, value);		
	}

}
