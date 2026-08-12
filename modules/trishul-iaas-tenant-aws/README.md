# trishul-iaas-tenant-aws

## Overview
- IaaS
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Spring Boot auto-configuration (provides beans via `@Configuration`).

## Artifact Coordinates

```xml
<dependency>
  <groupId>sh.trishul</groupId>
  <artifactId>trishul-iaas-tenant-aws</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-iaas-tenant](../trishul-iaas-tenant/README.md)
- [trishul-object-store-file-service](../trishul-object-store-file-service/README.md)
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

- `AwsDocumentTemplates` via `awsDocumentTemplates()` — @ConditionalOnMissingBean(AwsDocumentTemplates.class) ([modules/trishul-iaas-tenant-aws/src/main/java/sh/trishul/iaas/tenant/aws/autoconfiguration/IaasTenantAwsAutoConfiguration.java](modules/trishul-iaas-tenant-aws/src/main/java/sh/trishul/iaas/tenant/aws/autoconfiguration/IaasTenantAwsAutoConfiguration.java))
- `IaasObjectStoreNameProvider` via `iaasObjectStoreNameProvider()` — @ConditionalOnMissingBean(IaasObjectStoreNameProvider.class) ([modules/trishul-iaas-tenant-aws/src/main/java/sh/trishul/iaas/tenant/aws/autoconfiguration/IaasTenantAwsAutoConfiguration.java](modules/trishul-iaas-tenant-aws/src/main/java/sh/trishul/iaas/tenant/aws/autoconfiguration/IaasTenantAwsAutoConfiguration.java))

## Key Classes & APIs

- `sh.trishul.iaas.tenant.aws.AwsDocumentTemplates`
- `sh.trishul.iaas.tenant.aws.AwsTenantIaasResourceBuilder`
- `sh.trishul.iaas.tenant.aws.TenantContextAwsBucketNameProvider`
- `sh.trishul.iaas.tenant.aws.autoconfiguration.IaasTenantAwsAutoConfiguration`

## Tests (Examples)

- `sh.trishul.iaas.tenant.aws.AwsDocumentTemplatesTest`
- `sh.trishul.iaas.tenant.aws.AwsTenantIaasResourceBuilderTest`
- `sh.trishul.iaas.tenant.aws.TenantContextAwsBucketNameProviderTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:iaas-tenant-aws-application.properties
```

Note: the actual file name in this module is: `iaas-tenant-aws-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- None

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
