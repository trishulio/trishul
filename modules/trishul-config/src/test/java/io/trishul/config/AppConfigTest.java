package io.trishul.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class AppConfigTest {

  @Test
  void testSetAndGetName_ReturnsSetValue() {
    AppConfig config = new AppConfig("my-test-app");

    assertEquals("my-test-app", config.getName());
  }

  @Test
  void testDefaultName_IsNull() {
    AppConfig config = new AppConfig(null);

    assertNull(config.getName());
  }
}
