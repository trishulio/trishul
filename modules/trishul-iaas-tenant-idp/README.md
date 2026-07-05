# trishul-iaas-tenant-idp

## Overview
- IaaS
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Library module (consume types directly; may still include auto-config classes).

## Artifact Coordinates

```xml
<dependency>
  <groupId>io.trishul</groupId>
  <artifactId>trishul-iaas-tenant-idp</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-iaas-access](../trishul-iaas-access/README.md)
- [trishul-object-store](../trishul-object-store/README.md)
- [trishul-tenant](../trishul-tenant/README.md)
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

- `io.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant`
- `io.trishul.iaas.idp.tenant.model.IaasIdpTenant`
- `io.trishul.iaas.idp.tenant.model.IaasIdpTenantAccessor`
- `io.trishul.iaas.idp.tenant.model.TenantIaasAuthDeleteResult`
- `io.trishul.iaas.idp.tenant.model.TenantIaasAuthResourceMapper`
- `io.trishul.iaas.idp.tenant.model.TenantIaasAuthResources`
- `io.trishul.iaas.idp.tenant.model.TenantIaasIdpDeleteResult`
- `io.trishul.iaas.idp.tenant.model.TenantIaasIdpResources`
- `io.trishul.iaas.idp.tenant.model.TenantIaasIdpTenantMapper`
- `io.trishul.iaas.idp.tenant.model.UpdateIaasIdpTenant`
- `io.trishul.iaas.idp.tenant.model.mapper.TenantIaasIdpResourcesMapper`

## Tests (Examples)

- `io.trishul.iaas.idp.tenant.model.IaasIdpTenantTest`
- `io.trishul.iaas.idp.tenant.model.TenantIaasAuthDeleteResultTest`
- `io.trishul.iaas.idp.tenant.model.TenantIaasAuthResourceMapperTest`
- `io.trishul.iaas.idp.tenant.model.TenantIaasAuthResourcesTest`
- `io.trishul.iaas.idp.tenant.model.TenantIaasIdpDeleteResultTest`
- `io.trishul.iaas.idp.tenant.model.TenantIaasIdpResourcesTest`
- `io.trishul.iaas.idp.tenant.model.TenantIaasIdpTenantMapperTest`
- `io.trishul.iaas.idp.tenant.model.mapper.TenantIaasIdpResourcesMapperTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:iaas-tenant-idp-application.properties
```

Note: the actual file name in this module is: `iaas-tenant-idp-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- [trishul-iaas-tenant](../trishul-iaas-tenant/README.md)
- [trishul-iaas-tenant-idp-management-service](../trishul-iaas-tenant-idp-management-service/README.md)
- [trishul-iaas-tenant-object-store](../trishul-iaas-tenant-object-store/README.md)
- [trishul-iaas-tenant-object-store-aws](../trishul-iaas-tenant-object-store-aws/README.md)
- [trishul-iaas-user-service](../trishul-iaas-user-service/README.md)

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
