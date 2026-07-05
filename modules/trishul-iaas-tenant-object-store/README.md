# trishul-iaas-tenant-object-store

## Overview
- IaaS Tenant Object Store
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Library module (consume types directly; may still include auto-config classes).

## Artifact Coordinates

```xml
<dependency>
  <groupId>io.trishul</groupId>
  <artifactId>trishul-iaas-tenant-object-store</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-iaas-access](../trishul-iaas-access/README.md)
- [trishul-iaas-tenant-idp](../trishul-iaas-tenant-idp/README.md)
- [trishul-object-store](../trishul-object-store/README.md)
- [trishul-test-bom](../trishul-test-bom/README.md)

### External Dependencies
- None

## Configuration

### Application Properties

This module does not define module-specific `*-application.properties` keys (file may be empty).

### Environment Variables

None detected in this module’s properties file.

## AutoConfiguration Beans

- No Spring `@Bean` methods detected in this module.

## Key Classes & APIs

- `io.trishul.iaas.tenant.object.store.BaseTenantIaasVfsResources`
- `io.trishul.iaas.tenant.object.store.TenantIaasVfsDeleteResult`
- `io.trishul.iaas.tenant.object.store.TenantIaasVfsResourceMapper`
- `io.trishul.iaas.tenant.object.store.TenantIaasVfsResources`
- `io.trishul.iaas.tenant.object.store.UpdateTenantIaasVfsResources`
- `io.trishul.iaas.tenant.object.store.builder.TenantObjectStoreResourceBuilder`

## Tests (Examples)

- `io.trishul.iaas.tenant.object.store.TenantIaasVfsDeleteResultTest`
- `io.trishul.iaas.tenant.object.store.TenantIaasVfsResourceMapperTest`
- `io.trishul.iaas.tenant.object.store.TenantIaasVfsResourcesTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:iaas-tenant-object-store-application.properties
```

Note: the actual file name in this module is: `iaas-tenant-object-store-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- [trishul-iaas-tenant](../trishul-iaas-tenant/README.md)
- [trishul-iaas-tenant-object-store-service](../trishul-iaas-tenant-object-store-service/README.md)

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
