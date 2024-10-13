package io.trishul.auth.session.filters;

import java.io.IOException;
import java.util.function.Supplier;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import io.trishul.auth.session.context.CognitoPrincipalContext;
import io.trishul.auth.session.context.PrincipalContext;
import io.trishul.auth.session.context.holder.ThreadLocalContextHolder;

public class ContextHolderFilter implements Filter {
    public static final String HEADER_NAME_IAAS_TOKEN = "X-Iaas-Token";

    private ThreadLocalContextHolder ctxHolder;
    private Supplier<SecurityContext> securityCtxSupplier;

    public ContextHolderFilter(ThreadLocalContextHolder ctxHolder) {
        this(() -> SecurityContextHolder.getContext(), ctxHolder);
    }

    protected ContextHolderFilter(Supplier<SecurityContext> securityCtxSupplier, ThreadLocalContextHolder ctxHolder) {
        this.securityCtxSupplier = securityCtxSupplier;
        this.ctxHolder = ctxHolder;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        setPrincipalContext((HttpServletRequest) request);

        chain.doFilter(request, response);
    }

    private void setPrincipalContext(HttpServletRequest request) {
        SecurityContext ctx = securityCtxSupplier.get();
        Authentication auth = ctx.getAuthentication();
        Object principal = auth.getPrincipal();
        PrincipalContext principalCtx = null;
        if (principal instanceof Jwt) {
            Jwt jwt = (Jwt) principal;
            principalCtx = new CognitoPrincipalContext(jwt, request.getHeader(HEADER_NAME_IAAS_TOKEN));
        }
        this.ctxHolder.setContext(principalCtx);
    }
}
