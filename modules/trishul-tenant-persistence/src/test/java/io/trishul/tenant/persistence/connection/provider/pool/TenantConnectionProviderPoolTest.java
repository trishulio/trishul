package io.trishul.tenant.persistence.connection.provider.pool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import io.trishul.tenant.persistence.datasource.manager.TenantDataSourceManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.cache.LoadingCache;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutionException;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TenantConnectionProviderPoolTest {
  private DataSource mAdminDs;
  private TenantDataSourceManager mDsMgr;

  private TenantConnectionProviderPool providerPool;

  @BeforeEach
  void init() throws SQLException {
    mAdminDs = mock(DataSource.class);
    mDsMgr = mock(TenantDataSourceManager.class);

    providerPool = new TenantConnectionProviderPool(mDsMgr, mAdminDs);
  }

  @Test
  void testGetAnyConnectionProvider_ReturnsAdminConnectionProvider() throws SQLException {
    Connection mConn = mock(Connection.class);
    doReturn(mConn).when(mAdminDs).getConnection();

    Connection conn = providerPool.getAnyConnectionProvider().getConnection();

    assertEquals(mConn, conn);
  }

  @Test
  void testSelectConnectionProvider_ReturnsConnectionProviderWithTenantDs()
      throws SQLException, IOException {
    DataSource mDs = mock(DataSource.class);
    doReturn(mDs).when(mDsMgr)
        .getDataSource(UUID.fromString("00000000-0000-0000-0000-000000000001"));

    Connection mConn = mock(Connection.class);
    doReturn(mConn).when(mDs).getConnection();

    Connection conn = providerPool.selectConnectionProvider("00000000-0000-0000-0000-000000000001")
        .getConnection();
    assertEquals(mConn, conn);
  }

  @Test
  void testSelectConnectionProvider_ThrowsRuntimeException_WhenCacheThrowsExecutionExceptionWithSQLException()
      throws Exception {
    Field field = providerPool.getClass().getDeclaredField("cache");
    field.setAccessible(true);
    LoadingCache<String, ConnectionProvider> mockCache = mock(LoadingCache.class);
    field.set(providerPool, mockCache);

    SQLException sqlException = new SQLException("DB error");
    doThrow(new ExecutionException("Execution error", sqlException)).when(mockCache)
        .get("00000000-0000-0000-0000-000000000001");

    RuntimeException ex = assertThrows(RuntimeException.class, () -> {
      providerPool.selectConnectionProvider("00000000-0000-0000-0000-000000000001");
    });
    assertEquals("Failed to fetch datasource from DataSourceManager", ex.getMessage());
    assertEquals(sqlException, ex.getCause());
  }

  @Test
  void testSelectConnectionProvider_ThrowsRuntimeException_WhenCacheThrowsExecutionExceptionWithIOException()
      throws Exception {
    Field field = providerPool.getClass().getDeclaredField("cache");
    field.setAccessible(true);
    LoadingCache<String, ConnectionProvider> mockCache = mock(LoadingCache.class);
    field.set(providerPool, mockCache);

    IOException ioException = new IOException("IO error");
    doThrow(new ExecutionException("Execution error", ioException)).when(mockCache)
        .get("00000000-0000-0000-0000-000000000001");

    RuntimeException ex = assertThrows(RuntimeException.class, () -> {
      providerPool.selectConnectionProvider("00000000-0000-0000-0000-000000000001");
    });
    assertEquals("Failed to fetch datasource from DataSourceManager", ex.getMessage());
    assertEquals(ioException, ex.getCause());
  }

  @Test
  void testSelectConnectionProvider_ThrowsRuntimeException_WhenCacheThrowsExecutionExceptionWithOtherException()
      throws Exception {
    Field field = providerPool.getClass().getDeclaredField("cache");
    field.setAccessible(true);
    LoadingCache<String, ConnectionProvider> mockCache = mock(LoadingCache.class);
    field.set(providerPool, mockCache);

    RuntimeException otherException = new RuntimeException("Other error");
    doThrow(new ExecutionException("Execution error", otherException)).when(mockCache)
        .get("00000000-0000-0000-0000-000000000001");

    RuntimeException ex = assertThrows(RuntimeException.class, () -> {
      providerPool.selectConnectionProvider("00000000-0000-0000-0000-000000000001");
    });
    assertEquals(otherException, ex.getCause());
  }
}
