package name.dido.dbcompare.compare.field;

import java.sql.SQLException;
import java.sql.Types;

public class FieldComparatorFactory {
	public static FieldComparatorFactory instance = null;
	
	private FieldComparatorFactory() {		
	}
	
	public static FieldComparatorFactory getInstance() {
		if (instance == null) {
			instance = new FieldComparatorFactory();
		}
		return instance;
	}

	public FieldComparator getFieldComparator(int sqlType, String columnName, int columnIndex) throws SQLException {
		FieldComparator result = null;
		switch (sqlType) {
		case Types.CHAR:
			result = new CharComparator(columnName, columnIndex);
			break;
		case Types.VARCHAR:
			result = new CharComparator(columnName, columnIndex);
			break;
		case Types.LONGVARCHAR:
			result = new CharComparator(columnName, columnIndex);
			break;
		case Types.NVARCHAR:
			result = new CharComparator(columnName, columnIndex);
			break;			
		case Types.TINYINT:
			result = new TinyIntComparator(columnName, columnIndex);
			break;
		case Types.SMALLINT:
			result = new SmallIntComparator(columnName, columnIndex);
			break;
		case Types.INTEGER:
			result = new IntegerComparator(columnName, columnIndex);
			break;
		case Types.BIGINT:
			result = new BigIntComparator(columnName, columnIndex);
			break;
		case Types.REAL:
			result = new RealComparator(columnName, columnIndex);
			break;
		case Types.FLOAT:
			result = new DoubleComparator(columnName, columnIndex);
			break;
		case Types.DOUBLE:
			result = new DoubleComparator(columnName, columnIndex);
			break;
		case Types.DECIMAL:
			result = new NumericComparator(columnName, columnIndex);
			break;
		case Types.NUMERIC:
			result = new NumericComparator(columnName, columnIndex);
			break;
		case Types.BIT:
			result = new BitComparator(columnName, columnIndex);
			break;
		case Types.BINARY:
			result = new BinaryComparator(columnName, columnIndex);
			break;
		case Types.VARBINARY:
			result = new BinaryComparator(columnName, columnIndex);
			break;
		case Types.LONGVARBINARY:
			result = new BinaryComparator(columnName, columnIndex);
			break;
		case Types.DATE:
			result = new DateComparator(columnName, columnIndex);
			break;
		case Types.TIME:
			result = new TimeComparator(columnName, columnIndex);
			break;
		case Types.TIMESTAMP:
			result = new TimestampComparator(columnName, columnIndex);
			break;
		case Types.BLOB:
			result = new BlobComparator(columnName, columnIndex);
			break;
		case Types.CLOB:
			result = new ClobComparator(columnName, columnIndex);
			break;
		case Types.NCLOB:
			result = new ClobComparator(columnName, columnIndex);
			break;			
		case Types.ARRAY:
			result = new ArrayComparator(columnName, columnIndex);
			break;
		case Types.STRUCT:
			result = new JavaObjectComparator(columnName, columnIndex);
			break;
		case Types.JAVA_OBJECT:
			result = new JavaObjectComparator(columnName, columnIndex);
			break;
		default:
			throw (new SQLException("SQL type " + sqlType + " not supported\n"
					+ "(table " + ", column " + columnName + ")"));
		}
		return result;
		
	}
}
