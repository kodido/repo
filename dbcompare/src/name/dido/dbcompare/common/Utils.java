package name.dido.dbcompare.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Utils {
	
	public static Properties loadProperties(String fileName) throws IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(fileName));
		return properties;
	}

}
