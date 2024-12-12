package io.company.brewcraft.migration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MigrationAutoConfigurationTest {
    private MigrationAutoConfiguration config;

    @BeforeEach
    public void init() {
        config = new MigrationAutoConfiguration();
    }

    @Test
    public void testRandomGenerator_ReturnsInstanceOfRandonGeneratorImpl() throws NoSuchAlgorithmException {
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
