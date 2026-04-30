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
- Use `make install` to build the project. Always run `make install` after changes to verify build passes.
- Plugins: Spotless (code formatting, runs automatically), JaCoCo (code coverage), PIT (mutation testing), SonarQube (static analysis), SpotBugs (bug detection).
- Java 21 features allowed. Follow existing patterns in the codebase.

### Testing Standards
- All tests use JUnit 5 and Mockito for mocking
- Each module has unit tests
- Tests should have proper assertions (not empty tests)
- PIT mutation testing runs after unit tests in each module for quality assurance
- PIT mutation test coverage should be maintained
- Check PIT reports for mutation coverage after test changes (`target/pit-reports/index.html` or `modules/<module>/target/pit-reports/index.html`)

## Key Files to Review Before Changes
1. `modules/trishul-base-types/src/main/java/io/trishul/base/types/base/pojo/Identified.java`
2. `modules/trishul-model/src/main/java/io/trishul/model/base/mapper/BaseMapper.java`
3. Any `*Mapper.java` files when dealing with mapping code

## Common Issues to Avoid
1. **S3252 false positives**: Don't "fix" MapStruct static constant references
2. **Empty test methods**: Add meaningful assertions
3. **Removing TODOs**: Implement or leave them
4. **Breaking the mapper pattern**: Always test with `make install`
