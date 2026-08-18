package sh.trishul.auth.aws.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.auth.aws.session.context.builder.CognitoPrincipalContextBuilder;
import sh.trishul.auth.session.context.PrincipalContextBuilder;

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
