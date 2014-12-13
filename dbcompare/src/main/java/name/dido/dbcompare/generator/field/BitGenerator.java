package name.dido.dbcompare.generator.field;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class BitGenerator extends ParameterGenerator {

	BitGenerator(int position) {
		super(position);
	}

	@Override
	public void generateParameter(PreparedStatement insertStatement)
			throws SQLException {
		boolean value = new Random().nextBoolean();	
		insertStatement.setBoolean(position, value);	
	}

}
