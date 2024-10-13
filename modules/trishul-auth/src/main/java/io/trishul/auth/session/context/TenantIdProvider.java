package io.trishul.auth.session.context;

import java.util.UUID;

public interface TenantIdProvider {
    UUID getTenantId();
}
