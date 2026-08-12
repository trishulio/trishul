package sh.trishul.tenant.persistence.resolver;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import sh.trishul.tenant.entity.TenantIdProvider;

public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<String> {
  private final TenantIdProvider tenantIdProvider;

  public TenantIdentifierResolver(TenantIdProvider tenantIdProvider) {
    this.tenantIdProvider = tenantIdProvider;
  }

  @Override
  public String resolveCurrentTenantIdentifier() {
    return this.tenantIdProvider.getTenantId().toString();
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return true;
  }
}
