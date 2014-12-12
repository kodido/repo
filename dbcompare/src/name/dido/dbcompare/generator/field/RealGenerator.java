package name.dido.dbcompare.generator.field;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class RealGenerator extends ParameterGenerator {

	RealGenerator(int position) {
		super(position);
	}

	@Override
	public void generateParameter(PreparedStatement insertStatement)
			throws SQLException {
		float value = new Random().nextFloat();	
		insertStatement.setFloat(position, value);	
	}

}
