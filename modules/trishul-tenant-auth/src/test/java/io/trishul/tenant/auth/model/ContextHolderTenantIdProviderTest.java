package io.trishul.tenant.auth.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.tenant.entity.AdminTenant;
import java.net.URI;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContextHolderTenantIdProviderTest {
  private ContextHolder mContextHolder;
  private AdminTenant adminTenant;
  private ContextHolderTenantIdProvider provider;
  private final UUID defaultTenantId = UUID.fromString("00000000-0000-0000-0000-000000000000");

  @BeforeEach
  void init() {
    mContextHolder = mock(ContextHolder.class);
    adminTenant = new AdminTenant(defaultTenantId, "ADMIN", URI.create("http://localhost/"));
    provider = new ContextHolderTenantIdProvider(mContextHolder, adminTenant);
  }

  @Test
  void testGetTenantId_ReturnsSessionTenantId_WhenSessionTenantIdIsNotNull() {
    UUID sessionTenantId = UUID.fromString("00000000-0000-0000-0000-000000000001");
    when(mContextHolder.getSessionTenantId()).thenReturn(sessionTenantId);

    UUID tenantId = provider.getTenantId();
    assertEquals(sessionTenantId, tenantId);
  }

  @Test
  void testGetTenantId_ReturnsDefaultTenantId_WhenSessionTenantIdIsNull() {
    when(mContextHolder.getSessionTenantId()).thenReturn(null);

    UUID tenantId = provider.getTenantId();
    assertEquals(defaultTenantId, tenantId);
  }
}
