package io.trishul.auth.session.filters;

import io.trishul.auth.session.context.PrincipalContext;
import io.trishul.auth.session.context.PrincipalContextBuilder;
import io.trishul.auth.session.context.holder.ThreadLocalContextHolder;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;
import java.util.function.Supplier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class ContextHolderFilter implements Filter {
  public static final String TENANT_ID_HEADER = "X-TENANT-ID";

  private final ThreadLocalContextHolder contextHolder;
  private final Supplier<SecurityContext> securityCtxSupplier;
  private final PrincipalContextBuilder principalContextBuilder;

  public ContextHolderFilter(ThreadLocalContextHolder contextHolder,
      PrincipalContextBuilder principalContextBuilder) {
    this(() -> SecurityContextHolder.getContext(), contextHolder, principalContextBuilder);
  }

  protected ContextHolderFilter(Supplier<SecurityContext> securityCtxSupplier,
      ThreadLocalContextHolder contextHolder, PrincipalContextBuilder principalContextBuilder) {
    this.securityCtxSupplier = securityCtxSupplier;
    this.contextHolder = contextHolder;
    this.principalContextBuilder = principalContextBuilder;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    try {
      setPrincipalContext();
      setSessionTenantId(request);
      chain.doFilter(request, response);
    } finally {
      this.contextHolder.clear();
    }
  }

  private void setPrincipalContext() {
    SecurityContext ctx = securityCtxSupplier.get();
    Authentication auth = ctx.getAuthentication();
    Object principal = auth.getPrincipal();
    PrincipalContext principalCtx = null;
    if (principal instanceof Jwt jwt) {
      principalCtx = this.principalContextBuilder.build(jwt);
    }
    this.contextHolder.setContext(principalCtx);
  }

  private void setSessionTenantId(ServletRequest request) {
    HttpServletRequest req = (HttpServletRequest) request;
    String tenantIdHeader = req.getHeader(TENANT_ID_HEADER);
    if (tenantIdHeader != null) {
      UUID tenantId = UUID.fromString(tenantIdHeader);
      this.contextHolder.setSessionTenantId(tenantId);
    }
  }
}
