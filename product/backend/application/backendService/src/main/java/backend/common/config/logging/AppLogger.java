package backend.common.config.logging;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is a universal logger.
 */
public class AppLogger {

  /**
   * This created the logger that is used.
   */
  private static final Logger logger = LogManager.getLogger("AppLogger");

  /**
   * Private constructor to prevent instantiation.
   */
  private AppLogger() {
  }

  /**
   * Info level logging with a message.
   *
   * @param message the message to log.
   */
  public static void info(String message) {
    logger.info(message);
  }

  /**
   * Info level logging with message and objects.
   *
   * @param message the message to log.
   * @param params the objects to log.
   */
  public static void info(String message, Object... params) {
    logger.info(message, params);
  }

  /**
   * Warn level logging with a message.
   *
   * @param message the message to log.
   */
  public static void warn(String message) {
    logger.warn(message);
  }

  /**
   * Warn level logging with message and objects.
   *
   * @param message the message to log.
   * @param params the objects to log.
   */
  public static void warn(String message, Object... params) {
    logger.warn(message, params);
  }

  /**
   * Error logging with messages.
   *
   * @param message the message to log.
   */
  public static void error(String message) {
    logger.error(message);
  }

  /**
   * Error logging with messages and throwables.
   *
   * @param message the message to log.
   * @param throwable the throwable to log.
   */
  public static void error(String message, Throwable throwable) {
    logger.error(message, throwable);
  }
}