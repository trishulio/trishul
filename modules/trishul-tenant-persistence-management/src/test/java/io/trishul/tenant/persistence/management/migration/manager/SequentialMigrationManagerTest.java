package io.company.brewcraft.migration;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import io.company.brewcraft.model.Tenant;

@SuppressWarnings("unchecked")
public class SequentialMigrationManagerTest {
    private MigrationManager mgr;

    private TenantRegister mTenantReg;
    private MigrationRegister mMigrationReg;

    @BeforeEach
    public void init() {
        mTenantReg = mock(TenantRegister.class);
        mMigrationReg = mock(MigrationRegister.class);

        mgr = new SequentialMigrationManager(mTenantReg, mMigrationReg);
    }

    @Test
    public void testMigrate_PutsTenantInTenantRegisterAndMigrationRegister() {
        mgr.migrate(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        InOrder order = inOrder(mTenantReg, mMigrationReg);
        order.verify(mTenantReg).put(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
        order.verify(mMigrationReg).migrate(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
    }

    @Test
    public void testMigrateAll_CallsMigrateOnAllTenants() {
        mgr = spy(mgr);

        List<Tenant> tenants = List.of(
            new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")),
            new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002"))
        );

        mgr.migrateAll(tenants);

        InOrder order = inOrder(mgr);

        order.verify(mgr).migrate(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
        order.verify(mgr).migrate(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002")));
    }
}
