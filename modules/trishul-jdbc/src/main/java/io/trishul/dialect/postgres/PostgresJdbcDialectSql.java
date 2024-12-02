package io.trishul.dialect.postgres;

public class PostgresJdbcDialectSql {
    public String createSchemaIfNotExist(String schemaName) {
        return f("CREATE SCHEMA IF NOT EXISTS %s", schemaName);
    }

    public String createUser(String username, String password) {
        return f("CREATE USER %s PASSWORD '%s'", username, password);
    }

    public String grantPrivilege(String privilege, String resourceType, String resourceName, String username) {
        return f("GRANT %s ON %s %s TO %s", privilege, resourceType, resourceName, username);
    }

    public String userExist() {
        return f("SELECT 1 FROM pg_roles WHERE rolname = %s", placeholders(1));
    }

    public String schemaExists() {
        return f("SELECT schema_name FROM information_schema.schemata WHERE schema_name = %s", placeholders(1));
    }

    public String dropUser(String username) {
        return f("DROP USER %s", username);
    }

    public String dropSchema(String schemaName) {
        return f("DROP SCHEMA %s", schemaName);
    }

    public String reassignOwnedByTo(String owner, String assignee) {
        return f("REASSIGN OWNED BY %s TO %s", owner, assignee);
    }

    public String dropOwnedBy(String owner) {
        return f("DROP OWNED BY %s", owner);
    }

    private String placeholders(int count) {
        final String ph = "?,";
        String raw = ph.repeat(count);
        String finul = raw.substring(0, raw.length() - 1);
        return finul;
    }

    private String f(String s, Object... args) {
        return String.format(s, args);
    }
}
