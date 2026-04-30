# trishul-jdbc

JDBC dialect abstraction for database-specific operations like schema and user management.

> **Use this when**: You need to execute database-specific DDL operations (create schemas, users, grant privileges) for multi-tenant provisioning.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-jdbc</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Create a dialect for your database
JdbcDialect dialect = new PostgresJdbcDialect(new PostgresJdbcDialectSql());

// 2. Use it to manage tenant schemas
try (Connection conn = adminDataSource.getConnection()) {
    // Create tenant schema
    dialect.createSchemaIfNotExists(conn, "tenant_123");
    
    // Create tenant user with credentials
    dialect.createUser(conn, "tenant_123_user", "secure_password");
    
    // Grant schema access
    dialect.grantPrivilege(conn, "ALL", "SCHEMA", "tenant_123", "tenant_123_user");
}
```

## When Would I Use This?

### When you need to create tenant schemas dynamically

Use `JdbcDialect.createSchemaIfNotExists()`:

```java
public void provisionTenantSchema(UUID tenantId) throws SQLException {
    String schemaName = "tenant_" + tenantId.toString().replace("-", "");
    
    try (Connection conn = adminDataSource.getConnection()) {
        boolean created = dialect.createSchemaIfNotExists(conn, schemaName);
        if (created) {
            log.info("Created schema: {}", schemaName);
        }
    }
}
```

### When you need to create database users for tenants

Use `JdbcDialect.createUser()`:

```java
public void createTenantDbUser(String username, String password) throws SQLException {
    try (Connection conn = adminDataSource.getConnection()) {
        if (!dialect.userExists(conn, username)) {
            dialect.createUser(conn, username, password);
            dialect.grantPrivilege(conn, "ALL", "SCHEMA", schemaName, username);
        }
    }
}
```

### When you need to deprovision tenant resources

Use `dropSchema()`, `dropUser()`, and related methods:

```java
public void deprovisionTenant(String schemaName, String username) throws SQLException {
    try (Connection conn = adminDataSource.getConnection()) {
        // Reassign ownership before dropping
        dialect.reassignOwnedByTo(conn, username, "admin_user");
        dialect.dropOwnedBy(conn, username);
        
        // Drop schema and user
        dialect.dropSchema(conn, schemaName);
        dialect.dropUser(conn, username);
    }
}
```

### When you need to check if resources exist

Use `schemaExists()` and `userExists()`:

```java
public boolean isTenantProvisioned(String schemaName, String username) throws SQLException {
    try (Connection conn = adminDataSource.getConnection()) {
        return dialect.schemaExists(conn, schemaName) 
            && dialect.userExists(conn, username);
    }
}
```

## JdbcDialect Interface

| Method | Description |
|--------|-------------|
| `createSchemaIfNotExists(conn, schemaName)` | Create schema if it doesn't exist |
| `dropSchema(conn, schemaName)` | Drop schema |
| `schemaExists(conn, schemaName)` | Check if schema exists |
| `createUser(conn, username, password)` | Create database user |
| `dropUser(conn, username)` | Drop database user |
| `userExists(conn, username)` | Check if user exists |
| `grantPrivilege(conn, privilege, resourceType, resourceName, username)` | Grant privilege to user |
| `dropOwnedBy(conn, owner)` | Drop all objects owned by user |
| `reassignOwnedByTo(conn, owner, assignee)` | Reassign ownership |

## PostgreSQL Implementation

| Class | Purpose |
|-------|---------|
| `PostgresJdbcDialect` | PostgreSQL implementation of `JdbcDialect` |
| `PostgresJdbcDialectSql` | SQL statement builder for PostgreSQL |

### PostgreSQL SQL Examples

```sql
-- createSchemaIfNotExists
CREATE SCHEMA IF NOT EXISTS tenant_123;

-- createUser
CREATE USER tenant_123_user WITH PASSWORD 'password';

-- grantPrivilege
GRANT ALL ON SCHEMA tenant_123 TO tenant_123_user;

-- dropSchema
DROP SCHEMA tenant_123 CASCADE;

-- dropUser
DROP USER tenant_123_user;
```

## Complete Example

```java
@Service
public class TenantDatabaseProvisioner {
    
    private final DataSource adminDataSource;
    private final JdbcDialect dialect;
    
    public TenantDatabaseProvisioner(DataSource adminDataSource) {
        this.adminDataSource = adminDataSource;
        this.dialect = new PostgresJdbcDialect(new PostgresJdbcDialectSql());
    }
    
    @Transactional
    public TenantDbCredentials provision(UUID tenantId) throws SQLException {
        String schemaName = "tenant_" + tenantId.toString().replace("-", "");
        String username = schemaName + "_user";
        String password = generateSecurePassword();
        
        try (Connection conn = adminDataSource.getConnection()) {
            // Create schema
            dialect.createSchemaIfNotExists(conn, schemaName);
            
            // Create user
            dialect.createUser(conn, username, password);
            
            // Grant privileges
            dialect.grantPrivilege(conn, "ALL", "SCHEMA", schemaName, username);
            dialect.grantPrivilege(conn, "ALL", "ALL TABLES IN SCHEMA", schemaName, username);
            
            return new TenantDbCredentials(schemaName, username, password);
        }
    }
    
    public void deprovision(String schemaName, String username) throws SQLException {
        try (Connection conn = adminDataSource.getConnection()) {
            dialect.dropSchema(conn, schemaName);
            dialect.dropOwnedBy(conn, username);
            dialect.dropUser(conn, username);
        }
    }
}
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-data` | DataSource management |
| `trishul-tenant-persistence-management` | Tenant database provisioning |
| `trishul-tenant-service` | Tenant lifecycle management |
