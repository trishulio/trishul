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

# Full build with tests and mutation coverage (use THREADS=1C to prevent Docker PIT minion memory crashes)
make install THREADS=1C
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

## Testing and Assertions

- Do not use conditional assertions in unit tests. Every assertion should be a strict check on the expected value or state without branched logic (e.g., do not use `assertTrue(x == null || x.isEmpty())`; instead, assert the exact expected condition, such as `assertTrue(x.isEmpty())`).
- When testing AWS client implementations that log request IDs, mock `ResponseMetadata` (e.g. `new ResponseMetadata(Map.of(ResponseMetadata.AWS_REQUEST_ID, "REQ_ID"))`).
- When testing AWS SDK error handling, include test cases that trigger `ResourceNotFoundException` / `NoSuchEntityException` / `AmazonS3Exception` catch blocks.
- Fluent setters returning `this` must be asserted with `assertSame(instance, instance.setter(...))`.
- For classes extending `BaseModel`, explicitly test `equals`, `hashCode`, and `toString`.
- For deep-cloning setters and summation/accumulator loops, test both `null` and non-null branches.
- For MapStruct mappers with nested structures, test scenarios where nested fields in the source/target DTOs are `null`.
- Only test implementing POJO/Model classes; base interfaces do not need direct tests. Avoid empty or trivial `testContextLoads()` tests.
- When testing code that catches `InterruptedException` and restores the interrupt flag with `Thread.currentThread().interrupt()`, always assert `assertTrue(Thread.currentThread().isInterrupted())` and reset the flag in a `finally` block with `Thread.interrupted()`. This ensures PIT `VoidMethodCallMutator` is killed without leaking interrupted state to subsequent tests.

## Direct Fully Qualified Classpaths

Do not write fully qualified package class paths directly in Java code (e.g. `org.junit.jupiter.api.Assertions.assertNotEquals`). Instead, import packages and classes and use their short classnames. For functions and static utility methods (e.g. mock, assertions), use static imports.

## SonarQube & Static Analysis Guidelines

When remediating SonarQube issues (or using the `fix-sonarqube` skill), strictly adhere to the triage matrix:

### 🚫 Architectural Patterns to PRESERVE (NEVER MODIFY):
- **`java:S3252` (MapStruct static constant access via concrete class)**: MapStruct requires referencing entity constants via the concrete entity class (`Tenant.ATTR_ID`), not base interfaces. Never "fix" static constant access on mappers.
- **`java:S119` (Type parameter names)**: Framework generic parameters (`<T extends BaseUser<T>>`, `<ID, E>`) define the entity model contracts. Never rename them.
- **`java:S1135` / `xml:S1135` (TODO comments)**: Never delete or comment out TODO tags to silence SonarQube. Only remove a TODO when the associated feature/fix is actually implemented.
- **`java:S1874` / `java:S1123` (Deprecated code & tags)**: Deprecated methods are intentionally maintained for consumer backward compatibility.
- **`java:S1602` (Lambda block braces `{ return ...; }`)**: Multi-overloaded functional interfaces (e.g. `TenantSchemaRegister`) require explicit braces and return to disambiguate overloaded methods (`CheckedSupplier` vs `CheckedConsumer`). Removing braces breaks javac compilation.
- **`java:S3011` / `java:S2160` (Reflection accessibility & BaseModel equals)**: Reflection manipulation is by-design in `ReflectionManipulator` and `BaseModel`. Do not generate boilerplate equals/hashCode on DTOs extending `BaseModel`.
- **`java:S1172` (Unused parameters on framework interfaces)**: Do not remove parameters mandated by polymorphic service or controller signatures.

### ✅ Actionable Code Smells to FIX:
- **`java:S1068` / `java:S1481` / `java:S1144`**: Safely remove unused private fields, local variables, and dead private methods.
- **`java:S1488`**: Inline immediately returned local variables (`return expr;`).
- **`java:S2142`**: Always restore thread interrupt flag `Thread.currentThread().interrupt();` before rethrowing exceptions.
- **`java:S5164`**: Always clean up `ThreadLocal` instances in a `finally` block via `.remove()`.
- **`java:S1155`**: Replace `.size() == 0` or `.size() > 0` with `.isEmpty()` / `!isEmpty()`.
- **`java:S2129`**: Replace boxed constructors with literals or `valueOf()`.
- **`java:S1161`**: Add missing `@Override` annotations.
- **`java:S3776`**: Refactor cognitive complexity with guard clauses and helper methods.
