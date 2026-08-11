package io.trishul.model.aop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.trishul.model.logger.NoMethodLogging;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

class LoggingAspectTest {

  private LoggingAspect aspect;

  @BeforeEach
  void setUp() {
    this.aspect = new LoggingAspect();
  }

  public interface DummyService {
    void process(String arg);

    void skipProcess(String arg);
  }

  @NoMethodLogging
  public static class IgnoredService implements DummyService {
    @Override
    public void process(String arg) {}

    @Override
    public void skipProcess(String arg) {}
  }

  public static class StandardService implements DummyService {
    @Override
    public void process(String arg) {}

    @Override
    @NoMethodLogging
    public void skipProcess(String arg) {}
  }

  @Test
  void testFormatArgs_ReturnsEmptyString_WhenArgsAreNullOrEmpty() {
    assertEquals("", LoggingAspect.formatArgs(null));
    assertEquals("", LoggingAspect.formatArgs(new Object[0]));
  }

  @Test
  void testFormatArgs_TruncatesLongArguments() {
    String longStr = "a".repeat(300);
    String result = LoggingAspect.formatArgs(new Object[] {longStr});
    assertEquals(203, result.length()); // 200 + 3 dots
    assertEquals(longStr.substring(0, 200) + "...", result);

    String exactStr = "a".repeat(200);
    String exactResult = LoggingAspect.formatArgs(new Object[] {exactStr});
    assertEquals(200, exactResult.length());
    assertEquals(exactStr, exactResult);
  }

  @Test
  void testFormatArgs_HandlesMultipleArgs() {
    String result = LoggingAspect.formatArgs(new Object[] {"val1", 123});
    assertEquals("val1, 123", result);
  }

  @Test
  void testLogMethodEntry_ExecutesWithoutError() {
    JoinPoint jp = mock(JoinPoint.class);
    Signature sig = mock(Signature.class);
    when(jp.getTarget()).thenReturn(new StandardService());
    when(jp.getSignature()).thenReturn(sig);
    when(sig.getName()).thenReturn("process");
    when(jp.getArgs()).thenReturn(new Object[] {"hello"});

    // Verify it doesn't throw
    aspect.logMethodEntry(jp);
  }

  @Test
  void testLogMethodEntry_WithDebugEnabled() {
    JoinPoint jp = mock(JoinPoint.class);
    Signature sig = mock(Signature.class);
    when(jp.getTarget()).thenReturn(new StandardService());
    when(jp.getSignature()).thenReturn(sig);
    when(sig.getName()).thenReturn("process");
    when(jp.getArgs()).thenReturn(new Object[] {"hello"});

    Logger mLogger = mock(Logger.class);
    when(mLogger.isDebugEnabled()).thenReturn(true);

    try (MockedStatic<LoggerFactory> mockedFactory = Mockito.mockStatic(LoggerFactory.class)) {
      mockedFactory.when(() -> LoggerFactory.getLogger(StandardService.class)).thenReturn(mLogger);

      aspect.logMethodEntry(jp);

      verify(mLogger).debug("Entering {}.{}() args=[{}]", "StandardService", "process", "hello");
    }
  }

  @Test
  void testAspectIntegration() {
    StandardService target = new StandardService();
    AspectJProxyFactory factory = new AspectJProxyFactory(target);
    factory.addAspect(aspect);
    DummyService proxy = factory.getProxy();

    // Call method to ensure proxy intercept doesn't crash execution
    proxy.process("test");
    proxy.skipProcess("test");
    assertNotNull(proxy);
  }
}
