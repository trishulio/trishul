package io.trishul.auth.session.filters;

import io.trishul.auth.session.context.PrincipalContext;
import io.trishul.auth.session.context.PrincipalContextBuilder;
import io.trishul.auth.session.context.holder.ThreadLocalContextHolder;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import java.util.function.Supplier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class ContextHolderFilter implements Filter {
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
    setPrincipalContext();

    chain.doFilter(request, response);
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
}
