# trishul-iaas-tenant-idp-management-service

## Overview
- IaaS
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Spring Boot auto-configuration (provides beans via `@Configuration`).

## Artifact Coordinates

```xml
<dependency>
  <groupId>io.trishul</groupId>
  <artifactId>trishul-iaas-tenant-idp-management-service</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-crud](../trishul-crud/README.md)
- [trishul-iaas-access-service](../trishul-iaas-access-service/README.md)
- [trishul-iaas-tenant](../trishul-iaas-tenant/README.md)
- [trishul-iaas-tenant-idp](../trishul-iaas-tenant-idp/README.md)
- [trishul-test](../trishul-test/README.md)

### External Dependencies
- None

## Configuration

### Application Properties

This module does not define module-specific `*-application.properties` keys (file may be empty).

### Environment Variables

None detected in this module’s properties file.

## AutoConfiguration Beans

- `IaasIdpTenantService` via `iaasIdpTenantService()` — @ConditionalOnMissingBean(IaasIdpTenantService.class) ([modules/trishul-iaas-tenant-idp-management-service/src/main/java/io/trishul/iaas/tenant/idp/management/service/autoconfiguration/IaasTenantIdpManagementServiceAutoConfiguration.java](modules/trishul-iaas-tenant-idp-management-service/src/main/java/io/trishul/iaas/tenant/idp/management/service/autoconfiguration/IaasTenantIdpManagementServiceAutoConfiguration.java))
- `TenantIaasIdpService` via `tenantIaasIdpService()` — @ConditionalOnMissingBean(TenantIaasIdpService.class) ([modules/trishul-iaas-tenant-idp-management-service/src/main/java/io/trishul/iaas/tenant/idp/management/service/autoconfiguration/IaasTenantIdpManagementServiceAutoConfiguration.java](modules/trishul-iaas-tenant-idp-management-service/src/main/java/io/trishul/iaas/tenant/idp/management/service/autoconfiguration/IaasTenantIdpManagementServiceAutoConfiguration.java))
- `TenantIaasAuthService` via `tenantIaasAuthService()` — @ConditionalOnMissingBean(TenantIaasAuthService.class) ([modules/trishul-iaas-tenant-idp-management-service/src/main/java/io/trishul/iaas/tenant/idp/management/service/autoconfiguration/IaasTenantIdpManagementServiceAutoConfiguration.java](modules/trishul-iaas-tenant-idp-management-service/src/main/java/io/trishul/iaas/tenant/idp/management/service/autoconfiguration/IaasTenantIdpManagementServiceAutoConfiguration.java))

## Key Classes & APIs

- `io.trishul.iaas.tenant.idp.management.service.IaasIdpTenantService`
- `io.trishul.iaas.tenant.idp.management.service.TenantIaasAuthService`
- `io.trishul.iaas.tenant.idp.management.service.TenantIaasIdpService`
- `io.trishul.iaas.tenant.idp.management.service.autoconfiguration.IaasTenantIdpManagementServiceAutoConfiguration`

## Tests (Examples)

- `io.trishul.iaas.tenant.idp.management.service.IaasIdpTenantServiceTest`
- `io.trishul.iaas.tenant.idp.management.service.TenantIaasAuthServiceTest`
- `io.trishul.iaas.tenant.idp.management.service.TenantIaasIdpServiceTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:iaas-tenant-idp-management-service-application.properties
```

Note: the actual file name in this module is: `iaas-tenant-idp-management-service-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- [trishul-iaas-tenant-idp-service-aws](../trishul-iaas-tenant-idp-service-aws/README.md)
- [trishul-iaas-tenant-service](../trishul-iaas-tenant-service/README.md)

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
