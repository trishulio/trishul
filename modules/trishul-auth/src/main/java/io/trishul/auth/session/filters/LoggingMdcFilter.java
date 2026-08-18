package io.trishul.auth.session.filters;

import io.trishul.auth.session.context.PrincipalContext;
import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.auth.session.context.holder.ThreadLocalContextHolder;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;
import org.slf4j.MDC;

/**
 * Servlet filter that populates SLF4J MDC (Mapped Diagnostic Context) keys for every inbound
 * request so that log statements automatically include correlation metadata without any manual
 * plumbing inside business classes.
 *
 * <p>
 * The following MDC keys are set:
 * <ul>
 * <li>{@value #MDC_REQUEST_ID} — value of the {@code X-Request-ID} request header, or a freshly
 * generated UUID when the header is absent.</li>
 * <li>{@value #MDC_TENANT_ID} — the session tenant ID taken from the {@link ContextHolder}.</li>
 * <li>{@value #MDC_USERNAME} — the authenticated username taken from the
 * {@link ContextHolder}.</li>
 * </ul>
 *
 * <p>
 * This filter must be ordered <em>after</em> {@link ContextHolderFilter} because it reads values
 * that {@link ContextHolderFilter} populates from the JWT token.
 */
public class LoggingMdcFilter implements Filter {

  public static final String REQUEST_ID_HEADER = "X-Request-ID";
  public static final String MDC_REQUEST_ID = "requestId";
  public static final String MDC_TENANT_ID = "tenantId";
  public static final String MDC_USERNAME = "username";

  private final ContextHolder contextHolder;
  private final ThreadLocalContextHolder threadLocalContextHolder;

  public LoggingMdcFilter(ContextHolder contextHolder,
      ThreadLocalContextHolder threadLocalContextHolder) {
    this.contextHolder = contextHolder;
    this.threadLocalContextHolder = threadLocalContextHolder;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    try {
      populateMdc((HttpServletRequest) request);
      chain.doFilter(request, response);
    } finally {
      threadLocalContextHolder.setRequestId(null);
      MDC.remove(MDC_REQUEST_ID);
      MDC.remove(MDC_TENANT_ID);
      MDC.remove(MDC_USERNAME);
    }
  }

  private void populateMdc(HttpServletRequest request) {
    String reqId = request.getHeader(REQUEST_ID_HEADER);
    if (reqId == null || reqId.isBlank()) {
      reqId = UUID.randomUUID().toString();
    }
    threadLocalContextHolder.setRequestId(reqId);
    MDC.put(MDC_REQUEST_ID, reqId);

    UUID tenantId = contextHolder.getSessionTenantId();
    if (tenantId != null) {
      MDC.put(MDC_TENANT_ID, tenantId.toString());
    }

    PrincipalContext principal = contextHolder.getPrincipalContext();
    if (principal != null && principal.getUsername() != null) {
      MDC.put(MDC_USERNAME, principal.getUsername());
    }
  }
}
