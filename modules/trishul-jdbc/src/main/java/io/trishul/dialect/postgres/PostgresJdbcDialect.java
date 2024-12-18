package io.trishul.dialect.postgres;

import io.trishul.dialect.JdbcDialect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public void grantPrivilege(
            Connection conn,
            String privilege,
            String resourceType,
            String resourceName,
            String username)
            throws SQLException {
        String sql = this.pgSql.grantPrivilege(privilege, resourceType, resourceName, username);
        update(conn, sql);
    }

    @Override
    public boolean userExists(Connection conn, String username) throws SQLException {
        String sql = this.pgSql.userExist();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setObject(1, username);

        ResultSet rs = ps.executeQuery();
        boolean userExists = rs.next();

        rs.close();
        ps.close();

        return userExists;
    }

    @Override
    public boolean schemaExists(Connection conn, String schemaName) throws SQLException {
        String sql = this.pgSql.schemaExists();

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setObject(1, schemaName);

        ResultSet rs = ps.executeQuery();
        boolean exists = rs.next();

        rs.close();
        ps.close();

        return exists;
    }

    @Override
    public void dropSchema(Connection conn, String schemaName) throws SQLException {
        String sql = this.pgSql.dropSchema(schemaName);
        int count = update(conn, sql);
    }

    @Override
    public void dropUser(Connection conn, String username) throws SQLException {
        String sql = this.pgSql.dropUser(username);
        int count = update(conn, sql);
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
        PreparedStatement ps = conn.prepareStatement(sql);
        int updateCount = ps.executeUpdate();

        ps.close();

        return updateCount;
    }
}
