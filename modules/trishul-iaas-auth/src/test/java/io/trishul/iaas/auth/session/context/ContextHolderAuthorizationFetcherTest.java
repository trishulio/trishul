package io.trishul.iaas.auth.session.context;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.trishul.iaas.auth.session.context.holder.IaasAuthorizationCredentialsHolder;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

public class ContextHolderAuthorizationFetcherTest {

  @Test
  public void testFetcher_returnsAuth_whenCredentialsExist() {
    IaasAuthorizationFetcher delegate = mock(IaasAuthorizationFetcher.class);
    IaasAuthorizationCredentialsHolder holder = mock(IaasAuthorizationCredentialsHolder.class);
    IaasAuthorizationCredentials creds = new IaasAuthorizationCredentials("token");
    IaasAuthorization auth = mock(IaasAuthorization.class);

    when(holder.getIaasAuthorizationCredentials()).thenReturn(creds);
    when(delegate.fetch(creds)).thenReturn(auth);

    ContextHolderAuthorizationFetcher fetcher
        = new ContextHolderAuthorizationFetcher(delegate, holder);
    IaasAuthorization result = fetcher.fetch();

    assertEquals(auth, result);
  }

  @Test
  public void testFetcher_throwsRuntimeException_whenCredentialsAreNull() {
    IaasAuthorizationFetcher delegate = mock(IaasAuthorizationFetcher.class);
    IaasAuthorizationCredentialsHolder holder = mock(IaasAuthorizationCredentialsHolder.class);

    when(holder.getIaasAuthorizationCredentials()).thenReturn(null);

    ContextHolderAuthorizationFetcher fetcher
        = new ContextHolderAuthorizationFetcher(delegate, holder);
    RuntimeException ex = assertThrows(RuntimeException.class, fetcher::fetch);
    assertTrue(ex.getMessage().contains("No token set for header"));
  }

  @Test
  public void testBuilder_throwsIllegalArgumentException_whenRequestIsNull() {
    IaasAuthorizationCredentialsBuilder builder = new IaasAuthorizationCredentialsBuilder();
    assertThrows(IllegalArgumentException.class, () -> builder.build(null));
  }

  @Test
  public void testBuilder_throwsIllegalArgumentException_whenRequestIsNotHttp() {
    IaasAuthorizationCredentialsBuilder builder = new IaasAuthorizationCredentialsBuilder();
    ServletRequest request = mock(ServletRequest.class);
    assertThrows(IllegalArgumentException.class, () -> builder.build(request));
  }

  @Test
  public void testBuilder_returnsNull_whenHeaderIsMissing() {
    IaasAuthorizationCredentialsBuilder builder = new IaasAuthorizationCredentialsBuilder();
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getHeader(IaasAuthorizationCredentials.HEADER_NAME_IAAS_TOKEN)).thenReturn(null);

    assertNull(builder.build(request));
  }

  @Test
  public void testBuilder_returnsCredentials_whenHeaderIsPresent() {
    IaasAuthorizationCredentialsBuilder builder = new IaasAuthorizationCredentialsBuilder();
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getHeader(IaasAuthorizationCredentials.HEADER_NAME_IAAS_TOKEN))
        .thenReturn("my-token");

    IaasAuthorizationCredentials creds = builder.build(request);
    assertNotNull(creds);
    assertEquals("my-token", creds.toString());
  }
}
