package name.dido.dbcompare.generator.field;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Random;

public class TimestampGenerator extends ParameterGenerator {

	TimestampGenerator(int position) {
		super(position);
	}

	@Override
	public void generateParameter(PreparedStatement insertStatement)
			throws SQLException {
		long milis = new Random().nextInt();
		Timestamp value = new Timestamp(milis);
		insertStatement.setTimestamp(position, value);
	}

}
