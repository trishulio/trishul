# trishul-iaas-user-service

## Overview
- User
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Spring Boot auto-configuration (provides beans via `@Configuration`).

## Artifact Coordinates

```xml
<dependency>
  <groupId>io.trishul</groupId>
  <artifactId>trishul-iaas-user-service</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-crud](../trishul-crud/README.md)
- [trishul-iaas-tenant-idp](../trishul-iaas-tenant-idp/README.md)
- [trishul-iaas-user](../trishul-iaas-user/README.md)
- [trishul-tenant-auth](../trishul-tenant-auth/README.md)
- [trishul-test-bom](../trishul-test-bom/README.md)

### External Dependencies
- None

## Configuration

### Application Properties

This module does not define module-specific `*-application.properties` keys (file may be empty).

### Environment Variables

None detected in this module’s properties file.

## AutoConfiguration Beans

- `TenantIaasUserService` via `tenantIaasUserService()` — @ConditionalOnMissingBean(TenantIaasUserService.class) ([modules/trishul-iaas-user-service/src/main/java/io/trishul/iaas/user/service/autoconfiguration/IaasUserServiceAutoConfiguration.java](modules/trishul-iaas-user-service/src/main/java/io/trishul/iaas/user/service/autoconfiguration/IaasUserServiceAutoConfiguration.java))

## Key Classes & APIs

- `io.trishul.iaas.user.service.TenantIaasUserService`
- `io.trishul.iaas.user.service.autoconfiguration.IaasUserServiceAutoConfiguration`

## Tests (Examples)

- `io.trishul.iaas.user.service.TenantIaasUserServiceTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:iaas-user-service-application.properties
```

Note: the actual file name in this module is: `iaas-user-service-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- [trishul-user-service](../trishul-user-service/README.md)

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
