package io.trishul.dialect.postgres;

import static io.trishul.test.db.DbMockUtil.mockPs;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import io.trishul.dialect.JdbcDialect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostgresJdbcDialectTest {
  private JdbcDialect dialect;

  private PostgresJdbcDialectSql sql;

  @BeforeEach
  void init() {
    sql = new PostgresJdbcDialectSql();
    dialect = new PostgresJdbcDialect(sql);
  }

  @Test
  void testCreateSchemaIfNotExists_RunsCreateSchemaSqlAndReturnTrue_WhenPsReturnsCount1()
      throws SQLException {
    Connection mConn = mock(Connection.class);
    PreparedStatement mPs = mockPs(mConn, sql.createSchemaIfNotExist("SCHEMA_NAME"), 1);
    boolean created = dialect.createSchemaIfNotExists(mConn, "SCHEMA_NAME");

    assertTrue(created);
    verify(mPs, times(1)).close();
  }

  @Test
  void testCreateSchemaIfNotExists_RunsCreateSchemaSqlAndReturnTrue_WhenPsReturnsCount0()
      throws SQLException {
    Connection mConn = mock(Connection.class);
    PreparedStatement mPs = mockPs(mConn, sql.createSchemaIfNotExist("SCHEMA_NAME"), 0);
    boolean created = dialect.createSchemaIfNotExists(mConn, "SCHEMA_NAME");

    assertFalse(created);
    verify(mPs, times(1)).close();
  }

  @Test
  void testCreateUser_RunsCreateUserSql() throws SQLException {
    Connection mConn = mock(Connection.class);
    PreparedStatement mPs = mockPs(mConn, sql.createUser("TEST_USER", "TEST_PASS"), 1);

    dialect.createUser(mConn, "TEST_USER", "TEST_PASS");

    verify(mPs, times(1)).executeUpdate();
    verify(mPs, times(1)).close();
  }

  @Test
  void testGrantPrivilege_RunsGrantSql() throws SQLException {
    Connection mConn = mock(Connection.class);
    PreparedStatement mPs
        = mockPs(mConn, sql.grantPrivilege("CONNECT", "DATABASE", "DB_1", "USER_1"), 1);

    dialect.grantPrivilege(mConn, "CONNECT", "DATABASE", "DB_1", "USER_1");

    verify(mPs, times(1)).executeUpdate();
    verify(mPs, times(1)).close();
  }

  @Test
  void testUserExist_ReturnsTrue_WhenResultSetHasResults() throws SQLException {
    Connection mConn = mock(Connection.class);
    PreparedStatement mPs = mockPs(mConn, sql.userExist(), new Object[][] {{}});

    boolean b = dialect.userExists(mConn, "USER_1");
    assertTrue(b);

    verify(mPs, times(1)).setObject(1, "USER_1");
    verify(mPs, times(1)).close();
  }

  @Test
  void testUserExist_ReturnsFalse_WhenResultSetHasNoResults() throws SQLException {
    Connection mConn = mock(Connection.class);
    PreparedStatement mPs = mockPs(mConn, sql.userExist(), new Object[][] {});

    boolean b = dialect.userExists(mConn, "USER_1");
    assertFalse(b);

    verify(mPs, times(1)).setObject(1, "USER_1");
    verify(mPs, times(1)).close();
  }

  @Test
  void testSchemaExists_ReturnsTrue_WhenResultSetHasResults() throws SQLException {
    Connection mConn = mock(Connection.class);
    PreparedStatement mPs = mockPs(mConn, sql.schemaExists(), new Object[][] {{}});

    boolean b = dialect.schemaExists(mConn, "SCHEMA_1");
    assertTrue(b);

    verify(mPs, times(1)).setObject(1, "SCHEMA_1");
    verify(mPs, times(1)).close();
  }

  @Test
  void testSchemaExists_ReturnsFalse_WhenResultSetHasNoResults() throws SQLException {
    Connection mConn = mock(Connection.class);
    PreparedStatement mPs = mockPs(mConn, sql.schemaExists(), new Object[][] {});

    boolean b = dialect.schemaExists(mConn, "SCHEMA_1");
    assertFalse(b);

    verify(mPs, times(1)).setObject(1, "SCHEMA_1");
    verify(mPs, times(1)).close();
  }

  @Test
  void testDropSchema_RunsDropSchemaSql() throws Exception {
    Connection mConn = mock(Connection.class);
    PreparedStatement mPs = mockPs(mConn, sql.dropSchema("SCHEMA_1"), 1);

    dialect.dropSchema(mConn, "SCHEMA_1");

    verify(mPs, times(1)).executeUpdate();
    verify(mPs, times(1)).close();
  }

  @Test
  void testDropUser_RunsDropUserSql() throws SQLException {
    Connection mConn = mock(Connection.class);
    PreparedStatement mPs = mockPs(mConn, sql.dropUser("USER_1"), 1);

    dialect.dropUser(mConn, "USER_1");

    verify(mPs, times(1)).executeUpdate();
    verify(mPs, times(1)).close();
  }

  @Test
  void testDropOwned_RunsDropOwnedSql() throws SQLException {
    Connection mConn = mock(Connection.class);
    PreparedStatement mPs = mockPs(mConn, sql.dropOwnedBy("OWNER"), 1);

    dialect.dropOwnedBy(mConn, "OWNER");

    verify(mPs, times(1)).executeUpdate();
    verify(mPs, times(1)).close();
  }

  @Test
  void testReassignOwnedByTo_RunsReassignOwnedSql() throws SQLException {
    Connection mConn = mock(Connection.class);
    PreparedStatement mPs = mockPs(mConn, sql.reassignOwnedByTo("OWNER", "ASSIGNEE"), 1);

    dialect.reassignOwnedByTo(mConn, "OWNER", "ASSIGNEE");

    verify(mPs, times(1)).executeUpdate();
    verify(mPs, times(1)).close();
  }
}
