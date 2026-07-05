# trishul-object-store-file

## Overview
- Objects
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Library module (consume types directly; may still include auto-config classes).

## Artifact Coordinates

```xml
<dependency>
  <groupId>io.trishul</groupId>
  <artifactId>trishul-object-store-file</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-model](../trishul-model/README.md)
- [trishul-test-bom](../trishul-test-bom/README.md)

### External Dependencies
- `org.springframework.boot:spring-boot-starter-web`

## Configuration

### Application Properties

This module does not define module-specific `*-application.properties` keys (file may be empty).

### Environment Variables

None detected in this module’s properties file.

## AutoConfiguration Beans

- No Spring `@Bean` methods detected in this module.

## Key Classes & APIs

- `io.trishul.object.store.file.decorator.EntityDecorator`
- `io.trishul.object.store.file.decorator.NoActionDecorator`
- `io.trishul.object.store.file.model.BaseIaasObjectStoreFile`
- `io.trishul.object.store.file.model.IaasObjectStoreFile`
- `io.trishul.object.store.file.model.IaasObjectStoreFileMapper`
- `io.trishul.object.store.file.model.UpdateIaasObjectStoreFile`
- `io.trishul.object.store.file.model.accessor.DecoratedIaasObjectStoreFileAccessor`
- `io.trishul.object.store.file.model.accessor.IaasObjectStoreFileAccessor`
- `io.trishul.object.store.file.model.dto.AddIaasObjectStoreFileDto`
- `io.trishul.object.store.file.model.dto.IaasObjectStoreFileDto`
- `io.trishul.object.store.file.model.dto.UpdateIaasObjectStoreFileDto`

## Tests (Examples)

- `io.trishul.object.store.file.model.IaasObjectStoreFileMapperTest`
- `io.trishul.object.store.file.model.IaasObjectStoreFileTest`
- `io.trishul.object.store.file.model.dto.AddIaasObjectStoreFileDtoTest`
- `io.trishul.object.store.file.model.dto.IaasObjectStoreFileDtoTest`
- `io.trishul.object.store.file.model.dto.UpdateIaasObjectStoreFileDtoTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:object-store-file-application.properties
```

Note: the actual file name in this module is: `object-store-file-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- [trishul-crud](../trishul-crud/README.md)
- [trishul-object-store-file-service](../trishul-object-store-file-service/README.md)
- [trishul-user](../trishul-user/README.md)

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
