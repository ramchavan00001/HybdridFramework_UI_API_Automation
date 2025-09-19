package RamChavanTestAutomation.UI_API_HybdriFramework.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
    private static final Logger logger = LogManager.getLogger(Log.class);

    public static void info(String message) {
        logger.info(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    // Overloaded error method to log exceptions
    public static void error(String message, Throwable t) {
        logger.error(message, t);
    }

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void startTestCase(String testName) {
        logger.info("=== STARTING TEST: " + testName + " ===");
    }

    public static void endTestCase(String testName) {
        logger.info("=== ENDING TEST: " + testName + " ===");
    }
}
