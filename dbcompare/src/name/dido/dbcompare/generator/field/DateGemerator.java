package name.dido.dbcompare.generator.field;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class DateGemerator extends ParameterGenerator {

	DateGemerator(int position) {
		super(position);
	}

	@Override
	public void generateParameter(PreparedStatement insertStatement)
			throws SQLException {
		long milis = new Random().nextInt();
		Date value = new Date(milis);
		insertStatement.setDate(position, value);
	}

}
