package io.trishul.tenant.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.trishul.tenant.entity.AdminTenant;
import io.trishul.tenant.entity.Tenant;
import io.trishul.tenant.persistence.management.migration.manager.MigrationManager;
import io.trishul.tenant.service.service.TenantService;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

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
    AutoTenantMigratorOnStartup migrator
        = new AutoTenantMigratorOnStartup(mockAdminTenant, mockTenantService, mockMigrationManager);

    assertNotNull(migrator);
  }

  @Test
  void testMigrateAllTenantsOnStartup_MigratesAdminAndTenants() {
    Page<Tenant> page = mock(Page.class);
    when(page.hasNext()).thenReturn(false, true);
    when(page.getContent()).thenReturn(Collections.emptyList());

    when(mockTenantService.getAll(isNull(), isNull(), isNull(), anyBoolean(), any(), anyBoolean(),
        anyInt(), anyInt())).thenReturn(page);

    AutoTenantMigratorOnStartup migrator
        = new AutoTenantMigratorOnStartup(mockAdminTenant, mockTenantService, mockMigrationManager);

    migrator.migrateAllTenantsOnStartup();

    verify(mockMigrationManager).migrate(mockAdminTenant);
    verify(mockMigrationManager).migrateAll(Collections.emptyList());
  }

  @Test
  void testMigrateAllTenantsOnStartup_MigratesMultiplePagesOfTenants() {
    Page<Tenant> page1 = mock(Page.class);
    when(page1.hasNext()).thenReturn(true);
    when(page1.getContent()).thenReturn(Collections.emptyList());

    Page<Tenant> page2 = mock(Page.class);
    when(page2.hasNext()).thenReturn(false);
    when(page2.getContent()).thenReturn(Collections.emptyList());

    when(mockTenantService.getAll(isNull(), isNull(), isNull(), anyBoolean(), any(), anyBoolean(),
        eq(0), anyInt())).thenReturn(page1);
    when(mockTenantService.getAll(isNull(), isNull(), isNull(), anyBoolean(), any(), anyBoolean(),
        eq(1), anyInt())).thenReturn(page2);

    AutoTenantMigratorOnStartup migrator
        = new AutoTenantMigratorOnStartup(mockAdminTenant, mockTenantService, mockMigrationManager);

    migrator.migrateAllTenantsOnStartup();

    verify(mockMigrationManager).migrate(mockAdminTenant);
    verify(mockMigrationManager, times(2)).migrateAll(Collections.emptyList());
    verify(mockTenantService).getAll(isNull(), isNull(), isNull(), anyBoolean(), any(),
        anyBoolean(), eq(0), anyInt());
    verify(mockTenantService).getAll(isNull(), isNull(), isNull(), anyBoolean(), any(),
        anyBoolean(), eq(1), anyInt());
  }
}
