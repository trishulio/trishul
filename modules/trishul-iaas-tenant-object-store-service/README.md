# trishul-iaas-tenant-object-store-service

## Overview
- IaaS Tenant Object Store Service
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Spring Boot auto-configuration (provides beans via `@Configuration`).

## Artifact Coordinates

```xml
<dependency>
  <groupId>io.trishul</groupId>
  <artifactId>trishul-iaas-tenant-object-store-service</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-iaas-access-service](../trishul-iaas-access-service/README.md)
- [trishul-iaas-tenant](../trishul-iaas-tenant/README.md)
- [trishul-iaas-tenant-object-store](../trishul-iaas-tenant-object-store/README.md)
- [trishul-object-store-service](../trishul-object-store-service/README.md)
- [trishul-test-bom](../trishul-test-bom/README.md)

### External Dependencies
- None

## Configuration

### Application Properties

This module does not define module-specific `*-application.properties` keys (file may be empty).

### Environment Variables

None detected in this module’s properties file.

## AutoConfiguration Beans

- `TenantIaasVfsService` via `iaasVfsService()` — @ConditionalOnMissingBean(TenantIaasVfsService.class) ([modules/trishul-iaas-tenant-object-store-service/src/main/java/io/trishul/iaas/tenant/object/store/service/autoconfiguration/IaasTenantObjectStoreServiceAutoConfiguration.java](modules/trishul-iaas-tenant-object-store-service/src/main/java/io/trishul/iaas/tenant/object/store/service/autoconfiguration/IaasTenantObjectStoreServiceAutoConfiguration.java))

## Key Classes & APIs

- `io.trishul.iaas.tenant.object.store.service.autoconfiguration.IaasTenantObjectStoreServiceAutoConfiguration`
- `io.trishul.iaas.tenant.object.store.service.service.TenantIaasVfsService`

## Tests (Examples)

- `io.trishul.iaas.tenant.object.store.service.service.TenantIaasVfsServiceTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:iaas-tenant-object-store-service-application.properties
```

Note: the actual file name in this module is: `iaas-tenant-object-store-service-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- [trishul-iaas-tenant-service](../trishul-iaas-tenant-service/README.md)

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
