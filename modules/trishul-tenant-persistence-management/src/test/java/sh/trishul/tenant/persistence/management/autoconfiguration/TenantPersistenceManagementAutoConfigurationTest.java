package sh.trishul.tenant.persistence.management.autoconfiguration;

import static java.util.UUID.fromString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.base.types.util.random.RandomGenerator;
import sh.trishul.data.datasource.manager.DataSourceManager;
import sh.trishul.data.datasource.query.runner.DataSourceQueryRunner;
import sh.trishul.model.util.random.RandomGeneratorImpl;
import sh.trishul.tenant.entity.AdminTenant;
import sh.trishul.tenant.entity.TenantData;
import sh.trishul.tenant.persistence.management.migration.manager.MigrationManager;
import sh.trishul.tenant.persistence.management.migration.manager.SequentialMigrationManager;
import sh.trishul.tenant.persistence.management.migration.register.FlywayTenantMigrationRegister;
import sh.trishul.tenant.persistence.management.migration.register.MigrationRegister;
import sh.trishul.tenant.persistence.management.migration.register.TenantRegister;
import sh.trishul.tenant.persistence.management.migration.register.UnifiedTenantRegister;

class TenantPersistenceManagementAutoConfigurationTest {
  private TenantPersistenceManagementAutoConfiguration config;

  @BeforeEach
  void init() {
    config = new TenantPersistenceManagementAutoConfiguration();
  }

  @Test
  void testRandomGenerator_ReturnsInstanceOfRandonGeneratorImpl() throws NoSuchAlgorithmException {
    RandomGenerator rand = config.randomGenerator();
    assertTrue(rand instanceof RandomGeneratorImpl);
  }

  @Test
  void testTenantRegister_ReturnsInstanceOfFlywayTenantRegister() {
    TenantRegister register = config.tenantRegister(null, null, null, null, null, null);
    assertTrue(register instanceof UnifiedTenantRegister);
  }

  @Test
  void testMigrationMgr_ReturnsInstanceOfSequentialMigrationManager() {
    MigrationManager mgr = config.migrationManager(null, null);
    assertTrue(mgr instanceof SequentialMigrationManager);
  }

  @Test
  void testMigrationRegister_ReturnsInstanceOfFlywayMigrationRegister() {
    MigrationRegister register = config.migrationRegister(null, null);
    assertTrue(register instanceof FlywayTenantMigrationRegister);
  }

  @Test
  void testAdminTenant_ReturnsAdminTenantWithSpecifiedDetails() {
    TenantData tenant = config.adminTenant("00000000-0000-0000-0000-000000000001", "Admin Tenant");
    assertTrue(tenant instanceof AdminTenant);
    assertEquals(fromString("00000000-0000-0000-0000-000000000001"), tenant.getId());
    assertEquals("Admin Tenant", tenant.getName());
  }

  @Test
  void testDataSourceQueryRunner_ReturnsDataSourceQueryRunner() {
    DataSourceManager dsManager = mock(DataSourceManager.class);
    DataSourceQueryRunner runner = config.dataSourceQueryRunner(dsManager);
    assertNotNull(runner);
  }
}
