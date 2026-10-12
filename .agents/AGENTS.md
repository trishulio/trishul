# CLAUDE.md - AI Development Guidelines for Trishul Project

## Project Overview
Trishul is a multi-tenant SaaS backend infrastructure framework built with Java 21 and Spring Boot 3.4. It provides a comprehensive foundation for building multi-tenant applications with AWS integration (Cognito, S3, IAM) and provides tenant isolation, user management, and object storage.

## Architecture

### Core Modules (55 modules total)

#### Base Layer
- **trishul-base-types**: Core interfaces defining entity contracts (`Identified<T>`, `Versioned`, `Audited`, `Refreshable`)
- **trishul-model**: Base model classes, utilities, and patterns (`BaseMapper`, JSON, Reflection, Validation)
- **trishul-test-bom/trishul-test**: Testing utilities and shared test models

#### Data Layer
- **trishul-data**: DataSource management and configuration
- **trishul-jdbc**: JDBC dialect support (PostgreSQL)
- **trishul-repo**: JPA repository utilities, query builders
- **trishul-repo-aggregation**: Aggregate queries

#### Multi-Tenancy Layer
- **trishul-tenant**: Tenant entity and DTOs
- **trishul-tenant-auth**: Tenant context and authentication
- **trishul-tenant-persistence**: Per-tenant database connection pooling
- **trishul-tenant-persistence-management**: Flyway migrations per tenant
- **trishul-tenant-service**: Tenant CRUD operations

#### User Management
- **trishul-user**: User entities, roles, salutations, status
- **trishul-user-service**: User service layer

#### AWS Infrastructure as a Service (IaaS)
- **trishul-iaas**: Base IaaS abstractions
- **trishul-iaas-auth/trishul-iaas-auth-aws**: AWS Cognito identity credentials
- **trishul-iaas-access/trishul-iaas-access-aws**: IAM policies and roles
- **trishul-iaas-tenant***: Tenant-specific AWS resources
- **trishul-iaas-user***: Cognito user management

#### Object Storage
- **trishul-object-store**: Object store abstractions
- **trishul-object-store-aws**: S3 integration
- **trishul-object-store-file***: File management on top of object stores

#### Business Domains
- **trishul-money**: Currency, amounts, tax calculations
- **trishul-quantity**: Unit of measure handling
- **trishul-address**: Address models
- **trishul-commodity**: Product/commodity models

#### API Layer
- **trishul-api**: Common API utilities
- **trishul-crud**: Generic CRUD controller/service patterns

## Key Patterns

### Entity Hierarchy
```
Identified<T> (id)
    └── Versioned (version)
          └── Audited (createdAt, lastUpdated)
```

### MapStruct Mapper Pattern (CRITICAL)
**DO NOT CHANGE**: All mappers follow a consistent pattern using static constant references:
```java
@Mapper
public interface TenantMapper extends BaseMapper<Tenant, TenantDto, AddTenantDto, UpdateTenantDto> {
  @Mapping(target = Tenant.ATTR_ID, ignore = true)  // CORRECT - reference through concrete class
  @Mapping(target = Tenant.ATTR_VERSION, ignore = true)
  Tenant fromAddDto(AddTenantDto dto);
}
```
- Static property constants (like `ATTR_ID`, `ATTR_VERSION`) are inherited from base interfaces
- MapStruct requires the reference to be from the target class being mapped, not the interface
- SonarQube rule S3252 (static access) should be IGNORED for MapStruct mappers. Don't "fix" MapStruct static constant references.
- Always reference static constants through the concrete entity class (e.g., `Tenant.ATTR_ID` not `Identified.ATTR_ID`)

### Multi-Tenancy
- Schema-per-tenant database isolation
- Tenant context propagated via ThreadLocal
- Per-tenant connection pooling with HikariCP

### AWS Integration
- Cognito for identity/authentication
- IAM for access control policies
- S3 for object storage
- All AWS clients are abstracted behind interfaces

## Global Guidelines

### TODO Comments
- TODOs should be addressed by implementing the actual fix, not by deleting them
- If a TODO cannot be implemented now, leave it in place
- Only remove a TODO when its associated task is complete

### Build System & Configuration
- Maven multi-module project
- Configuration files: `pom.xml`, `mvn.env`, `docker-compose-bin.yml`, `Makefile`
- Build runs inside Docker container via docker-compose
- Use `make install THREADS=1C` to build the project (single-threaded execution prevents Docker resource exhaustion and parallel PIT minion crashes). Always run `make install` after changes to verify build passes.
- **Monitoring Builds**: The full `make install` build takes about 30 minutes. When running it, set a background timer (e.g., `schedule` tool) for 15 minutes (900 seconds) to monitor status, rather than polling frequently (e.g. every minute) to conserve tokens and context.
- Plugins: Spotless (code formatting, runs automatically), JaCoCo (code coverage), PIT (mutation testing), SonarQube (static analysis), SpotBugs (bug detection).
- Java 21 features allowed. Follow existing patterns in the codebase.

### Testing Standards
- All tests use JUnit 5 and Mockito for mocking
- Each module has unit tests
- Tests should have proper assertions (not empty tests)
- PIT mutation testing runs after unit tests in each module for quality assurance
- PIT mutation test coverage should be maintained
- Check PIT reports for mutation coverage after test changes (`target/pit-reports/index.html` or `modules/<module>/target/pit-reports/index.html`)
- No conditional assertions in unit tests (assert exact expected condition without branching)
- When testing AWS clients logging request IDs, mock `ResponseMetadata`
- Test `ResourceNotFoundException` / `NoSuchEntityException` / `AmazonS3Exception` catch branches
- Verify fluent setters return `this` via `assertSame`
- For classes extending `BaseModel`, test `equals`, `hashCode`, and `toString`
- For deep-cloning setters and accumulator loops, test both `null` and non-null branches
- For MapStruct mappers with nested structures, test scenarios where nested fields in DTOs are `null`
- Only test implementing POJO/Model classes; base interfaces do not need direct tests. Remove `testContextLoads()` style tests.
- When testing code that catches `InterruptedException` and restores the interrupt flag with `Thread.currentThread().interrupt()`, always assert `assertTrue(Thread.currentThread().isInterrupted())` and reset the flag in a `finally` block with `Thread.interrupted()`. This ensures PIT `VoidMethodCallMutator` is killed without leaking interrupted state to subsequent tests.

## Key Files to Review Before Changes
1. `modules/trishul-base-types/src/main/java/sh/trishul/base/types/base/pojo/Identified.java`
2. `modules/trishul-model/src/main/java/sh/trishul/model/base/mapper/BaseMapper.java`
3. Any `*Mapper.java` files when dealing with mapping code

## Common Issues to Avoid
1. **S3252 false positives**: Don't "fix" MapStruct static constant references
2. **Empty test methods**: Add meaningful assertions
3. **Removing TODOs**: Implement or leave them
4. **Breaking the mapper pattern**: Always test with `make install`
5. **Direct fully qualified classpaths**: Do not write fully qualified package class paths directly in code (e.g., `org.junit.jupiter.api.Assertions.assertNotEquals`). Instead, import classes and use their short name. For functions/static methods, use static imports (e.g., `import static org.junit.jupiter.api.Assertions.assertNotEquals;`).

## SonarQube & Static Analysis Triage Guidelines
When running the `fix-sonarqube` skill or triaging static analysis issues:

### 🚫 Rules to PRESERVE (DO NOT MODIFY):
- `java:S3252`: MapStruct static entity constants must be accessed through concrete classes (`Tenant.ATTR_ID`).
- `java:S119`: Framework generic type parameter naming (`<T extends BaseUser<T>>`, `<ID, E>`).
- `java:S1135` / `xml:S1135`: TODO comments must only be removed upon genuine feature implementation.
- `java:S1874` / `java:S1123`: Deprecated methods are preserved for API consumer backward compatibility.
- `java:S1602`: Lambda `{ return ...; }` braces that disambiguate overloaded CheckedSupplier vs CheckedConsumer functional interfaces (e.g. `TenantSchemaRegister`).
- `java:S3011` / `java:S2160`: Reflection in `BaseModel` / `ReflectionManipulator`. Do not generate boilerplate `equals/hashCode` on DTOs extending `BaseModel`.
- `java:S1172`: Unused parameters mandated by polymorphic service or controller signatures.

### ✅ Rules to FIX:
- `java:S1068` / `java:S1481` / `java:S1144`: Remove unused private fields, local variables, and private methods.
- `java:S1488`: Inline immediately returned local variables (`return expr;`).
- `java:S2142`: Restore thread interrupt flag `Thread.currentThread().interrupt();` before rethrowing exception.
- `java:S5164`: Clean up `ThreadLocal` instances in a `finally` block with `.remove()`.
- `java:S1155`: Replace `.size() == 0` or `.size() > 0` with `.isEmpty()` / `!isEmpty()`.
- `java:S2129`: Replace boxed constructors with literals or `.valueOf()`.
- `java:S1161`: Add missing `@Override` annotations.
- `java:S3776`: Refactor cognitive complexity into guard clauses and helper methods.
