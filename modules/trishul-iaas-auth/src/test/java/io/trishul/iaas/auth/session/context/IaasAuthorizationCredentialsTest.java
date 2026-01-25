package io.trishul.iaas.auth.session.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IaasAuthorizationCredentialsTest {
  private IaasAuthorizationCredentials credentials;

  @BeforeEach
  void init() {
    credentials = new IaasAuthorizationCredentials("TOKEN");
  }

  @Test
  void testAllArgConstructor() {
    assertEquals("TOKEN", credentials.toString());
  }
}
