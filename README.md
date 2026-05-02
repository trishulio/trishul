# Trishul

A modular Java Spring Boot framework for building multi-tenant cloud backend with enterprise grade security, and consistent,composable and opinionated modules, enabling rapid development of high quality applications with minimal boilerplate code and built-in low level optimizations.

## Overview

Trishul provides a comprehensive set of reusable modules for building production-grade multi-tenant applications. Each module is independently usable and follows consistent patterns for entities, services, and mappers.

### Key Features

- **Multi-tenancy**: Database-per-tenant isolation with automatic schema routing
- **AWS Integration**: First-class support for Cognito, S3, IAM, and Secrets Manager
- **Generic CRUD**: Reusable patterns for entities, services, and REST controllers
- **Type Safety**: MapStruct-based mapping with compile-time validation
- **Domain Modeling**: Built-in support for money, quantities, addresses, and commodities
- **OpenAPI**: Automatic API documentation generation
- **Platform Agnostic**: Dependencies are constructured as interfaces and can be replaced with any cloud provider's implementation. AWS based modules are provided as starter modules.
- **Optimized Patterns**: The framework establishes patterns and idioms for building applications with Java that are optimized for performance and scalability, while maintaining readability and maintainability.
- **Mutually Exclusive Modules**: Modules are designed to be mutually exclusive, allowing developers to choose only the modules they need, reducing bloat and improving performance.
- **Dockerized Build**: The framework is built inside a Docker container for consistent build environment.
- **Advanced build tools**: Includes PIT mutation coverage, bug spot, formatter, vulnerability detection, test coverage, and much more.

## Requirements

- Java 21
- Docker & Docker Compose
- Maven 3.8+
- AWS account (for production deployments)

## Quick Start

### Building the Project

```bash
make install
```

This builds all modules inside a Docker container and runs unit tests with PIT mutation coverage.

### Using Modules in Your Project

Add the parent BOM to your `pom.xml`:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>io.trishul</groupId>
            <artifactId>trishul-parent</artifactId>
            <version>1.0.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Then add individual modules as needed:

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-crud</artifactId>
</dependency>
```

## Module Reference

### Foundation Layer

| Module | Description |
|--------|-------------|
| `trishul-base-types` | Core interfaces: `Identified`, `Versioned`, `Audited`, `CrudEntity` |
| `trishul-model` | Base model classes, MapStruct mappers, DTOs, validation, exceptions |
| `trishul-test` | Test utilities and helpers |
| `trishul-test-bom` | Test dependency bill of materials |

### Infrastructure Layer

| Module | Description |
|--------|-------------|
| `trishul-repo` | JPA repository utilities, query builders, criteria specifications |
| `trishul-repo-aggregation` | Aggregation query support |
| `trishul-data` | DataSource configuration, HikariCP integration |
| `trishul-data-management` | Database migration support |
| `trishul-jdbc` | Low-level JDBC utilities |
| `trishul-secrets` | Secret management interface |
| `trishul-secrets-aws` | AWS Secrets Manager implementation |

### IaaS Abstraction Layer

| Module | Description |
|--------|-------------|
| `trishul-iaas` | IaaS client abstractions (`IaasClient`, `BulkIaasClient`, `IaasRepository`) |
| `trishul-iaas-auth` | IaaS authentication interfaces |
| `trishul-iaas-auth-aws` | AWS Cognito authentication |
| `trishul-iaas-access` | IAM/access control abstractions |
| `trishul-iaas-access-aws` | AWS IAM implementation |
| `trishul-iaas-access-service` | Access management service |

### Authentication Layer

| Module | Description |
|--------|-------------|
| `trishul-auth` | Authentication abstractions, security configuration, context holders |
| `trishul-auth-aws` | AWS Cognito JWT validation |

### Multi-tenancy Layer

| Module | Description |
|--------|-------------|
| `trishul-tenant` | Tenant entity, DTOs, mappers |
| `trishul-tenant-auth` | Tenant context extraction from authentication |
| `trishul-tenant-persistence` | Multi-tenant datasource routing via Hibernate |
| `trishul-tenant-persistence-management` | Per-tenant database migrations |
| `trishul-tenant-service` | Tenant CRUD service with lifecycle management |
| `trishul-iaas-tenant` | IaaS tenant resource abstractions |
| `trishul-iaas-tenant-aws` | AWS resource builder for tenants |
| `trishul-iaas-tenant-service` | Tenant IaaS resource management |
| `trishul-iaas-tenant-idp` | Tenant identity provider abstractions |
| `trishul-iaas-tenant-idp-management-service` | Tenant IdP management |
| `trishul-iaas-tenant-idp-service-aws` | AWS Cognito tenant IdP |

### User Management Layer

| Module | Description |
|--------|-------------|
| `trishul-user` | User entity, roles, salutations, status |
| `trishul-user-service` | User CRUD service |
| `trishul-iaas-user` | IaaS user abstractions |
| `trishul-iaas-user-aws` | AWS Cognito user management |
| `trishul-iaas-user-service` | User IaaS operations service |
| `trishul-iaas-user-service-aws` | AWS Cognito user service |

### Object Storage Layer

| Module | Description |
|--------|-------------|
| `trishul-object-store` | Object store (bucket) abstractions |
| `trishul-object-store-aws` | AWS S3 bucket implementation |
| `trishul-object-store-service` | Object store management service |
| `trishul-object-store-service-aws` | AWS S3 service implementation |
| `trishul-object-store-file` | File storage models |
| `trishul-object-store-file-service` | File storage service |
| `trishul-object-store-file-service-aws` | AWS S3 file operations |
| `trishul-iaas-tenant-object-store` | Per-tenant object store abstractions |
| `trishul-iaas-tenant-object-store-aws` | Per-tenant S3 buckets |
| `trishul-iaas-tenant-object-store-service` | Tenant object store management |

### Application Layer

| Module | Description |
|--------|-------------|
| `trishul-api` | REST API base with OpenAPI/Swagger UI |
| `trishul-crud` | Generic CRUD controller and service patterns |
| `trishul-crud-aws` | AWS-specific CRUD utilities |

### Domain Modules

| Module | Description |
|--------|-------------|
| `trishul-money` | Monetary values with Joda-Money, taxes, currencies |
| `trishul-quantity` | Physical quantities with JSR-385 Units of Measurement |
| `trishul-quantity-service` | Quantity conversion service |
| `trishul-quantity-management-service` | Quantity management |
| `trishul-address` | Address model and mapping |
| `trishul-commodity` | Commodity/product models |

## Common Integration Patterns

### Pattern 1: Basic Multi-tenant REST API

For a simple multi-tenant CRUD API with PostgreSQL:

```xml
<dependencies>
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-crud</artifactId>
    </dependency>
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-tenant-persistence</artifactId>
    </dependency>
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-auth</artifactId>
    </dependency>
</dependencies>
```

### Pattern 2: Full AWS Stack with User Management

For production deployment with AWS Cognito, S3, and Secrets Manager:

```xml
<dependencies>
    <!-- Core -->
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-crud</artifactId>
    </dependency>

    <!-- Multi-tenancy -->
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-tenant-service</artifactId>
    </dependency>
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-tenant-persistence-management</artifactId>
    </dependency>

    <!-- AWS Auth -->
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-auth-aws</artifactId>
    </dependency>
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-iaas-auth-aws</artifactId>
    </dependency>

    <!-- User Management -->
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-user-service</artifactId>
    </dependency>
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-iaas-user-service-aws</artifactId>
    </dependency>

    <!-- Object Storage -->
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-object-store-service-aws</artifactId>
    </dependency>
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-iaas-tenant-object-store-service</artifactId>
    </dependency>

    <!-- Secrets -->
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-secrets-aws</artifactId>
    </dependency>
</dependencies>
```

### Pattern 3: Domain-Rich Application

For applications with complex domain models:

```xml
<dependencies>
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-crud</artifactId>
    </dependency>
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-money</artifactId>
    </dependency>
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-quantity</artifactId>
    </dependency>
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-address</artifactId>
    </dependency>
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-commodity</artifactId>
    </dependency>
</dependencies>
```

## Configuration

### Core Properties

```properties
# Application
spring.application.name=my-app

# Database (Admin/Global)
spring.datasource.url=jdbc:postgresql://localhost:5432/admin_db
spring.datasource.username=admin
spring.datasource.password=${DB_PASSWORD}

# Tenant Database Template
trishul.tenant.datasource.url-template=jdbc:postgresql://localhost:5432/tenant_{tenantId}
trishul.tenant.datasource.username=tenant_user
trishul.tenant.datasource.password=${TENANT_DB_PASSWORD}
```

### AWS Properties

```properties
# AWS Region
aws.region=us-east-1

# Cognito
aws.cognito.user-pool-id=us-east-1_xxxxx
aws.cognito.client-id=xxxxx

# S3
aws.s3.bucket-prefix=my-app

# Secrets Manager
aws.secrets.prefix=/my-app/
```

## Entity Patterns

### Implementing a CRUD Entity

All entities should implement the standard interfaces from `trishul-base-types`:

```java
@Entity
@Table(name = "MY_ENTITY")
public class MyEntity extends BaseEntity
    implements CrudEntity<UUID, MyEntity>, Audited<MyEntity> {

    @Id
    private UUID id;

    @Column(name = "name")
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    // Implement interface methods...
}
```

### MapStruct Mapper Pattern

```java
@Mapper(componentModel = "spring")
public interface MyEntityMapper extends BaseMapper<MyEntity, MyEntityDto, AddMyEntityDto, UpdateMyEntityDto> {

    // IMPORTANT: Always reference static constants from the concrete class
    @Mapping(target = MyEntity.ATTR_ID, ignore = true)
    @Mapping(target = MyEntity.ATTR_CREATED_AT, ignore = true)
    MyEntity fromAddDto(AddMyEntityDto dto);
}
```

## Testing

### Running Tests

```bash
# Full build with tests
make install

# View mutation test coverage
open modules/<module>/target/pit-reports/index.html
```

### Test Utilities

Use `trishul-test` for common test patterns:

```java
@ExtendWith(MockitoExtension.class)
class MyServiceTest {
    @Mock
    private MyRepository repository;

    @InjectMocks
    private MyService service;

    @Test
    void testCreate() {
        // Test implementation
    }
}
```

## Build Profiles

| Profile | Activation | Description |
|---------|------------|-------------|
| `mutation-coverage` | `ENABLE_MUTATION_COVERAGE=true` | PIT mutation testing |
| `analysis` | `ENABLE_SONARQUBE=true` | SonarQube analysis |
| `dependency-check` | `ENABLE_DEPENDENCY_CHECK=true` | OWASP dependency check |
| `spotbugs` | `ENABLE_SPOTBUGS=true` | SpotBugs static analysis |
| `generate-openapi` | `ENABLE_OPENAPI_SPEC_GENERATION=true` | OpenAPI spec generation |

## Documentation

- [Architecture Guide](docs/ARCHITECTURE.md)
- [Getting Started Tutorial](docs/GETTING_STARTED.md)
- [AWS Setup Guide](docs/AWS_SETUP.md)

## Module-Specific Documentation

Each module contains its own README with detailed usage instructions. See the `modules/` directory.

## Contributing

1. Follow existing code patterns
2. Ensure all tests pass: `make install`
3. Maintain PIT mutation coverage
4. Use Spotless formatter (runs automatically)

## License

See [LICENSE](LICENSE) file.
