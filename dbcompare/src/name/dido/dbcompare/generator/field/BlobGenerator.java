package name.dido.dbcompare.generator.field;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class BlobGenerator extends ParameterGenerator {

	int MAX_LENGTH = 1024*50;	
	
	BlobGenerator(int position) {
		super(position);
	}

	@Override
	public void generateParameter(PreparedStatement insertStatement)
			throws SQLException {
		int length = new Random().nextInt(MAX_LENGTH);
		byte[] buffer = new byte[(int) length];
		new Random().nextBytes(buffer);
		insertStatement.setBinaryStream(position, new ByteArrayInputStream(buffer), buffer.length);
	}

}
