package io.trishul.model.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class LoggingAutoConfigurationTest {

  @Test
  void testLoggingAspectBeanCreation() {
    LoggingAutoConfiguration config = new LoggingAutoConfiguration();
    assertNotNull(config.loggingAspect());
  }
}
