package io.trishul.dialect;

import java.sql.Connection;
import java.sql.SQLException;

public interface JdbcDialect {
  boolean createSchemaIfNotExists(Connection conn, String schemaName) throws SQLException;

  void dropSchema(Connection conn, String schemaName) throws SQLException;

  void createUser(Connection conn, String username, String password) throws SQLException;

  void dropUser(Connection conn, String username) throws SQLException;

  void dropOwnedBy(Connection conn, String owner) throws SQLException;

  void reassignOwnedByTo(Connection conn, String owner, String assignee) throws SQLException;

  void grantPrivilege(Connection conn, String privilege, String resourceType, String resourceName,
      String username) throws SQLException;

  boolean userExists(Connection conn, String username) throws SQLException;

  boolean schemaExists(Connection conn, String schemaName) throws SQLException;
}
