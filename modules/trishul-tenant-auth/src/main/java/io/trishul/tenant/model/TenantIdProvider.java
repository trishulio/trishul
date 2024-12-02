package io.trishul.tenant.model;

import java.util.UUID;

public interface TenantIdProvider {
    UUID getTenantId();
}
