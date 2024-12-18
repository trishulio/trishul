package io.trishul.tenant.persistence.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import io.trishul.tenant.entity.TenantIdProvider;
import java.util.UUID;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantIdentifierResolverTest {
    private CurrentTenantIdentifierResolver<String> resolver;
    private TenantIdProvider mTenantIdProvider;

    @BeforeEach
    public void init() {
        mTenantIdProvider = mock(TenantIdProvider.class);

        resolver = new TenantIdentifierResolver(mTenantIdProvider);
    }

    @Test
    public void testResolveCurrentTenantIdentifier_ReturnsTenantIdFromTenantIdProvider() {
        doReturn(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .when(mTenantIdProvider)
                .getTenantId();
        assertEquals(
                "00000000-0000-0000-0000-000000000000", resolver.resolveCurrentTenantIdentifier());

        doReturn(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .when(mTenantIdProvider)
                .getTenantId();
        assertEquals(
                "00000000-0000-0000-0000-000000000001", resolver.resolveCurrentTenantIdentifier());
    }

    @Test
    public void testValidateExistingCurrentSessions_returnsTrue() {
        boolean validate = resolver.validateExistingCurrentSessions();

        assertTrue(validate);
    }
}
