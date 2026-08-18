package sh.trishul.tenant.entity;

import java.util.UUID;

public interface TenantIdProvider {
  UUID getTenantId();
}
