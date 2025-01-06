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

public class TenantPersistenceManagementAutoConfigurationTest {
  private TenantPersistenceManagementAutoConfiguration config;

  @BeforeEach
  public void init() {
    config = new TenantPersistenceManagementAutoConfiguration();
  }

  @Test
  public void testRandomGenerator_ReturnsInstanceOfRandonGeneratorImpl()
      throws NoSuchAlgorithmException {
    RandomGenerator rand = config.randomGenerator();
    assertTrue(rand instanceof RandomGeneratorImpl);
  }

  @Test
  public void testTenantRegister_ReturnsInstanceOfFlywayTenantRegister() {
    TenantRegister register = config.tenantRegister(null, null, null, null, null, null);
    assertTrue(register instanceof UnifiedTenantRegister);
  }

  @Test
  public void testMigrationMgr_ReturnsInstanceOfSequentialMigrationManager() {
    MigrationManager mgr = config.migrationMgr(null, null);
    assertTrue(mgr instanceof SequentialMigrationManager);
  }

  @Test
  public void testMigrationRegister_ReturnsInstanceOfFlywayMigrationRegister() {
    MigrationRegister register = config.migrationReg(null, null);
    assertTrue(register instanceof FlywayTenantMigrationRegister);
  }
}
