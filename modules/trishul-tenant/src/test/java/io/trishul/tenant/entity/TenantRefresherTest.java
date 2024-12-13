package io.trishul.tenant.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

public class TenantRefresherTest {
    private TenantRefresher tenantRefresher;

    private AccessorRefresher<UUID, TenantAccessor, Tenant> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mRefresher = mock(AccessorRefresher.class);

        tenantRefresher = new TenantRefresher(mRefresher);
    }

    @Test
    public void testRefreshAccessors_CallsRefreshAccessor() {
        List<TenantAccessor> accessors = List.of(mock(TenantAccessor.class), mock(TenantAccessor.class));

        tenantRefresher.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }

    @Test
    public void testRefresh_RefreshesSuppliers() {
        List<Tenant> entities = List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")), new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002")));
        tenantRefresher.refresh(entities);

        List<Tenant> expected = List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")), new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002")));
        assertEquals(expected, entities);
    }
}
