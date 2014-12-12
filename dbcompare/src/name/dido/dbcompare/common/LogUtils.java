package name.dido.dbcompare.common;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogUtils {

	public static Logger LOGGER = Logger.getLogger("name.dido.dbtools");

	public static void startConsoleLogging() {		
		Handler[] handlers = LOGGER.getHandlers();
		for (int i = 0; i < handlers.length; i++) {
			LOGGER.removeHandler(handlers[i]);
		}
		Handler handler = new ConsoleHandler();
		handler.setFormatter(new SimpleFormatter());
		LOGGER.addHandler(handler);
	}

}
