package io.trishul.model.logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrishulLoggerFactoryTest {
  @BeforeEach
  void clearLoggers() throws Exception {
    Field field = TrishulLoggerFactory.class.getDeclaredField("loggers");
    field.setAccessible(true);
    ConcurrentMap<?, ?> map = (ConcurrentMap<?, ?>) field.get(null);
    map.clear();
  }

  @Test
  void testGetLogger_WithClass_ReturnsLoggerAndCachesInstance() {
    TrishulLogger logger1 = TrishulLoggerFactory.getLogger(TrishulLoggerFactoryTest.class);
    assertNotNull(logger1);
    assertEquals(TrishulLoggerFactoryTest.class.getName(), logger1.getName());

    TrishulLogger logger2 = TrishulLoggerFactory.getLogger(TrishulLoggerFactoryTest.class);
    assertSame(logger1, logger2);
  }

  @Test
  void testGetLogger_WithString_ReturnsLoggerAndCachesInstance() {
    TrishulLogger logger1 = TrishulLoggerFactory.getLogger("test-logger");
    assertNotNull(logger1);
    assertEquals("test-logger", logger1.getName());

    TrishulLogger logger2 = TrishulLoggerFactory.getLogger("test-logger");
    assertSame(logger1, logger2);
  }
}
