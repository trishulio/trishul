package sh.trishul.tenant.auth.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.auth.session.context.holder.ContextHolder;
import sh.trishul.tenant.auth.model.ContextHolderTenantIdProvider;
import sh.trishul.tenant.entity.AdminTenant;
import sh.trishul.tenant.entity.TenantIdProvider;

class TenantAuthAutoConfigurationTest {
  private TenantAuthAutoConfiguration config;

  @BeforeEach
  void init() {
    config = new TenantAuthAutoConfiguration();
  }

  @Test
  void testTenantIdProvider_ReturnsInstanceOfContextHolderTenantIdProvider() {
    ContextHolder mContextHolder = mock(ContextHolder.class);
    AdminTenant mAdminTenant = mock(AdminTenant.class);

    TenantIdProvider provider = config.tenantIdProvider(mContextHolder, mAdminTenant);
    assertTrue(provider instanceof ContextHolderTenantIdProvider);
  }
}
