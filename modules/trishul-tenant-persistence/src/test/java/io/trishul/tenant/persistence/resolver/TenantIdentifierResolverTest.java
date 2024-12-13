package io.trishul.tenant.persistence.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.UUID;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.auth.session.context.PrincipalContext;
import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.auth.session.context.holder.ThreadLocalContextHolder;
import io.trishul.tenant.entity.Tenant;

public class TenantIdentifierResolverTest {
    private ContextHolder mCtxHolder;
    private PrincipalContext mCtx;

    private Tenant mAdminTenant;

    private CurrentTenantIdentifierResolver resolver;

    @BeforeEach
    public void init() {
        mCtxHolder = mock(ThreadLocalContextHolder.class);
        mCtx = mock(CognitoPrincipalContext.class);

        mAdminTenant = mock(Tenant.class);
        doReturn(UUID.fromString("00000000-0000-0000-0000-000000000000")).when(mAdminTenant).getId();

        resolver = new TenantIdentifierResolver(mCtxHolder, mAdminTenant);
    }

    @Test
    public void testResolveCurrentTenantIdentifier_ReturnDefaultTenantId_WhenContextIsNull() {
        assertEquals("00000000-0000-0000-0000-000000000000", resolver.resolveCurrentTenantIdentifier());
    }

    @Test
    public void testResolveCurrentTenantIdentifier_ReturnDefaultTenantId_WhenContextHasNullTenant() {
        doReturn(mCtx).when(mCtxHolder).getPrincipalContext();
        assertEquals("00000000-0000-0000-0000-000000000000", resolver.resolveCurrentTenantIdentifier());
    }

    @Test
    public void testResolveCurrentTenantIdentifier_ReturnTenantId_WhenContextHasTenant() {
        doReturn(UUID.fromString("00000000-0000-0000-0000-000000000001")).when(mCtx).getTenantId();
        doReturn(mCtx).when(mCtxHolder).getPrincipalContext();

        assertEquals("00000000-0000-0000-0000-000000000001", resolver.resolveCurrentTenantIdentifier());
    }

    @Test
    public void testValidateExistingCurrentSessions_returnsTrue() {
        boolean validate = resolver.validateExistingCurrentSessions();

        assertTrue(validate);
    }
}
