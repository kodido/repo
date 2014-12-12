package name.dido.dbcompare.generator.field;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class BinaryGenerator extends ParameterGenerator {	
	
	BinaryGenerator(int position) {
		super(position);
	}

	private static final int SIZE = 128;
	
	@Override
	public void generateParameter(PreparedStatement insertStatement)
			throws SQLException {
		byte[] value = new byte[SIZE];
	    new Random().nextBytes(value);
		insertStatement.setBytes(position, value);	
	}

}
