package io.trishul.iaas.auth.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.iaas.auth.session.context.ContextHolderAuthorizationFetcher;
import io.trishul.iaas.auth.session.context.IaasAuthorizationCredentialsBuilder;
import io.trishul.iaas.auth.session.context.IaasAuthorizationFetcher;
import io.trishul.iaas.auth.session.context.holder.IaasAuthorizationCredentialsHolder;
import io.trishul.iaas.auth.session.context.holder.ThreadLocalIaasAuthorizationCredentialsHolder;
import io.trishul.iaas.auth.session.filters.IaasAuthorizationCredentialsHolderFilter;

class IaasAuthAutoConfigurationTest {

  private IaasAuthAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new IaasAuthAutoConfiguration();
  }

  @Test
  void testIaasAuthorizationCredentialsHolder_ReturnsNonNull() {
    IaasAuthorizationCredentialsHolder result = config.iaasAuthorizationCredentialsHolder();

    assertNotNull(result);
    assertTrue(result instanceof ThreadLocalIaasAuthorizationCredentialsHolder);
  }

  @Test
  void testIaasAuthorizationCredentialsBuilder_ReturnsNonNull() {
    IaasAuthorizationCredentialsBuilder result = config.iaasAuthorizationCredentialsBuilder();

    assertNotNull(result);
  }

  @Test
  void testIaasAuthorizationCredentialsHolderFilter_ReturnsNonNull() {
    ThreadLocalIaasAuthorizationCredentialsHolder mockHolder = new ThreadLocalIaasAuthorizationCredentialsHolder();
    IaasAuthorizationCredentialsBuilder mockBuilder = mock(IaasAuthorizationCredentialsBuilder.class);

    IaasAuthorizationCredentialsHolderFilter result = config.iaasAuthorizationCredentialsHolderFilter(
        mockHolder, mockBuilder);

    assertNotNull(result);
  }

  @Test
  void testContextHolderAuthorizationFetcher_ReturnsNonNull() {
    IaasAuthorizationFetcher mockFetcher = mock(IaasAuthorizationFetcher.class);
    IaasAuthorizationCredentialsHolder mockHolder = mock(IaasAuthorizationCredentialsHolder.class);

    ContextHolderAuthorizationFetcher result = config.contextHolderAuthorizationFetcher(
        mockFetcher, mockHolder);

    assertNotNull(result);
  }
}
