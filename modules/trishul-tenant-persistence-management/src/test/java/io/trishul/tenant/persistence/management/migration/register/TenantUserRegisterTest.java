package io.company.brewcraft.migration;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import io.company.brewcraft.data.CheckedConsumer;
import io.company.brewcraft.data.CheckedSupplier;
import io.company.brewcraft.data.DataSourceConfiguration;
import io.company.brewcraft.data.DataSourceQueryRunner;
import io.company.brewcraft.data.JdbcDialect;
import io.company.brewcraft.data.TenantDataSourceConfigurationProvider;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.security.store.SecretsManager;

@SuppressWarnings("unchecked")
public class TenantUserRegisterTest {
    private TenantRegister register;

    private DataSourceQueryRunner mQueryRunner;
    private TenantDataSourceConfigurationProvider mConfigProvider;
    private DataSourceConfiguration mConfig;
    private DataSourceConfiguration mAdminDsConfig;
    private SecretsManager<String, String> mSecretsMgr;
    private JdbcDialect mDialect;
    private RandomGenerator mRand;

    @BeforeEach
    public void init() {
        mQueryRunner = mock(DataSourceQueryRunner.class);
        mSecretsMgr = mock(SecretsManager.class);
        mDialect = mock(JdbcDialect.class);
        mRand = mock(RandomGenerator.class);

        mAdminDsConfig = mock(DataSourceConfiguration.class);
        doReturn("ADMIN").when(mAdminDsConfig).getUserName();

        mConfig = mock(DataSourceConfiguration.class);
        doReturn("DBNAME").when(mConfig).getDbName();
        doReturn("USERNAME").when(mConfig).getUserName();
        doReturn("SCHEMA").when(mConfig).getSchemaName();

        mConfigProvider = mock(TenantDataSourceConfigurationProvider.class);
        doReturn(mConfig).when(mConfigProvider).getConfiguration(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        register = new TenantUserRegister(mQueryRunner, mConfigProvider, mAdminDsConfig, mSecretsMgr, mDialect, mRand);
    }

    @Test
    public void testAdd_CreatesUserAndSavesPassword() throws SQLException, IOException {
        Connection mConn = mock(Connection.class);
        doAnswer(inv -> {
            CheckedConsumer<Connection, Exception> consumer = inv.getArgument(0, CheckedConsumer.class);
            consumer.run(mConn);
            return null;
        }).when(mQueryRunner).query(any(CheckedConsumer.class));

        doReturn("PASSWORD").when(mRand).string(TenantUserRegister.PASSWORD_LENGTH);

        register.add(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        InOrder order = inOrder(mDialect, mSecretsMgr);
        order.verify(mDialect).createUser(mConn, "USERNAME", "PASSWORD");
        order.verify(mDialect).grantPrivilege(mConn, "CONNECT", "DATABASE", "DBNAME", "USERNAME");
        order.verify(mDialect).grantPrivilege(mConn, "CREATE", "DATABASE", "DBNAME", "USERNAME");
        order.verify(mSecretsMgr).put("SCHEMA", "PASSWORD");
    }

    @Test
    public void testPut_DoesNothing_WhenExistReturnsTrue() {
        register = spy(register);

        doReturn(true).when(register).exists(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        register.put(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        verify(register, times(0)).add(any(Tenant.class));
    }

    @Test
    public void testPut_CallsAdd_WhenExistsReturnsFalse() {
        register = spy(register);

        doReturn(false).when(register).exists(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        register.put(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        verify(register, times(1)).add(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
    }

    @Test
    public void testRemove_DropsSchema_WhenRemoveIsCalled() throws IOException, SQLException {
        Connection mConn = mock(Connection.class);
        doAnswer(inv -> {
            CheckedConsumer<Connection, Exception> consumer = inv.getArgument(0, CheckedConsumer.class);
            consumer.run(mConn);
            return null;
        }).when(mQueryRunner).query(any(CheckedConsumer.class));

        register.remove(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        InOrder order = inOrder(mDialect, mSecretsMgr);
        order.verify(mDialect).reassignOwnedByTo(mConn, "USERNAME", "ADMIN");
        order.verify(mDialect).dropOwnedBy(mConn, "USERNAME");
        order.verify(mDialect).dropUser(mConn, "USERNAME");
        order.verify(mSecretsMgr).remove("SCHEMA");
    }

    @Test
    public void testExists_ReturnsTrue_WhenDialectReturnsTrue() throws IOException, SQLException {
        Connection mConn = mock(Connection.class);
        doAnswer(inv -> {
            CheckedSupplier<Boolean, Connection, Exception> supplier = inv.getArgument(0, CheckedSupplier.class);
            return supplier.get(mConn);
        }).when(mQueryRunner).query(any(CheckedSupplier.class));

        doReturn(true).when(mDialect).userExists(mConn, "USERNAME");

        boolean b = register.exists(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        assertTrue(b);
    }

    @Test
    public void testExists_ReturnsFalse_WhenDialectReturnsFalse() throws IOException, SQLException {
        Connection mConn = mock(Connection.class);
        doAnswer(inv -> {
            CheckedSupplier<Boolean, Connection, Exception> supplier = inv.getArgument(0, CheckedSupplier.class);
            return supplier.get(mConn);
        }).when(mQueryRunner).query(any(CheckedSupplier.class));

        doReturn(false).when(mDialect).userExists(mConn, "USERNAME");

        boolean b = register.exists(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        assertFalse(b);
    }
}
