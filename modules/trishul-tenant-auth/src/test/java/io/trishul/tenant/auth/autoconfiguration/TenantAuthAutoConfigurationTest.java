package io.trishul.tenant.auth.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.tenant.auth.model.ContextHolderTenantIdProvider;
import io.trishul.tenant.entity.AdminTenant;
import io.trishul.tenant.entity.TenantIdProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
