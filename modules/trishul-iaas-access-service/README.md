# trishul-iaas-access-service

## Overview
- IaaS Access
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Spring Boot auto-configuration (provides beans via `@Configuration`).

## Artifact Coordinates

```xml
<dependency>
  <groupId>io.trishul</groupId>
  <artifactId>trishul-iaas-access-service</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-crud](../trishul-crud/README.md)
- [trishul-iaas-access](../trishul-iaas-access/README.md)
- [trishul-test](../trishul-test/README.md)

### External Dependencies
- None

## Configuration

### Application Properties

This module does not define module-specific `*-application.properties` keys (file may be empty).

### Environment Variables

None detected in this module’s properties file.

## AutoConfiguration Beans

- `IaasRoleService` via `iaasRoleService()` — @ConditionalOnMissingBean(IaasRoleService.class) ([modules/trishul-iaas-access-service/src/main/java/io/trishul/iaas/access/service/autoconfiguration/IaasAccessServiceAutoConfiguration.java](modules/trishul-iaas-access-service/src/main/java/io/trishul/iaas/access/service/autoconfiguration/IaasAccessServiceAutoConfiguration.java))
- `IaasPolicyService` via `iaasPolicyService()` — @ConditionalOnMissingBean(IaasPolicyService.class) ([modules/trishul-iaas-access-service/src/main/java/io/trishul/iaas/access/service/autoconfiguration/IaasAccessServiceAutoConfiguration.java](modules/trishul-iaas-access-service/src/main/java/io/trishul/iaas/access/service/autoconfiguration/IaasAccessServiceAutoConfiguration.java))
- `IaasRolePolicyAttachmentService` via `iaasRolePolicyAttachmentService()` — @ConditionalOnMissingBean(IaasRolePolicyAttachmentService.class) ([modules/trishul-iaas-access-service/src/main/java/io/trishul/iaas/access/service/autoconfiguration/IaasAccessServiceAutoConfiguration.java](modules/trishul-iaas-access-service/src/main/java/io/trishul/iaas/access/service/autoconfiguration/IaasAccessServiceAutoConfiguration.java))

## Key Classes & APIs

- `io.trishul.iaas.access.service.autoconfiguration.IaasAccessServiceAutoConfiguration`
- `io.trishul.iaas.access.service.policy.service.IaasPolicyService`
- `io.trishul.iaas.access.service.role.policy.attachment.service.IaasRolePolicyAttachmentService`
- `io.trishul.iaas.access.service.role.service.IaasRoleService`

## Tests (Examples)

- `io.trishul.iaas.access.service.policy.service.IaasPolicyServiceTest`
- `io.trishul.iaas.access.service.role.service.IaasRoleServiceTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:iaas-access-service-application.properties
```

Note: the actual file name in this module is: `iaas-access-service-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- [trishul-iaas-tenant-idp-management-service](../trishul-iaas-tenant-idp-management-service/README.md)
- [trishul-iaas-tenant-object-store-service](../trishul-iaas-tenant-object-store-service/README.md)

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
