package sh.trishul.model.logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.LoggerFactory;

/**
 * Factory class to retrieve instances of {@link Slf4jLoggerWrapper}.
 */
public final class Slf4jLoggerFactory {
  private static final ConcurrentMap<String, Slf4jLoggerWrapper> loggers
      = new ConcurrentHashMap<>();

  private Slf4jLoggerFactory() {}

  public static Slf4jLoggerWrapper getLogger(Class<?> clazz) {
    Slf4jLoggerWrapper logger = loggers.get(clazz.getName());
    if (logger == null) {
      logger = new Slf4jLoggerWrapper(clazz);
      Slf4jLoggerWrapper existing = loggers.putIfAbsent(clazz.getName(), logger);
      if (existing != null) {
        logger = existing;
      }
    }
    return logger;
  }

  public static Slf4jLoggerWrapper getLogger(String name) {
    Slf4jLoggerWrapper logger = loggers.get(name);
    if (logger == null) {
      logger = new Slf4jLoggerWrapper(LoggerFactory.getLogger(name));
      Slf4jLoggerWrapper existing = loggers.putIfAbsent(name, logger);
      if (existing != null) {
        logger = existing;
      }
    }
    return logger;
  }
}
