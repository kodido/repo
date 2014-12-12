package name.dido.dbcompare.compare.field;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class BlobComparator extends FieldComparator {

	BlobComparator(String fieldName, int columnIndex) {
		super(fieldName, columnIndex);
	}

	public static final int BUF_SIZE = 256*1024;
	
	byte[] byteBuf1 = new byte[BUF_SIZE];
	byte[] byteBuf2 = new byte[BUF_SIZE];	
	
	@Override
	public boolean compare(ResultSet rs1, ResultSet rs2) throws SQLException, IOException {
		Blob value1 = rs1.getBlob(columnName);
		Blob value2 = rs2.getBlob(columnName);

		if (rs1.wasNull() && rs2.wasNull())
			return true;
		if (rs1.wasNull() || rs2.wasNull())
			return false;
		if (value1.length() != value2.length())
			return false;

		InputStream is1 = value1.getBinaryStream();
		InputStream is2 = value2.getBinaryStream();
		try {

			while ((is1.read(byteBuf1)) != -1 && (is2.read(byteBuf2)) != -1) {
				if (!Arrays.equals(byteBuf1, byteBuf2))
					return false;
			}
		} finally {
			is1.close();
			is2.close();
		}
		
		return true;				
	}

}
