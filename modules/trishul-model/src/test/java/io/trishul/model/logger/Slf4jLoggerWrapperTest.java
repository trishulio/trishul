package io.trishul.model.logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

class Slf4jLoggerWrapperTest {
  private Logger mockLogger;
  private Slf4jLoggerWrapper trishulLogger;

  @BeforeEach
  void setUp() {
    mockLogger = mock(Logger.class);
    trishulLogger = new Slf4jLoggerWrapper(mockLogger);
  }

  @Test
  void testGetName() {
    when(mockLogger.getName()).thenReturn("TestName");
    assertEquals("TestName", trishulLogger.getName());
  }

  @Test
  void testTraceMethods() {
    when(mockLogger.isTraceEnabled()).thenReturn(true);
    assertTrue(trishulLogger.isTraceEnabled());

    trishulLogger.trace("msg");
    verify(mockLogger).trace("msg");

    trishulLogger.trace("format {}", "arg");
    verify(mockLogger).trace("format {}", "arg");

    trishulLogger.trace("format {} {}", "arg1", "arg2");
    verify(mockLogger).trace("format {} {}", "arg1", "arg2");

    Object[] args = new Object[] {"a", "b"};
    trishulLogger.trace("format {} {}", args);
    verify(mockLogger).trace("format {} {}", args);

    Throwable t = new RuntimeException();
    trishulLogger.trace("msg", t);
    verify(mockLogger).trace("msg", t);
  }

  @Test
  void testDebugMethods() {
    when(mockLogger.isDebugEnabled()).thenReturn(true);
    assertTrue(trishulLogger.isDebugEnabled());

    trishulLogger.debug("msg");
    verify(mockLogger).debug("msg");

    trishulLogger.debug("format {}", "arg");
    verify(mockLogger).debug("format {}", "arg");

    trishulLogger.debug("format {} {}", "arg1", "arg2");
    verify(mockLogger).debug("format {} {}", "arg1", "arg2");

    Object[] args = new Object[] {"a", "b"};
    trishulLogger.debug("format {} {}", args);
    verify(mockLogger).debug("format {} {}", args);

    Throwable t = new RuntimeException();
    trishulLogger.debug("msg", t);
    verify(mockLogger).debug("msg", t);
  }

  @Test
  void testInfoMethods() {
    when(mockLogger.isInfoEnabled()).thenReturn(true);
    assertTrue(trishulLogger.isInfoEnabled());

    trishulLogger.info("msg");
    verify(mockLogger).info("msg");

    trishulLogger.info("format {}", "arg");
    verify(mockLogger).info("format {}", "arg");

    trishulLogger.info("format {} {}", "arg1", "arg2");
    verify(mockLogger).info("format {} {}", "arg1", "arg2");

    Object[] args = new Object[] {"a", "b"};
    trishulLogger.info("format {} {}", args);
    verify(mockLogger).info("format {} {}", args);

    Throwable t = new RuntimeException();
    trishulLogger.info("msg", t);
    verify(mockLogger).info("msg", t);
  }

  @Test
  void testWarnMethods() {
    when(mockLogger.isWarnEnabled()).thenReturn(true);
    assertTrue(trishulLogger.isWarnEnabled());

    trishulLogger.warn("msg");
    verify(mockLogger).warn("msg");

    trishulLogger.warn("format {}", "arg");
    verify(mockLogger).warn("format {}", "arg");

    trishulLogger.warn("format {} {}", "arg1", "arg2");
    verify(mockLogger).warn("format {} {}", "arg1", "arg2");

    Object[] args = new Object[] {"a", "b"};
    trishulLogger.warn("format {} {}", args);
    verify(mockLogger).warn("format {} {}", args);

    Throwable t = new RuntimeException();
    trishulLogger.warn("msg", t);
    verify(mockLogger).warn("msg", t);
  }

  @Test
  void testErrorMethods() {
    when(mockLogger.isErrorEnabled()).thenReturn(true);
    assertTrue(trishulLogger.isErrorEnabled());

    trishulLogger.error("msg");
    verify(mockLogger).error("msg");

    trishulLogger.error("format {}", "arg");
    verify(mockLogger).error("format {}", "arg");

    trishulLogger.error("format {} {}", "arg1", "arg2");
    verify(mockLogger).error("format {} {}", "arg1", "arg2");

    Object[] args = new Object[] {"a", "b"};
    trishulLogger.error("format {} {}", args);
    verify(mockLogger).error("format {} {}", args);

    Throwable t = new RuntimeException();
    trishulLogger.error("msg", t);
    verify(mockLogger).error("msg", t);
  }

  @Test
  void testFactoryGetLogger() {
    Slf4jLoggerWrapper l1 = Slf4jLoggerFactory.getLogger(Slf4jLoggerWrapperTest.class);
    assertNotNull(l1);
    Slf4jLoggerWrapper l2 = Slf4jLoggerFactory.getLogger("some-logger");
    assertNotNull(l2);
  }
}
