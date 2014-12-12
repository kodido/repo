package name.dido.dbcompare.generator.field;

import java.io.CharArrayReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class ClobGenerator extends ParameterGenerator {

	int MAX_LENGTH = 1024*10;
	
	ClobGenerator(int position) {
		super(position);
	}

	@Override
	public void generateParameter(PreparedStatement insertStatement)
			throws SQLException {
		Random rand = new Random();
		int length = rand.nextInt(MAX_LENGTH);
		char[] buffer = new char[(int) length];
		for (int i = 0; i < buffer.length; ++i)
			buffer[i] = (char)(rand.nextInt(26) + 'a');
		insertStatement.setCharacterStream(position, new CharArrayReader(buffer), (int)length);
	}

}
