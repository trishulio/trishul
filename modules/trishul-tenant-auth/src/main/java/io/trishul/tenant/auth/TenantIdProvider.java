package io.trishul.tenant.auth;

import java.util.UUID;

public interface TenantIdProvider {
    UUID getTenantId();
}
