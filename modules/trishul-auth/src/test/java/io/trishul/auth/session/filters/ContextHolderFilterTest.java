package io.trishul.auth.session.filters;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import io.trishul.auth.session.context.PrincipalContext;
import io.trishul.auth.session.context.PrincipalContextBuilder;
import io.trishul.auth.session.context.holder.ThreadLocalContextHolder;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;

public class ContextHolderFilterTest {
    private Filter filter;

    private ThreadLocalContextHolder mCtxHolder;
    private SecurityContext mSecurityCtx;
    private PrincipalContextBuilder mPrincipalContextBuilder;

    private ServletRequest mReq;
    private ServletResponse mRes;
    private FilterChain mChain;

    @BeforeEach
    public void init() {
        mCtxHolder = new ThreadLocalContextHolder();
        mSecurityCtx = mock(SecurityContext.class);
        mPrincipalContextBuilder = mock(PrincipalContextBuilder.class);

        mReq = mock(HttpServletRequest.class);
        mRes = mock(HttpServletResponse.class);
        mChain = mock(FilterChain.class);

        filter = new ContextHolderFilter(() -> mSecurityCtx, mCtxHolder, mPrincipalContextBuilder);
    }

    @Test
    public void testDoFilter_SetsPrincipalContext() throws IOException, ServletException {
        Authentication mAuth = mock(Authentication.class);
        doReturn(mAuth).when(mSecurityCtx).getAuthentication();

        Jwt mJwt = mock(Jwt.class);
        doReturn(mJwt).when(mAuth).getPrincipal();

        PrincipalContext mCtx = mock(PrincipalContext.class);
        doReturn(mCtx).when(mPrincipalContextBuilder).build(mJwt);

        filter.doFilter(mReq, mRes, mChain);

        PrincipalContext ctx = mCtxHolder.getPrincipalContext();

        assertSame(mCtx, ctx);
        verify(mChain).doFilter(mReq, mRes);
    }

    @Test
    public void testDoFilter_SetsNull_WhenPrincipalIsNull() throws IOException, ServletException {
        Authentication mAuth = mock(Authentication.class);
        doReturn(mAuth).when(mSecurityCtx).getAuthentication();
        doReturn(null).when(mAuth).getPrincipal();

        filter.doFilter(mReq, mRes, mChain);

        PrincipalContext ctx = mCtxHolder.getPrincipalContext();

        assertNull(ctx);
        verify(mChain).doFilter(mReq, mRes);
        verifyNoInteractions(mPrincipalContextBuilder);
    }
}
