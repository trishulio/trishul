package io.trishul.auth.session.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.trishul.auth.session.context.PrincipalContext;
import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.auth.session.context.holder.ThreadLocalContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

class LoggingMdcFilterTest {
  private LoggingMdcFilter filter;
  private ContextHolder contextHolder;
  private ThreadLocalContextHolder threadLocalContextHolder;

  private HttpServletRequest request;
  private HttpServletResponse response;
  private FilterChain chain;

  @BeforeEach
  void setUp() {
    contextHolder = mock(ContextHolder.class);
    threadLocalContextHolder = new ThreadLocalContextHolder();
    filter = new LoggingMdcFilter(contextHolder, threadLocalContextHolder);

    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    chain = mock(FilterChain.class);
  }

  @Test
  void testDoFilter_PopulatesMdc_AndClearsAfterwards() throws IOException, ServletException {
    UUID tenantId = UUID.randomUUID();
    doReturn(tenantId).when(contextHolder).getSessionTenantId();

    PrincipalContext principalCtx = mock(PrincipalContext.class);
    doReturn(principalCtx).when(contextHolder).getPrincipalContext();
    doReturn("test-user").when(principalCtx).getUsername();
    doReturn("custom-req-id").when(request).getHeader(LoggingMdcFilter.REQUEST_ID_HEADER);

    doAnswer(invocation -> {
      // Within filter execution, MDC should be populated
      assertEquals("custom-req-id", MDC.get(LoggingMdcFilter.MDC_REQUEST_ID));
      assertEquals("custom-req-id", threadLocalContextHolder.getRequestId());
      assertEquals(tenantId.toString(), MDC.get(LoggingMdcFilter.MDC_TENANT_ID));
      assertEquals("test-user", MDC.get(LoggingMdcFilter.MDC_USERNAME));
      return null;
    }).when(chain).doFilter(request, response);

    filter.doFilter(request, response, chain);

    // Verify it called downstream chain
    verify(chain).doFilter(request, response);

    // Verify MDC is cleared after filter execution finishes
    assertNull(MDC.get(LoggingMdcFilter.MDC_REQUEST_ID));
    assertNull(MDC.get(LoggingMdcFilter.MDC_TENANT_ID));
    assertNull(MDC.get(LoggingMdcFilter.MDC_USERNAME));
    assertNull(threadLocalContextHolder.getRequestId());
  }

  @Test
  void testDoFilter_GeneratesRequestId_WhenHeaderIsMissing() throws IOException, ServletException {
    doReturn(null).when(contextHolder).getSessionTenantId();
    doReturn(null).when(contextHolder).getPrincipalContext();
    doReturn(null).when(request).getHeader(LoggingMdcFilter.REQUEST_ID_HEADER);

    doAnswer(invocation -> {
      String generatedId = MDC.get(LoggingMdcFilter.MDC_REQUEST_ID);
      // It should generate a UUID
      UUID.fromString(generatedId);
      assertEquals(generatedId, threadLocalContextHolder.getRequestId());
      return null;
    }).when(chain).doFilter(request, response);

    filter.doFilter(request, response, chain);
  }
}
