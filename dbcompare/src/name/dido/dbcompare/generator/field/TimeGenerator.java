package name.dido.dbcompare.generator.field;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Random;

public class TimeGenerator extends ParameterGenerator {

	TimeGenerator(int position) {
		super(position);
	}

	@Override
	public void generateParameter(PreparedStatement insertStatement)
			throws SQLException {
		long milis = new Random().nextInt();
		Time value = new Time(milis);
		insertStatement.setTime(position, value);
	}

}
