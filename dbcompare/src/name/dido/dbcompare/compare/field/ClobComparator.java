package name.dido.dbcompare.compare.field;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class ClobComparator extends FieldComparator {

	ClobComparator(String fieldName, int columnIndex) {
		super(fieldName, columnIndex);
	}

	public static final int BUF_SIZE = 256*1024;	
	char[] charBuf1 = new char[BUF_SIZE];
	char[] charBuf2 = new char[BUF_SIZE];	
	
	@Override
	public boolean compare(ResultSet rs1, ResultSet rs2) throws SQLException,
			IOException {
		Clob value1 = rs1.getClob(columnName);
		Clob value2 = rs2.getClob(columnName);
		try {
			if (rs1.wasNull() && rs2.wasNull())
				return true;
			if (value1.length() != value2.length())
				return false;

			Reader reader1 = value1.getCharacterStream();
			Reader reader2 = value2.getCharacterStream();

			try {
				while ((reader1.read(charBuf1)) != -1
						&& (reader2.read(charBuf2)) != -1) {
					if (!Arrays.equals(charBuf1, charBuf2))
						return false;
				}
			} finally {
				reader1.close();
				reader2.close();
			}
		} finally {			
			/*
			 * TODO: do we need to call Clob::free() here? Applies also to other spots
			if (value1 != null)
			    value1.free();
			if (value2 != null)
			    value2.free();
			*/			
		}

		return true;		
	}

}
