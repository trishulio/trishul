package io.company.brewcraft.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantConnectionProviderPoolTest {
    private DataSource mAdminDs;
    private TenantDataSourceManager mDsMgr;

    private TenantConnectionProviderPool providerPool;

    @BeforeEach
    public void init() throws SQLException {
        mAdminDs = mock(DataSource.class);
        mDsMgr = mock(TenantDataSourceManager.class);

        providerPool = new TenantConnectionProviderPool(mDsMgr, mAdminDs);
    }

    @Test
    public void testGetAnyConnectionProvider_ReturnsAdminConnectionProvider() throws SQLException {
        Connection mConn = mock(Connection.class);
        doReturn(mConn).when(mAdminDs).getConnection();

        Connection conn = providerPool.getAnyConnectionProvider().getConnection();

        assertEquals(mConn, conn);
    }

    @Test
    public void testSelectConnectionProvider_ReturnsConnectionProviderWithTenantDs() throws SQLException, IOException {
        DataSource mDs = mock(DataSource.class);
        doReturn(mDs).when(mDsMgr).getDataSource(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        Connection mConn = mock(Connection.class);
        doReturn(mConn).when(mDs).getConnection();

        Connection conn = providerPool.selectConnectionProvider("00000000-0000-0000-0000-000000000001").getConnection();
        assertEquals(mConn, conn);
    }
}
