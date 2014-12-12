package name.dido.dbcompare.generator.field;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class DoubleGenerator extends ParameterGenerator {

	DoubleGenerator(int position) {
		super(position);
	}

	@Override
	public void generateParameter(PreparedStatement insertStatement)
			throws SQLException {
		double value = new Random().nextDouble();	
		insertStatement.setDouble(position, value);	
	}

}
