package io.trishul.model.logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.LoggerFactory;

/**
 * Factory class to retrieve instances of {@link TrishulLogger}.
 */
public final class TrishulLoggerFactory {
  private static final ConcurrentMap<String, TrishulLogger> loggers = new ConcurrentHashMap<>();

  private TrishulLoggerFactory() {}

  public static TrishulLogger getLogger(Class<?> clazz) {
    TrishulLogger logger = loggers.get(clazz.getName());
    if (logger == null) {
      logger = new TrishulLogger(clazz);
      TrishulLogger existing = loggers.putIfAbsent(clazz.getName(), logger);
      if (existing != null) {
        logger = existing;
      }
    }
    return logger;
  }

  public static TrishulLogger getLogger(String name) {
    TrishulLogger logger = loggers.get(name);
    if (logger == null) {
      logger = new TrishulLogger(LoggerFactory.getLogger(name));
      TrishulLogger existing = loggers.putIfAbsent(name, logger);
      if (existing != null) {
        logger = existing;
      }
    }
    return logger;
  }
}
