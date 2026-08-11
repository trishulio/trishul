# Trishul Development Guidelines

For file and code search, use the `ccc` MCP to locate implementations without needing exact names:

```bash
# Find MapStruct mapper usage with BaseMapper patterns
ccc_tool(query="BaseMapper interface implementation", limit=10)
```

## Build & Test Commands

All builds run inside Docker via `docker-compose-bin.yml`. Never assume native tooling is available.

```bash
# Full build with tests and mutation coverage
make install

# Compile only (quiet mode)
make compile

# Run verification/tests
make verify

# Change Maven threads for faster builds
export THREADS=4C && make install
```

When running `make install`, it takes about 30 minutes. To avoid wasting tokens and context, monitor the build status by setting a background schedule/timer for 15 minutes (900 seconds) instead of polling frequently.

## Project Structure

This is a monorepo with modules in `modules/`. Each module has its own README and follows the same build pattern. Understand that modules are:

- **Foundation Layer**: Core interfaces, MapStruct mappers (trishul-base-types, trishul-model)
- **Infrastructure Layer**: Repo utilities, data sources, secret management (trishul-repo*, trishul-data*)
- **IaaS Abstraction Layer**: Platform-abstraced services to support multiple cloud providers
- **Domain Modules**: Money, quantity, address, commodity abstractions

## Multi-Tenant Architecture Key Concepts

1. **Base Database**: Stores tenant metadata in tables prefixed `TENANT_INFO_*` and global configuration (`CONFIG_TABLE`)
2. **Tenant Datasources**: Per-tenant routing template configured via:
   ```properties
   trishul.tenant.datasource.url-template=jdbc:postgresql://host/tenant_{tenantId}
   ```
3. **Schema Validation**: All table names in `trishul-repo` must be registered for multi-tenancy support

## MapStruct Usage Pattern

Map mappers are not Spring-component model and extend BaseMapper interface from trishul-model:

```java
@Mapper
public interface EntityMapper extends BaseMapper<Entity, EntityDto> {
    @Mapping(target = ATTR_ID, ignore = true)  // Reference static constants via concrete class!
}
```

## Build Profiles | Profile | Variable | Purpose ||---------|----------|---------|| Mutation Coverage | `ENABLE_MUTATION_COVERAGE=true` | PIT testing - required for PRs|| SonarQube | `ENABLE_SONARQUUBE=true` | Code quality analysis|| Dependency Check | `ENABLE_DEPENDENCY_CHECK=true` | OWASP vulnerability scan **Always run with mutation coverage enabled. Open HTML reports at modules/*/target/pit-reports/index.html to inspect failed mutations before committing test changes.
# Application identity
spring.application.name=your-app-name

Database configuration - Base database for multi-tenant routing
spring.datasource.url=jdbc:postgresql://host/admin_db
trishul.tenant-persistent-management

Tenant datasource template with {tenentId} variable substitution, and username/password from environment variables or secrets manager.

AWS Cognito integration requires both auth modules + region setting in env file or resource configuration.

## Entity Pattern Template @Entity public class MyEntity extends BaseEntity implements CrudEntity MY_ENTT Audited<MyEntt> that Id private UUID id; string name attribute with CreationTimestamp for created_at and Update Timestamp columns always added as part of interface contract
```java

## Direct Fully Qualified Classpaths

Do not write fully qualified package class paths directly in Java code (e.g. `org.junit.jupiter.api.Assertions.assertNotEquals`). Instead, import packages and classes and use their short classnames. For functions and static utility methods (e.g. mock, assertions), use static imports.
