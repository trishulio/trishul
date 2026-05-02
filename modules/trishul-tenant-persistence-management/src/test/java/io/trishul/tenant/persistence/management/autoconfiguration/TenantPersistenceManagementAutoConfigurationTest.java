package io.trishul.tenant.persistence.management.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import io.trishul.base.types.util.random.RandomGenerator;
import io.trishul.model.util.random.RandomGeneratorImpl;
import io.trishul.tenant.persistence.management.migration.manager.MigrationManager;
import io.trishul.tenant.persistence.management.migration.manager.SequentialMigrationManager;
import io.trishul.tenant.persistence.management.migration.register.FlywayTenantMigrationRegister;
import io.trishul.tenant.persistence.management.migration.register.MigrationRegister;
import io.trishul.tenant.persistence.management.migration.register.TenantRegister;
import io.trishul.tenant.persistence.management.migration.register.UnifiedTenantRegister;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
