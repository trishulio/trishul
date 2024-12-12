package io.trishul.auth.session.filters;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Arrays;

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

import io.trishul.auth.aws.session.context.CognitoPrincipalContext;
import io.trishul.auth.session.context.holder.ThreadLocalContextHolder;

public class ContextHolderFilterTest {
    private Filter filter;

    private ThreadLocalContextHolder mCtxHolder;
    private SecurityContext mSecurityCtx;

    private ServletRequest mReq;
    private ServletResponse mRes;
    private FilterChain mChain;

    @BeforeEach
    public void init() {
        mCtxHolder = new ThreadLocalContextHolder();
        mSecurityCtx = mock(SecurityContext.class);

        mReq = mock(HttpServletRequest.class);
        mRes = mock(HttpServletResponse.class);
        mChain = mock(FilterChain.class);

        filter = new ContextHolderFilter(() -> mSecurityCtx, mCtxHolder);
    }

    @Test
    public void testDoFilter_SetsPrincipalContext() throws IOException, ServletException {
        Jwt mJwt = mock(Jwt.class);
        doReturn(Arrays.asList("00000000-0000-0000-0000-000000000001")).when(mJwt).getClaimAsStringList(CognitoPrincipalContext.CLAIM_GROUPS);
        doReturn("USERNAME_1").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_USERNAME);
        doReturn("SCOPE_1 SCOPE_2").when(mJwt).getClaimAsString(CognitoPrincipalContext.CLAIM_SCOPE);

        Authentication mAuth = mock(Authentication.class);
        doReturn(mJwt).when(mAuth).getPrincipal();
        doReturn(mAuth).when(mSecurityCtx).getAuthentication();

        doReturn("IAAS-TOKEN").when((HttpServletRequest) mReq).getHeader(ContextHolderFilter.HEADER_NAME_IAAS_TOKEN);

        filter.doFilter(mReq, mRes, mChain);

        CognitoPrincipalContext expected = new CognitoPrincipalContext(mJwt, "IAAS-TOKEN");
        assertEquals(expected, mCtxHolder.getPrincipalContext());

        verify(mChain).doFilter(mReq, mRes);
    }

    @Test
    public void testDoFilter_SetsNull_WhenPrincipalIsNull() throws IOException, ServletException {
        Authentication mAuth = mock(Authentication.class);
        doReturn(null).when(mAuth).getPrincipal();
        doReturn(mAuth).when(mSecurityCtx).getAuthentication();

        filter.doFilter(mReq, mRes, mChain);

        assertNull(mCtxHolder.getPrincipalContext());

        verify(mChain).doFilter(mReq, mRes);
    }
}
