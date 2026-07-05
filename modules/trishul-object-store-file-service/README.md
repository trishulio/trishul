# trishul-object-store-file-service

## Overview
- Object Service
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Spring Boot auto-configuration (provides beans via `@Configuration`).

## Artifact Coordinates

```xml
<dependency>
  <groupId>io.trishul</groupId>
  <artifactId>trishul-object-store-file-service</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-crud](../trishul-crud/README.md)
- [trishul-iaas](../trishul-iaas/README.md)
- [trishul-object-store-file](../trishul-object-store-file/README.md)
- [trishul-test](../trishul-test/README.md)
- [trishul-test-bom](../trishul-test-bom/README.md)

### External Dependencies
- None

## Configuration

### Application Properties

This module does not define module-specific `*-application.properties` keys (file may be empty).

### Environment Variables

None detected in this module’s properties file.

## AutoConfiguration Beans

- `TemporaryImageSrcDecorator` via `temporaryImageSrcDecorator()` — @ConditionalOnMissingBean(TemporaryImageSrcDecorator.class) ([modules/trishul-object-store-file-service/src/main/java/io/trishul/object/store/file/service/autoconfiguration/IaasObjectStoreFileServiceAutoConfiguration.java](modules/trishul-object-store-file-service/src/main/java/io/trishul/object/store/file/service/autoconfiguration/IaasObjectStoreFileServiceAutoConfiguration.java))
- `IaasObjectStoreFileService` via `iaasObjectStoreFileService()` — @ConditionalOnMissingBean(IaasObjectStoreFileService.class) ([modules/trishul-object-store-file-service/src/main/java/io/trishul/object/store/file/service/autoconfiguration/IaasObjectStoreFileServiceAutoConfiguration.java](modules/trishul-object-store-file-service/src/main/java/io/trishul/object/store/file/service/autoconfiguration/IaasObjectStoreFileServiceAutoConfiguration.java))

## Key Classes & APIs

- `io.trishul.object.store.file.service.autoconfiguration.IaasObjectStoreFileServiceAutoConfiguration`
- `io.trishul.object.store.file.service.controller.IaasObjectStoreFileController`
- `io.trishul.object.store.file.service.decorator.DtoDecorator`
- `io.trishul.object.store.file.service.decorator.TemporaryImageSrcDecorator`
- `io.trishul.object.store.file.service.service.IaasObjectStoreFileService`
- `io.trishul.object.store.file.service.service.IaasObjectStoreNameProvider`

## Tests (Examples)

- `io.trishul.object.store.file.service.controller.IaasObjectStoreFileControllerTest`
- `io.trishul.object.store.file.service.decorator.TemporaryImageSrcDecoratorTest`
- `io.trishul.object.store.file.service.service.IaasObjectStoreFileServiceTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:object-store-file-service-application.properties
```

Note: the actual file name in this module is: `object-store-file-service-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- [trishul-iaas-tenant-aws](../trishul-iaas-tenant-aws/README.md)
- [trishul-object-store-file-service-aws](../trishul-object-store-file-service-aws/README.md)
- [trishul-user-service](../trishul-user-service/README.md)

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
