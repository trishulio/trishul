package io.company.brewcraft.migration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.data.CheckedConsumer;
import io.company.brewcraft.data.CheckedSupplier;
import io.company.brewcraft.data.DataSourceConfiguration;
import io.company.brewcraft.data.DataSourceQueryRunner;
import io.company.brewcraft.data.JdbcDialect;
import io.company.brewcraft.data.TenantDataSourceConfigurationProvider;
import io.company.brewcraft.model.Tenant;

@SuppressWarnings("unchecked")
public class TenantSchemaRegisterTest {
    private TenantRegister register;

    private TenantDataSourceConfigurationProvider mConfigProvider;
    private DataSourceConfiguration mConfig;

    private DataSourceQueryRunner mQueryRunner;
    private JdbcDialect mDialect;

    @BeforeEach
    public void init() {
        mConfig = mock(DataSourceConfiguration.class);
        doReturn("SCHEMA").when(mConfig).getSchemaName();

        mConfigProvider = mock(TenantDataSourceConfigurationProvider.class);
        doReturn(mConfig).when(mConfigProvider).getConfiguration(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        mDialect = mock(JdbcDialect.class);

        mQueryRunner = mock(DataSourceQueryRunner.class);

        register = new TenantSchemaRegister(mConfigProvider, mQueryRunner, mDialect);
    }

    @Test
    public void testAdd_RunsCreateSchema() throws Exception {
        Connection mConn = mock(Connection.class);
        doAnswer(inv -> {
            CheckedConsumer<Connection, Exception> consumer = inv.getArgument(1, CheckedConsumer.class);
            consumer.run(mConn);
            return null;
        }).when(mQueryRunner).query(eq(mConfig), any(CheckedConsumer.class));

        register.add(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        verify(mDialect).createSchemaIfNotExists(mConn, "SCHEMA");
        verify(mConn).commit();
    }

    @Test
    public void testPut_RunsAdd_WhenExistIsFalse() throws Exception {
        register = spy(register);
        doReturn(false).when(register).exists(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        doNothing().when(register).add(any());

        register.put(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        verify(register, times(1)).add(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
    }

    @Test
    public void testPut_DoesNothing_WhenExistIsTrue() throws Exception {
        register = spy(register);
        doReturn(true).when(register).exists(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        verify(register, times(0)).add(any());
    }

    @Test
    public void testRemove_CallsDropSchema() throws Exception {
        Connection mConn = mock(Connection.class);
        doAnswer(inv -> {
            CheckedConsumer<Connection, Exception> consumer = inv.getArgument(1, CheckedConsumer.class);
            consumer.run(mConn);
            return null;
        }).when(mQueryRunner).query(eq(mConfig), any(CheckedConsumer.class));

        register.remove(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        verify(mDialect).dropSchema(mConn, "SCHEMA");
        verify(mConn).commit();
    }

    @Test
    public void testExists_ReturnsTrue_WhenDialectReturnsTrue() throws Exception {
        Connection mConn = mock(Connection.class);
        doAnswer(inv -> {
            CheckedSupplier<Boolean, Connection, Exception> supplier = inv.getArgument(1, CheckedSupplier.class);

            return supplier.get(mConn);
        }).when(mQueryRunner).query(eq(mConfig), any(CheckedSupplier.class));

        doReturn(true).when(mDialect).schemaExists(mConn, "SCHEMA");

        boolean b = register.exists(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        assertTrue(b);
    }

    @Test
    public void testExists_ReturnsFalse_WhenDialectReturnsFalse() throws Exception {
        Connection mConn = mock(Connection.class);
        doAnswer(inv -> {
            CheckedSupplier<Boolean, Connection, Exception> supplier = inv.getArgument(1, CheckedSupplier.class);

            return supplier.get(mConn);
        }).when(mQueryRunner).query(eq(mConfig), any(CheckedSupplier.class));

        doReturn(false).when(mDialect).schemaExists(mConn, "SCHEMA");

        boolean b = register.exists(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        assertFalse(b);
    }
}
