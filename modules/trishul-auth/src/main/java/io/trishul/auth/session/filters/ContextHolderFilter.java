package io.trishul.auth.session.filters;

import java.io.IOException;
import java.util.function.Supplier;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import io.trishul.auth.session.context.PrincipalContext;
import io.trishul.auth.session.context.PrincipalContextBuilder;
import io.trishul.auth.session.context.holder.ThreadLocalContextHolder;

public class ContextHolderFilter implements Filter {
    private final ThreadLocalContextHolder ctxHolder;
    private final Supplier<SecurityContext> securityCtxSupplier;
    private final PrincipalContextBuilder ctxBuilder;

    public ContextHolderFilter(ThreadLocalContextHolder ctxHolder, PrincipalContextBuilder ctxBuilder) {
        this(() -> SecurityContextHolder.getContext(), ctxHolder, ctxBuilder);
    }

    protected ContextHolderFilter(Supplier<SecurityContext> securityCtxSupplier, ThreadLocalContextHolder ctxHolder, PrincipalContextBuilder ctxBuilder) {
        this.securityCtxSupplier = securityCtxSupplier;
        this.ctxHolder = ctxHolder;
        this.ctxBuilder = ctxBuilder;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        setPrincipalContext();

        chain.doFilter(request, response);
    }

    private void setPrincipalContext() {
        SecurityContext ctx = securityCtxSupplier.get();
        Authentication auth = ctx.getAuthentication();
        Object principal = auth.getPrincipal();
        PrincipalContext principalCtx = null;
        if (principal instanceof Jwt jwt) {
            principalCtx = this.ctxBuilder.build(jwt);
        }
        this.ctxHolder.setContext(principalCtx);
    }
}
