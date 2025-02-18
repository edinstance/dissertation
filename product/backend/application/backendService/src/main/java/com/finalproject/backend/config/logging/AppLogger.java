package com.finalproject.backend.config.logging;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppLogger {
  private static final Logger logger = LogManager.getLogger("AppLogger");

  // Private constructor to prevent instantiation
  private AppLogger() {
  }
  public static void info(String message) {
    logger.info(message);
  }

  public static void info(String message, Object... params) {
    logger.info(message, params);
  }

  public static void error(String message) {
    logger.error(message);
  }

  public static void error(String message, Throwable throwable) {
    logger.error(message, throwable);
  }
}