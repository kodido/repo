package name.dido.dbcompare.generator.cmd;

import static name.dido.dbcompare.common.ConnectProperties.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import name.dido.dbcompare.common.ConnectionData;
import name.dido.dbcompare.common.LogUtils;
import name.dido.dbcompare.generator.parallel.ParallelDataGenerator;

public class Generator {

	private static Logger getLogger() {
		return LogUtils.LOGGER;
	}

	public static void main(String[] args) {
		try {
			LogUtils.startConsoleLogging();

			Properties props = loadProperties();

			getLogger().info("Connecting to source DB");

			String driver = props.getProperty(propSourceDriver);
			String url = props.getProperty(propSourceUrl);
			String user = props.getProperty(propSourceUser);
			String passwd = props.getProperty(propSourcePasswd);

			ConnectionData connectionData = new ConnectionData(driver, url,
					user, passwd);
			ParallelDataGenerator dataGenerator = new ParallelDataGenerator(
					connectionData);
			
			getLogger().info("Generating data");
			dataGenerator.generateData(props.getProperty(propTableName),
					Integer.parseInt(props.getProperty(propTableInsertCount)),
					Integer.parseInt(props.getProperty(propNumberOfTasks)),
					Integer.parseInt(props.getProperty(propNumberOfExecutors)));
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, "Exception caught: " + e.getMessage(), e);
			System.exit(1);
		}
	}

	private static Properties loadProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream("connect.properties"));
		return properties;
	}

}
