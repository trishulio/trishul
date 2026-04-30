package io.trishul.tenant.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import io.trishul.tenant.entity.AdminTenant;
import io.trishul.tenant.entity.Tenant;
import io.trishul.tenant.persistence.management.migration.manager.MigrationManager;
import io.trishul.tenant.service.service.TenantService;

class AutoTenantMigratorOnStartupTest {

  private TenantService mockTenantService;
  private MigrationManager mockMigrationManager;
  private AdminTenant mockAdminTenant;

  @BeforeEach
  void setUp() {
    mockTenantService = mock(TenantService.class);
    mockMigrationManager = mock(MigrationManager.class);
    mockAdminTenant = mock(AdminTenant.class);
  }

  @Test
  void testConstructor_CreatesInstance() {
    AutoTenantMigratorOnStartup migrator = new AutoTenantMigratorOnStartup(
        mockAdminTenant, mockTenantService, mockMigrationManager);

    assertNotNull(migrator);
  }

  @Test
  void testMigrateAllTenantsOnStartup_MigratesAdminAndTenants() {
    Page<Tenant> emptyPage = new PageImpl<>(Collections.emptyList());
    when(mockTenantService.getAll(isNull(), isNull(), isNull(), anyBoolean(), any(), anyBoolean(), anyInt(), anyInt()))
        .thenReturn(emptyPage);

    AutoTenantMigratorOnStartup migrator = new AutoTenantMigratorOnStartup(
        mockAdminTenant, mockTenantService, mockMigrationManager);

    migrator.migrateAllTenantsOnStartup();

    verify(mockMigrationManager).migrate(mockAdminTenant);
  }
}
