# trishul-object-store-file-service-aws

## Overview
- AWS Implementation for ObjectStoreService
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Spring Boot auto-configuration (provides beans via `@Configuration`).
- Configurable via `*-application.properties` (importable into your app).
- Includes AWS-related integration (module name/deps indicate AWS support).

## Artifact Coordinates

```xml
<dependency>
  <groupId>sh.trishul</groupId>
  <artifactId>trishul-object-store-file-service-aws</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-auth-aws](../trishul-auth-aws/README.md)
- [trishul-iaas](../trishul-iaas/README.md)
- [trishul-iaas-auth-aws](../trishul-iaas-auth-aws/README.md)
- [trishul-object-store-file-service](../trishul-object-store-file-service/README.md)
- [trishul-test-bom](../trishul-test-bom/README.md)

### External Dependencies
- `com.amazonaws:aws-java-sdk-s3`

## Configuration

### Application Properties

```properties
app.object-store.file.get.url.expiry=24
app.object-store.bucket.name=root-bucket
```

### Environment Variables

None detected in this module’s properties file.

## AutoConfiguration Beans

- `ObjectStoreFileServiceAwsFactory` via `objectStoreFileServiceAwsFactory()` — @ConditionalOnMissingBean(ObjectStoreFileServiceAwsFactory.class) ([modules/trishul-object-store-file-service-aws/src/main/java/sh/trishul/object/store/file/service/aws/autoconfiguration/ObjectStoreFileServiceAwsAutoConfiguration.java](modules/trishul-object-store-file-service-aws/src/main/java/sh/trishul/object/store/file/service/aws/autoconfiguration/ObjectStoreFileServiceAwsAutoConfiguration.java))
- `IaasRepositoryProvider<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>>` via `iaasObjectStoreFileClientProvider()` — @ConditionalOnMissingBean(IaasRepositoryProvider.class) ([modules/trishul-object-store-file-service-aws/src/main/java/sh/trishul/object/store/file/service/aws/autoconfiguration/ObjectStoreFileServiceAwsAutoConfiguration.java](modules/trishul-object-store-file-service-aws/src/main/java/sh/trishul/object/store/file/service/aws/autoconfiguration/ObjectStoreFileServiceAwsAutoConfiguration.java))

## Key Classes & APIs

- `sh.trishul.object.store.file.service.aws.autoconfiguration.ObjectStoreFileServiceAwsAutoConfiguration`
- `sh.trishul.object.store.file.service.aws.client.AwsS3FileClient`
- `sh.trishul.object.store.file.service.aws.client.provider.TenantContextAwsObjectStoreFileClientProvider`
- `sh.trishul.object.store.file.service.aws.factory.ObjectStoreFileServiceAwsFactory`

## Tests (Examples)

- `sh.trishul.object.store.file.service.aws.client.AwsS3FileClientTest`
- `sh.trishul.object.store.file.service.aws.client.provider.TenantContextAwsObjectStoreFileClientProviderTest`
- `sh.trishul.object.store.file.service.aws.factory.ObjectStoreFileServiceAwsFactoryTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:object-store-file-service-aws-application.properties
```

Note: the actual file name in this module is: `object-store-file-service-aws-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- None

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
