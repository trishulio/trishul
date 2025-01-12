package io.trishul.dialect.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import io.trishul.dialect.JdbcDialect;

public class PostgresJdbcDialect implements JdbcDialect {
  private final PostgresJdbcDialectSql pgSql;

  public PostgresJdbcDialect(PostgresJdbcDialectSql pgSql) {
    this.pgSql = pgSql;
  }

  @Override
  public boolean createSchemaIfNotExists(Connection conn, String schemaName) throws SQLException {
    String sql = this.pgSql.createSchemaIfNotExist(schemaName);
    return update(conn, sql) > 0;
  }

  @Override
  public void createUser(Connection conn, String username, String password) throws SQLException {
    String sql = this.pgSql.createUser(username, password);
    update(conn, sql);
  }

  @Override
  public void grantPrivilege(Connection conn, String privilege, String resourceType,
      String resourceName, String username) throws SQLException {
    String sql = this.pgSql.grantPrivilege(privilege, resourceType, resourceName, username);
    update(conn, sql);
  }

  @Override
  public boolean userExists(Connection conn, String username) throws SQLException {
    String sql = this.pgSql.userExist();
    boolean userExists;
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setObject(1, username);
      try (ResultSet rs = ps.executeQuery()) {
        userExists = rs.next();
      }
    }

    return userExists;
  }

  @Override
  public boolean schemaExists(Connection conn, String schemaName) throws SQLException {
    String sql = this.pgSql.schemaExists();

    boolean exists;
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setObject(1, schemaName);
      try (ResultSet rs = ps.executeQuery()) {
        exists = rs.next();
      }
    }

    return exists;
  }

  @Override
  public void dropSchema(Connection conn, String schemaName) throws SQLException {
    String sql = this.pgSql.dropSchema(schemaName);
    update(conn, sql);
  }

  @Override
  public void dropUser(Connection conn, String username) throws SQLException {
    String sql = this.pgSql.dropUser(username);
    update(conn, sql);
  }

  @Override
  public void dropOwnedBy(Connection conn, String owner) throws SQLException {
    String sql = this.pgSql.dropOwnedBy(owner);
    update(conn, sql);
  }

  @Override
  public void reassignOwnedByTo(Connection conn, String owner, String assignee)
      throws SQLException {
    String sql = this.pgSql.reassignOwnedByTo(owner, assignee);
    update(conn, sql);
  }

  private int update(Connection conn, String sql) throws SQLException {
    int updateCount;
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      updateCount = ps.executeUpdate();
    }

    return updateCount;
  }
}
