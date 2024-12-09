package io.trishul.tenant.entity;

import java.util.Collection;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.trishul.model.base.pojo.refresher.Refresher;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;

public class TenantRefresher implements Refresher<Tenant, TenantAccessor> {
    private static final Logger log = LoggerFactory.getLogger(TenantRefresher.class);

    private final AccessorRefresher<UUID, TenantAccessor, Tenant> refresher;

    public TenantRefresher(AccessorRefresher<UUID, TenantAccessor, Tenant> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<Tenant> tenants) {
        // Nothing to refresh
    }

    @Override
    public void refreshAccessors(Collection<? extends TenantAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}