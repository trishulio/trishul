# trishul-iaas-tenant-service

## Overview
- IaaS
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Spring Boot auto-configuration (provides beans via `@Configuration`).

## Artifact Coordinates

```xml
<dependency>
  <groupId>sh.trishul</groupId>
  <artifactId>trishul-iaas-tenant-service</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-iaas-tenant-idp-management-service](../trishul-iaas-tenant-idp-management-service/README.md)
- [trishul-iaas-tenant-object-store-service](../trishul-iaas-tenant-object-store-service/README.md)
- [trishul-test-bom](../trishul-test-bom/README.md)

### External Dependencies
- None

## Configuration

### Application Properties

This module does not define module-specific `*-application.properties` keys (file may be empty).

### Environment Variables

None detected in this module’s properties file.

## AutoConfiguration Beans

- `TenantIaasService` via `tenantIaasService()` — @ConditionalOnMissingBean(TenantIaasService.class) ([modules/trishul-iaas-tenant-service/src/main/java/sh/trishul/iaas/tenant/service/autoconfiguration/IaasTenantServiceAutoConfiguration.java](modules/trishul-iaas-tenant-service/src/main/java/sh/trishul/iaas/tenant/service/autoconfiguration/IaasTenantServiceAutoConfiguration.java))

## Key Classes & APIs

- `sh.trishul.iaas.tenant.service.TenantIaasDeleteResult`
- `sh.trishul.iaas.tenant.service.TenantIaasService`
- `sh.trishul.iaas.tenant.service.autoconfiguration.IaasTenantServiceAutoConfiguration`

## Tests (Examples)

- `sh.trishul.iaas.tenant.service.TenantIaasDeleteResultTest`
- `sh.trishul.iaas.tenant.service.TenantIaasServiceTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:iaas-tenant-service-application.properties
```

Note: the actual file name in this module is: `iaas-tenant-service-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- [trishul-tenant-service](../trishul-tenant-service/README.md)

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
