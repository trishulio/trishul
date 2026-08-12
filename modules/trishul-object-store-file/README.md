# trishul-object-store-file

## Overview
- Objects
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Library module (consume types directly; may still include auto-config classes).

## Artifact Coordinates

```xml
<dependency>
  <groupId>sh.trishul</groupId>
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

- `sh.trishul.object.store.file.decorator.EntityDecorator`
- `sh.trishul.object.store.file.decorator.NoActionDecorator`
- `sh.trishul.object.store.file.model.BaseIaasObjectStoreFile`
- `sh.trishul.object.store.file.model.IaasObjectStoreFile`
- `sh.trishul.object.store.file.model.IaasObjectStoreFileMapper`
- `sh.trishul.object.store.file.model.UpdateIaasObjectStoreFile`
- `sh.trishul.object.store.file.model.accessor.DecoratedIaasObjectStoreFileAccessor`
- `sh.trishul.object.store.file.model.accessor.IaasObjectStoreFileAccessor`
- `sh.trishul.object.store.file.model.dto.AddIaasObjectStoreFileDto`
- `sh.trishul.object.store.file.model.dto.IaasObjectStoreFileDto`
- `sh.trishul.object.store.file.model.dto.UpdateIaasObjectStoreFileDto`

## Tests (Examples)

- `sh.trishul.object.store.file.model.IaasObjectStoreFileMapperTest`
- `sh.trishul.object.store.file.model.IaasObjectStoreFileTest`
- `sh.trishul.object.store.file.model.dto.AddIaasObjectStoreFileDtoTest`
- `sh.trishul.object.store.file.model.dto.IaasObjectStoreFileDtoTest`
- `sh.trishul.object.store.file.model.dto.UpdateIaasObjectStoreFileDtoTest`

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
