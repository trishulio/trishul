# trishul-api

Shared API infrastructure module bundling Spring Boot Web, Actuator, and OpenAPI documentation dependencies.

> **Use this when**: You're building a REST API module and need web server, health endpoints, and Swagger UI out of the box.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-api</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Add the dependency to your API module's pom.xml
// 2. Create your Spring Boot application
@SpringBootApplication
public class MyApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApiApplication.class, args);
    }
}

// 3. Create REST controllers
@RestController
@RequestMapping("/api/v1/items")
public class ItemController {
    
    @GetMapping
    public List<ItemDto> getItems() {
        return itemService.findAll();
    }
}

// 4. Access endpoints:
//    - API: http://localhost:8080/api/v1/items
//    - Swagger UI: http://localhost:8080/swagger-ui.html
//    - OpenAPI spec: http://localhost:8080/api-docs
//    - Actuator: http://localhost:8080/actuator
```

## When Would I Use This?

### When you need a REST API with Swagger documentation

Include this module to get Spring Web + SpringDoc OpenAPI configured:

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-api</artifactId>
</dependency>
```

Swagger UI is automatically available at `/swagger-ui.html`.

### When you need health checks and metrics endpoints

Actuator is pre-configured with all endpoints exposed:

```
GET /actuator/health     - Health status
GET /actuator/info       - Application info
GET /actuator/metrics    - Application metrics
GET /actuator/*          - All actuator endpoints
```

### When you need consistent API configuration across modules

This module provides default properties that can be overridden:

```properties
# Default configuration (from api-application.properties)
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
springdoc.api-docs.path=/api-docs
```

## Bundled Dependencies

| Dependency | Purpose |
|------------|---------|
| `spring-boot-starter-web` | REST API infrastructure (Tomcat, Jackson, Spring MVC) |
| `spring-boot-starter-actuator` | Health checks, metrics, info endpoints |
| `springdoc-openapi-starter-webmvc-ui` | OpenAPI 3 spec generation + Swagger UI |
| `trishul-model` | Base model classes |

## Default Configuration

### Actuator Endpoints

```properties
# All actuator endpoints exposed
management.endpoints.web.exposure.include=*

# Health endpoint shows full details
management.endpoint.health.show-details=always
```

### OpenAPI Documentation

```properties
# OpenAPI spec available at /api-docs
springdoc.api-docs.path=/api-docs
```

**Available URLs:**
| URL | Description |
|-----|-------------|
| `/swagger-ui.html` | Interactive API documentation |
| `/api-docs` | OpenAPI 3 JSON spec |
| `/api-docs.yaml` | OpenAPI 3 YAML spec |

## Customization

### Override Default Properties

In your application's `application.properties`:

```properties
# Restrict actuator endpoints
management.endpoints.web.exposure.include=health,info,metrics

# Change API docs path
springdoc.api-docs.path=/v3/api-docs

# Add API metadata
springdoc.info.title=My API
springdoc.info.version=1.0.0
```

### Add OpenAPI Annotations

```java
@RestController
@RequestMapping("/api/v1/tenants")
@Tag(name = "Tenants", description = "Tenant management operations")
public class TenantController {
    
    @Operation(summary = "Get all tenants")
    @ApiResponse(responseCode = "200", description = "List of tenants")
    @GetMapping
    public List<TenantDto> getTenants() {
        return tenantService.findAll();
    }
}
```

## Complete Example

```xml
<!-- your-api-module/pom.xml -->
<dependencies>
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-api</artifactId>
    </dependency>
    <dependency>
        <groupId>io.trishul</groupId>
        <artifactId>trishul-tenant-service</artifactId>
    </dependency>
</dependencies>
```

```java
@SpringBootApplication
@Import({TenantServiceAutoConfiguration.class})
public class TenantApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TenantApiApplication.class, args);
    }
}

@RestController
@RequestMapping("/api/v1/tenants")
public class TenantController {
    
    private final TenantService tenantService;
    
    @GetMapping
    public List<TenantDto> list() {
        return tenantService.findAll();
    }
    
    @PostMapping
    public TenantDto create(@RequestBody AddTenantDto dto) {
        return tenantService.create(dto);
    }
    
    @GetMapping("/{id}")
    public TenantDto get(@PathVariable UUID id) {
        return tenantService.findById(id);
    }
}
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-model` | Base model classes |
| `trishul-crud` | CRUD service abstractions |
| `trishul-auth` | Authentication/authorization integration |
