package io.company.brewcraft;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.data.PostgresJdbcDialectSql;

public class PostgresJdbcDialectSqlTest {
    private PostgresJdbcDialectSql pgSql;

    @BeforeEach
    public void init() {
        pgSql = new PostgresJdbcDialectSql();
    }

    @Test
    public void testCreateSchemaIfNotExists_ReturnsSqlWithSchemaName() {
        String sql = pgSql.createSchemaIfNotExist("SCHEMA_NAME");
        assertEquals("CREATE SCHEMA IF NOT EXISTS SCHEMA_NAME", sql);
    }

    @Test
    public void testCreateUser_ReturnsSqlWithUsernameAndPassword() {
        String sql = pgSql.createUser("TEST_USER", "TEST_PASS");
        assertEquals("CREATE USER TEST_USER PASSWORD 'TEST_PASS'", sql);
    }

    @Test
    public void testGrantPrivilege_ReturnsPrivilegeSql_WithValues() {
        String sql = pgSql.grantPrivilege("CONNECT", "DATABASE", "DB_1", "USER_1");
        assertEquals("GRANT CONNECT ON DATABASE DB_1 TO USER_1", sql);
    }

    @Test
    public void testUserExist_ReturnsSqlWithPlaceholder() {
        String sql = pgSql.userExist();
        assertEquals("SELECT 1 FROM pg_roles WHERE rolname = ?", sql);
    }

    @Test
    public void testSchemaExists_ReturnsSqlWithPlaceholder() {
        String sql = pgSql.schemaExists();
        assertEquals("SELECT schema_name FROM information_schema.schemata WHERE schema_name = ?", sql);
    }

    @Test
    public void testDropUser_ReturnsSqlWithUsername() {
        String sql = pgSql.dropUser("USER_1");
        assertEquals("DROP USER USER_1", sql);
    }

    @Test
    public void testDropSchema_ReturnsSqlWithSchemaName() {
        String sql = pgSql.dropSchema("SCHEMA_1");
        assertEquals("DROP SCHEMA SCHEMA_1", sql);
    }

    @Test
    public void testReassignOwnedByTo_ReturnsSqlWithOwnerAndAssignee() {
        String sql = pgSql.reassignOwnedByTo("OWNER", "ASSIGNEE");
        assertEquals("REASSIGN OWNED BY OWNER TO ASSIGNEE", sql);
    }

    @Test
    public void testDropOwned_ReturnsSqlWithOwner() {
        String sql = pgSql.dropOwnedBy("OWNER");
        assertEquals("DROP OWNED BY OWNER", sql);
    }
}
