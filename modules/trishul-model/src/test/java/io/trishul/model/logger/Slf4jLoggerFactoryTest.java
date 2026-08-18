package io.trishul.model.logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Slf4jLoggerFactoryTest {
  @BeforeEach
  void clearLoggers() throws Exception {
    Field field = Slf4jLoggerFactory.class.getDeclaredField("loggers");
    field.setAccessible(true);
    ConcurrentMap<?, ?> map = (ConcurrentMap<?, ?>) field.get(null);
    map.clear();
  }

  @Test
  void testGetLogger_WithClass_ReturnsLoggerAndCachesInstance() {
    Slf4jLoggerWrapper logger1 = Slf4jLoggerFactory.getLogger(Slf4jLoggerFactoryTest.class);
    assertNotNull(logger1);
    assertEquals(Slf4jLoggerFactoryTest.class.getName(), logger1.getName());

    Slf4jLoggerWrapper logger2 = Slf4jLoggerFactory.getLogger(Slf4jLoggerFactoryTest.class);
    assertSame(logger1, logger2);
  }

  @Test
  void testGetLogger_WithString_ReturnsLoggerAndCachesInstance() {
    Slf4jLoggerWrapper logger1 = Slf4jLoggerFactory.getLogger("test-logger");
    assertNotNull(logger1);
    assertEquals("test-logger", logger1.getName());

    Slf4jLoggerWrapper logger2 = Slf4jLoggerFactory.getLogger("test-logger");
    assertSame(logger1, logger2);
  }
}
