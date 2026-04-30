package io.trishul.data.datasource.query.runner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.trishul.base.types.lambda.CheckedConsumer;
import io.trishul.base.types.lambda.CheckedSupplier;
import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import io.trishul.data.datasource.manager.DataSourceManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataSourceQueryRunnerTest {
  private DataSourceQueryRunner queryRunner;

  private DataSourceManager mDsManager;
  private DataSource mDataSource;
  private DataSource mAdminDataSource;
  private Connection mConnection;
  private DataSourceConfiguration mDsConfig;

  @BeforeEach
  void init() throws SQLException {
    mDsManager = mock(DataSourceManager.class);
    mDataSource = mock(DataSource.class);
    mAdminDataSource = mock(DataSource.class);
    mConnection = mock(Connection.class);
    mDsConfig = mock(DataSourceConfiguration.class);

    when(mDataSource.getConnection()).thenReturn(mConnection);
    when(mAdminDataSource.getConnection()).thenReturn(mConnection);
    when(mDsManager.getAdminDataSource()).thenReturn(mAdminDataSource);

    queryRunner = new DataSourceQueryRunner(mDsManager);
  }

  @Test
  void testQueryWithConfigAndSupplier_ReturnsSupplierResult() throws SQLException, IOException {
    String expected = "result";
    when(mDsManager.getDataSource(mDsConfig)).thenReturn(mDataSource);

    String result = queryRunner.query(mDsConfig, conn -> expected);

    assertEquals(expected, result);
  }

  @Test
  void testQueryWithConfigAndSupplier_PassesConnectionToSupplier() throws SQLException, IOException {
    when(mDsManager.getDataSource(mDsConfig)).thenReturn(mDataSource);

    Connection[] capturedConnection = new Connection[1];
    queryRunner.query(mDsConfig, conn -> {
      capturedConnection[0] = conn;
      return "result";
    });

    assertEquals(mConnection, capturedConnection[0]);
  }

  @Test
  void testQueryWithConfigAndSupplier_ClosesConnectionAfterSuccess() throws SQLException, IOException {
    when(mDsManager.getDataSource(mDsConfig)).thenReturn(mDataSource);

    queryRunner.query(mDsConfig, conn -> "result");

    verify(mConnection).close();
  }

  @Test
  void testQueryWithConfigAndSupplier_ThrowsRuntimeException_WhenGetDataSourceThrowsSQLException()
      throws SQLException, IOException {
    when(mDsManager.getDataSource(mDsConfig)).thenThrow(new SQLException("Connection failed"));

    RuntimeException ex = assertThrows(RuntimeException.class,
        () -> queryRunner.query(mDsConfig, conn -> "result"));

    assertNotNull(ex.getCause());
    assertEquals(SQLException.class, ex.getCause().getClass());
  }

  @Test
  void testQueryWithConfigAndSupplier_ThrowsRuntimeException_WhenGetDataSourceThrowsIOException()
      throws SQLException, IOException {
    when(mDsManager.getDataSource(mDsConfig)).thenThrow(new IOException("IO failed"));

    RuntimeException ex = assertThrows(RuntimeException.class,
        () -> queryRunner.query(mDsConfig, conn -> "result"));

    assertNotNull(ex.getCause());
    assertEquals(IOException.class, ex.getCause().getClass());
  }

  @Test
  void testQueryWithConfigAndSupplier_RollsBackAndThrows_WhenSupplierThrowsException()
      throws SQLException, IOException {
    when(mDsManager.getDataSource(mDsConfig)).thenReturn(mDataSource);
    CheckedSupplier<String, Connection, Exception> failingSupplier = conn -> {
      throw new RuntimeException("Supplier failed");
    };

    RuntimeException ex = assertThrows(RuntimeException.class, () -> queryRunner.query(mDsConfig, failingSupplier));

    verify(mConnection).rollback();
    verify(mConnection).close();
    assertNotNull(ex.getCause());
  }

  @Test
  void testQueryWithConfigAndSupplier_ClosesConnection_WhenRollbackFails()
      throws SQLException, IOException {
    when(mDsManager.getDataSource(mDsConfig)).thenReturn(mDataSource);
    doThrow(new SQLException("Rollback failed")).when(mConnection).rollback();
    CheckedSupplier<String, Connection, Exception> failingSupplier = conn -> {
      throw new RuntimeException("Supplier failed");
    };

    assertThrows(RuntimeException.class, () -> queryRunner.query(mDsConfig, failingSupplier));

    verify(mConnection).close();
  }

  @Test
  void testQueryWithSupplier_ReturnsSupplierResult() {
    String expected = "admin_result";

    String result = queryRunner.query(conn -> expected);

    assertEquals(expected, result);
  }

  @Test
  void testQueryWithSupplier_UsesAdminDataSource() throws SQLException {
    queryRunner.query(conn -> "result");

    verify(mDsManager).getAdminDataSource();
    verify(mAdminDataSource).getConnection();
  }

  @Test
  void testQueryWithSupplier_ClosesConnectionAfterSuccess() throws SQLException {
    queryRunner.query(conn -> "result");

    verify(mConnection).close();
  }

  @Test
  void testQueryWithSupplier_RollsBackAndThrows_WhenSupplierThrowsException() throws SQLException {
    CheckedSupplier<String, Connection, Exception> failingSupplier = conn -> {
      throw new RuntimeException("Supplier failed");
    };

    RuntimeException ex = assertThrows(RuntimeException.class, () -> queryRunner.query(failingSupplier));

    verify(mConnection).rollback();
    verify(mConnection).close();
    assertNotNull(ex.getCause());
  }

  @Test
  void testQueryWithConfigAndConsumer_ExecutesConsumer() throws SQLException, IOException {
    when(mDsManager.getDataSource(mDsConfig)).thenReturn(mDataSource);

    boolean[] executed = { false };
    CheckedConsumer<Connection, Exception> consumer = conn -> {
      executed[0] = true;
    };
    queryRunner.query(mDsConfig, consumer);

    assertTrue(executed[0]);
  }

  @Test
  void testQueryWithConfigAndConsumer_PassesConnectionToConsumer() throws SQLException, IOException {
    when(mDsManager.getDataSource(mDsConfig)).thenReturn(mDataSource);

    Connection[] capturedConnection = new Connection[1];
    CheckedConsumer<Connection, Exception> consumer = conn -> {
      capturedConnection[0] = conn;
    };
    queryRunner.query(mDsConfig, consumer);

    assertEquals(mConnection, capturedConnection[0]);
  }

  @Test
  void testQueryWithConfigAndConsumer_ClosesConnectionAfterSuccess() throws SQLException, IOException {
    when(mDsManager.getDataSource(mDsConfig)).thenReturn(mDataSource);

    CheckedConsumer<Connection, Exception> consumer = conn -> {
    };
    queryRunner.query(mDsConfig, consumer);

    verify(mConnection).close();
  }

  @Test
  void testQueryWithConfigAndConsumer_ThrowsRuntimeException_WhenGetDataSourceThrowsSQLException()
      throws SQLException, IOException {
    when(mDsManager.getDataSource(mDsConfig)).thenThrow(new SQLException("Connection failed"));
    CheckedConsumer<Connection, Exception> consumer = conn -> {
    };

    RuntimeException ex = assertThrows(RuntimeException.class,
        () -> queryRunner.query(mDsConfig, consumer));

    assertNotNull(ex.getCause());
    assertEquals(SQLException.class, ex.getCause().getClass());
  }

  @Test
  void testQueryWithConfigAndConsumer_ThrowsRuntimeException_WhenGetDataSourceThrowsIOException()
      throws SQLException, IOException {
    when(mDsManager.getDataSource(mDsConfig)).thenThrow(new IOException("IO failed"));
    CheckedConsumer<Connection, Exception> consumer = conn -> {
    };

    RuntimeException ex = assertThrows(RuntimeException.class,
        () -> queryRunner.query(mDsConfig, consumer));

    assertNotNull(ex.getCause());
    assertEquals(IOException.class, ex.getCause().getClass());
  }

  @Test
  void testQueryWithConfigAndConsumer_RollsBackAndThrows_WhenConsumerThrowsException()
      throws SQLException, IOException {
    when(mDsManager.getDataSource(mDsConfig)).thenReturn(mDataSource);
    CheckedConsumer<Connection, Exception> failingConsumer = conn -> {
      throw new RuntimeException("Consumer failed");
    };

    RuntimeException ex = assertThrows(RuntimeException.class, () -> queryRunner.query(mDsConfig, failingConsumer));

    verify(mConnection).rollback();
    verify(mConnection).close();
    assertNotNull(ex.getCause());
  }

  @Test
  void testQueryWithConfigAndConsumer_ClosesConnection_WhenRollbackFails()
      throws SQLException, IOException {
    when(mDsManager.getDataSource(mDsConfig)).thenReturn(mDataSource);
    doThrow(new SQLException("Rollback failed")).when(mConnection).rollback();
    CheckedConsumer<Connection, Exception> failingConsumer = conn -> {
      throw new RuntimeException("Consumer failed");
    };

    assertThrows(RuntimeException.class, () -> queryRunner.query(mDsConfig, failingConsumer));

    verify(mConnection).close();
  }

  @Test
  void testQueryWithConsumer_ExecutesConsumer() {
    boolean[] executed = { false };
    CheckedConsumer<Connection, Exception> consumer = conn -> {
      executed[0] = true;
    };
    queryRunner.query(consumer);

    assertTrue(executed[0]);
  }

  @Test
  void testQueryWithConsumer_UsesAdminDataSource() throws SQLException {
    CheckedConsumer<Connection, Exception> consumer = conn -> {
    };
    queryRunner.query(consumer);

    verify(mDsManager).getAdminDataSource();
    verify(mAdminDataSource).getConnection();
  }

  @Test
  void testQueryWithConsumer_ClosesConnectionAfterSuccess() throws SQLException {
    CheckedConsumer<Connection, Exception> consumer = conn -> {
    };
    queryRunner.query(consumer);

    verify(mConnection).close();
  }

  @Test
  void testQueryWithConsumer_RollsBackAndThrows_WhenConsumerThrowsException() throws SQLException {
    CheckedConsumer<Connection, Exception> failingConsumer = conn -> {
      throw new RuntimeException("Consumer failed");
    };

    RuntimeException ex = assertThrows(RuntimeException.class, () -> queryRunner.query(failingConsumer));

    verify(mConnection).rollback();
    verify(mConnection).close();
    assertNotNull(ex.getCause());
  }

  // Edge case: connection is null (getConnection returns null)

  @Test
  void testQueryWithSupplier_DoesNotRollback_WhenConnectionIsNull() throws SQLException {
    when(mAdminDataSource.getConnection()).thenReturn(null);
    CheckedSupplier<String, Connection, Exception> failingSupplier = conn -> {
      throw new RuntimeException("Fail");
    };

    assertThrows(RuntimeException.class, () -> queryRunner.query(failingSupplier));

    verify(mConnection, never()).rollback();
  }

  @Test
  void testQueryWithConsumer_DoesNotRollback_WhenConnectionIsNull() throws SQLException {
    when(mAdminDataSource.getConnection()).thenReturn(null);
    CheckedConsumer<Connection, Exception> failingConsumer = conn -> {
      throw new RuntimeException("Fail");
    };

    assertThrows(RuntimeException.class, () -> queryRunner.query(failingConsumer));

    verify(mConnection, never()).rollback();
  }

  // Edge case: close throws exception (should be logged but not rethrown)

  @Test
  void testQueryWithSupplier_CompletesSuccessfully_WhenCloseThrowsException() throws SQLException {
    doThrow(new SQLException("Close failed")).when(mConnection).close();

    // Should not throw - close exception is logged
    String result = queryRunner.query(conn -> "result");

    assertEquals("result", result);
    verify(mConnection).close();
  }

  @Test
  void testQueryWithConsumer_CompletesSuccessfully_WhenCloseThrowsException() throws SQLException {
    doThrow(new SQLException("Close failed")).when(mConnection).close();

    boolean[] executed = { false };
    CheckedConsumer<Connection, Exception> consumer = conn -> {
      executed[0] = true;
    };
    // Should not throw - close exception is logged
    queryRunner.query(consumer);

    assertTrue(executed[0]);
    verify(mConnection).close();
  }
}
