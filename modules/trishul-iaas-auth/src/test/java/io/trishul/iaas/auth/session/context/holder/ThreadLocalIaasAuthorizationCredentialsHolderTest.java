package io.trishul.iaas.auth.session.context.holder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.iaas.auth.session.context.IaasAuthorizationCredentials;

class ThreadLocalIaasAuthorizationCredentialsHolderTest {

  private ThreadLocalIaasAuthorizationCredentialsHolder holder;

  @BeforeEach
  void setUp() {
    holder = new ThreadLocalIaasAuthorizationCredentialsHolder();
  }

  @Test
  void testConstructor_CreatesInstance() {
    assertNotNull(holder);
  }

  @Test
  void testGetIaasAuthorizationCredentials_ReturnsNullWhenNotSet() {
    IaasAuthorizationCredentials result = holder.getIaasAuthorizationCredentials();

    assertNull(result);
  }

  @Test
  void testSetIaasAuthorizationCredentials_SetsCredentials() {
    IaasAuthorizationCredentials mockCredentials = mock(IaasAuthorizationCredentials.class);

    ThreadLocalIaasAuthorizationCredentialsHolder result = holder.setIaasAuthorizationCredentials(mockCredentials);

    assertNotNull(result);
    assertEquals(holder, result);
  }

  @Test
  void testGetIaasAuthorizationCredentials_ReturnsSetCredentials() {
    IaasAuthorizationCredentials mockCredentials = mock(IaasAuthorizationCredentials.class);
    holder.setIaasAuthorizationCredentials(mockCredentials);

    IaasAuthorizationCredentials result = holder.getIaasAuthorizationCredentials();

    assertEquals(mockCredentials, result);
  }

  @Test
  void testSetIaasAuthorizationCredentials_OverwritesPreviousCredentials() {
    IaasAuthorizationCredentials mockCredentials1 = mock(IaasAuthorizationCredentials.class);
    IaasAuthorizationCredentials mockCredentials2 = mock(IaasAuthorizationCredentials.class);

    holder.setIaasAuthorizationCredentials(mockCredentials1);
    holder.setIaasAuthorizationCredentials(mockCredentials2);

    IaasAuthorizationCredentials result = holder.getIaasAuthorizationCredentials();

    assertEquals(mockCredentials2, result);
  }

  @Test
  void testSetIaasAuthorizationCredentials_AcceptsNull() {
    IaasAuthorizationCredentials mockCredentials = mock(IaasAuthorizationCredentials.class);
    holder.setIaasAuthorizationCredentials(mockCredentials);

    holder.setIaasAuthorizationCredentials(null);

    IaasAuthorizationCredentials result = holder.getIaasAuthorizationCredentials();
    assertNull(result);
  }
}
