package io.trishul.iaas.auth.session.filters;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.iaas.auth.session.context.IaasAuthorizationCredentials;
import io.trishul.iaas.auth.session.context.IaasAuthorizationCredentialsBuilder;
import io.trishul.iaas.auth.session.context.holder.ThreadLocalIaasAuthorizationCredentialsHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

class IaasAuthorizationCredentialsHolderFilterTest {

  private ThreadLocalIaasAuthorizationCredentialsHolder mockHolder;
  private IaasAuthorizationCredentialsBuilder mockBuilder;
  private IaasAuthorizationCredentialsHolderFilter filter;

  @BeforeEach
  void setUp() {
    mockHolder = mock(ThreadLocalIaasAuthorizationCredentialsHolder.class);
    mockBuilder = mock(IaasAuthorizationCredentialsBuilder.class);
    filter = new IaasAuthorizationCredentialsHolderFilter(mockHolder, mockBuilder);
  }

  @Test
  void testConstructor_CreatesInstance() {
    assertNotNull(filter);
  }

  @Test
  void testDoFilter_SetsCredentialsAndChainsFilter() throws IOException, ServletException {
    ServletRequest mockRequest = mock(ServletRequest.class);
    ServletResponse mockResponse = mock(ServletResponse.class);
    FilterChain mockChain = mock(FilterChain.class);
    IaasAuthorizationCredentials mockCredentials = mock(IaasAuthorizationCredentials.class);

    when(mockBuilder.build(mockRequest)).thenReturn(mockCredentials);

    filter.doFilter(mockRequest, mockResponse, mockChain);

    verify(mockBuilder).build(mockRequest);
    verify(mockHolder).setIaasAuthorizationCredentials(mockCredentials);
    verify(mockChain).doFilter(mockRequest, mockResponse);
  }

  @Test
  void testDoFilter_SetsNullCredentialsWhenBuilderReturnsNull()
      throws IOException, ServletException {
    ServletRequest mockRequest = mock(ServletRequest.class);
    ServletResponse mockResponse = mock(ServletResponse.class);
    FilterChain mockChain = mock(FilterChain.class);

    when(mockBuilder.build(mockRequest)).thenReturn(null);

    filter.doFilter(mockRequest, mockResponse, mockChain);

    verify(mockBuilder).build(mockRequest);
    verify(mockHolder).setIaasAuthorizationCredentials(null);
    verify(mockChain).doFilter(mockRequest, mockResponse);
  }

  @Test
  void testDoFilter_CallsChainEvenAfterSettingCredentials() throws IOException, ServletException {
    ServletRequest mockRequest = mock(ServletRequest.class);
    ServletResponse mockResponse = mock(ServletResponse.class);
    FilterChain mockChain = mock(FilterChain.class);
    IaasAuthorizationCredentials mockCredentials = mock(IaasAuthorizationCredentials.class);

    when(mockBuilder.build(any(ServletRequest.class))).thenReturn(mockCredentials);

    filter.doFilter(mockRequest, mockResponse, mockChain);

    verify(mockChain).doFilter(mockRequest, mockResponse);
  }
}
