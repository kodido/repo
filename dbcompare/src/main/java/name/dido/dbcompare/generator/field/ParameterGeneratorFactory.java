package name.dido.dbcompare.generator.field;

import java.sql.SQLException;
import java.sql.Types;


public class ParameterGeneratorFactory {

	private static ParameterGeneratorFactory instance = null;
	
	private ParameterGeneratorFactory() {
		
	}
	
	public static ParameterGeneratorFactory getInstance() {
		if (instance == null) {
			instance = new ParameterGeneratorFactory();
		}
		return instance;
	}

	public ParameterGenerator getParameterGenerator(int parameterType, int precision, int position) throws SQLException {
		ParameterGenerator result = null;
		switch (parameterType) {
		case Types.CHAR: 
		case Types.VARCHAR:
		case Types.LONGNVARCHAR:
		case -1:
			result = new CharGenerator(position, precision);
			break;
		case Types.INTEGER:
			result = new IntegerGenerator(position);
			break;
		case Types.TINYINT:
			result = new TinyIntGenerator(position);
			break;		
		case Types.SMALLINT:
			result = new SmallIntGenerator(position);
			break;
		case Types.BIGINT:
			result = new BigIntGenerator(position);
			break;
		case Types.REAL:
			result = new RealGenerator(position);
			break;
		case Types.FLOAT:
			result = new DoubleGenerator(position);
			break;
		case Types.DOUBLE:
			result = new DoubleGenerator(position);
			break;
		case Types.DECIMAL:
			result = new NumericGenerator(position);
			break;
		case Types.NUMERIC:
			result = new NumericGenerator(position);
			break;
		case Types.BIT:
			result = new BitGenerator(position);
			break;
		case Types.BINARY:
		case Types.VARBINARY:
		case Types.LONGVARBINARY:			
			result = new BinaryGenerator(position);
			break;
		case Types.DATE:
			result = new DateGemerator(position);
			break;
		case Types.TIME:
			result = new TimeGenerator(position);
			break;
		case Types.TIMESTAMP:
			result = new TimestampGenerator(position);
			break;			
		case Types.BLOB:
			result = new BlobGenerator(position);
			break;	
		case Types.CLOB:
			result = new ClobGenerator(position);
			break;	
		/*
        TODO: find out how to handle these types or document some limitaion of the tool
		case Types.ARRAY:
			result = new ArrayComparator(fieldName, columnIndex);
			break;
		case Types.STRUCT:
			result = new JavaObjectComparator(fieldName, columnIndex);
			break;
		case Types.JAVA_OBJECT:
			result = new JavaObjectComparator(fieldName, columnIndex);
			break;
		*/
		default:
			//result = null;
			throw (new SQLException("SQL type " + parameterType + " not supported\n"));
		}
		return result;
	}

}
