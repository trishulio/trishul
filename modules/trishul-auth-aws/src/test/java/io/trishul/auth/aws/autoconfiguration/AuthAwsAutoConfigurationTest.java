package io.trishul.auth.aws.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.trishul.auth.aws.session.context.builder.CognitoPrincipalContextBuilder;
import io.trishul.auth.session.context.PrincipalContextBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthAwsAutoConfigurationTest {
  private AuthAwsAutoConfiguration config;

  @BeforeEach
  void init() {
    config = new AuthAwsAutoConfiguration();
  }

  @Test
  void testPrincipalContextBuilder_ReturnsNonNullInstance() {
    PrincipalContextBuilder builder = config.principalContextBuilder();
    assertNotNull(builder);
  }

  @Test
  void testPrincipalContextBuilder_ReturnsInstanceOfCognitoPrincipalContextBuilder() {
    PrincipalContextBuilder builder = config.principalContextBuilder();
    assertTrue(builder instanceof CognitoPrincipalContextBuilder);
  }
}
