package name.dido.dbcompare.generator.field;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class ParameterGenerator {
	
	protected int position;

	ParameterGenerator(int position) {
		this.position = position;
	}

	public abstract void generateParameter(PreparedStatement insertStatement) throws SQLException;

}
