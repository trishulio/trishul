# trishul-iaas-tenant-idp

## Overview
- IaaS
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Library module (consume types directly; may still include auto-config classes).

## Artifact Coordinates

```xml
<dependency>
  <groupId>sh.trishul</groupId>
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

- `sh.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant`
- `sh.trishul.iaas.idp.tenant.model.IaasIdpTenant`
- `sh.trishul.iaas.idp.tenant.model.IaasIdpTenantAccessor`
- `sh.trishul.iaas.idp.tenant.model.TenantIaasAuthDeleteResult`
- `sh.trishul.iaas.idp.tenant.model.TenantIaasAuthResourceMapper`
- `sh.trishul.iaas.idp.tenant.model.TenantIaasAuthResources`
- `sh.trishul.iaas.idp.tenant.model.TenantIaasIdpDeleteResult`
- `sh.trishul.iaas.idp.tenant.model.TenantIaasIdpResources`
- `sh.trishul.iaas.idp.tenant.model.TenantIaasIdpTenantMapper`
- `sh.trishul.iaas.idp.tenant.model.UpdateIaasIdpTenant`
- `sh.trishul.iaas.idp.tenant.model.mapper.TenantIaasIdpResourcesMapper`

## Tests (Examples)

- `sh.trishul.iaas.idp.tenant.model.IaasIdpTenantTest`
- `sh.trishul.iaas.idp.tenant.model.TenantIaasAuthDeleteResultTest`
- `sh.trishul.iaas.idp.tenant.model.TenantIaasAuthResourceMapperTest`
- `sh.trishul.iaas.idp.tenant.model.TenantIaasAuthResourcesTest`
- `sh.trishul.iaas.idp.tenant.model.TenantIaasIdpDeleteResultTest`
- `sh.trishul.iaas.idp.tenant.model.TenantIaasIdpResourcesTest`
- `sh.trishul.iaas.idp.tenant.model.TenantIaasIdpTenantMapperTest`
- `sh.trishul.iaas.idp.tenant.model.mapper.TenantIaasIdpResourcesMapperTest`

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
