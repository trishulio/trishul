package sh.trishul.tenant.persistence.connection.provider.pool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TenantDataSourceManagerConnectionProviderTest {
  private DataSource mDs;
  private TenantDataSourceManagerConnectionProvider provider;

  @BeforeEach
  void init() {
    mDs = mock(DataSource.class);
    provider = new TenantDataSourceManagerConnectionProvider(mDs);
  }

  @Test
  void testIsUnwrappableAs_ReturnsFalse() {
    assertFalse(provider.isUnwrappableAs(String.class));
  }

  @Test
  void testUnwrap_ReturnsNull() {
    assertNull(provider.unwrap(String.class));
  }

  @Test
  void testGetConnection_CallsDataSourceGetConnection() throws SQLException {
    Connection mConn = mock(Connection.class);
    when(mDs.getConnection()).thenReturn(mConn);

    Connection conn = provider.getConnection();
    assertEquals(mConn, conn);
  }

  @Test
  void testCloseConnection_ClosesConnection() throws SQLException {
    Connection mConn = mock(Connection.class);
    provider.closeConnection(mConn);
    verify(mConn).close();
  }

  @Test
  void testSupportsAggressiveRelease_ReturnsFalse() {
    assertFalse(provider.supportsAggressiveRelease());
  }
}
