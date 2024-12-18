package io.trishul.tenant.persistence.management.migration.manager;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import io.trishul.tenant.entity.Tenant;
import io.trishul.tenant.persistence.management.migration.register.MigrationRegister;
import io.trishul.tenant.persistence.management.migration.register.TenantRegister;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

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
        order.verify(mTenantReg)
                .put(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
        order.verify(mMigrationReg)
                .migrate(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
    }

    @Test
    public void testMigrateAll_CallsMigrateOnAllTenants() {
        mgr = spy(mgr);

        List<Tenant> tenants =
                List.of(
                        new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")),
                        new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002")));

        mgr.migrateAll(tenants);

        InOrder order = inOrder(mgr);

        order.verify(mgr)
                .migrate(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
        order.verify(mgr)
                .migrate(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002")));
    }
}
