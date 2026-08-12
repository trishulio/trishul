package sh.trishul.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ConfigAutoConfigurationTest {
  @Test
  void testAppConfig_ReturnsAppConfigWithGivenName() {
    ConfigAutoConfiguration config = new ConfigAutoConfiguration();
    AppConfig appConfig = config.appConfig("my-app");
    assertNotNull(appConfig);
    assertEquals("my-app", appConfig.getName());
  }
}
