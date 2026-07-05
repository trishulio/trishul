# trishul-object-store-service

## Overview
- Objects
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Spring Boot auto-configuration (provides beans via `@Configuration`).

## Artifact Coordinates

```xml
<dependency>
  <groupId>io.trishul</groupId>
  <artifactId>trishul-object-store-service</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-crud](../trishul-crud/README.md)
- [trishul-iaas](../trishul-iaas/README.md)
- [trishul-object-store](../trishul-object-store/README.md)
- [trishul-test](../trishul-test/README.md)

### External Dependencies
- None

## Configuration

### Application Properties

This module does not define module-specific `*-application.properties` keys (file may be empty).

### Environment Variables

None detected in this module’s properties file.

## AutoConfiguration Beans

- `IaasObjectStoreService` via `iaasObjectStoreService()` — @ConditionalOnMissingBean(IaasObjectStoreService.class) ([modules/trishul-object-store-service/src/main/java/io/trishul/object/store/service/autoconfiguration/IaasObjectStoreAutoConfiguration.java](modules/trishul-object-store-service/src/main/java/io/trishul/object/store/service/autoconfiguration/IaasObjectStoreAutoConfiguration.java))
- `IaasObjectStoreCorsConfigService` via `iaasObjectStoreCorsConfigService()` — @ConditionalOnMissingBean(IaasObjectStoreCorsConfigService.class) ([modules/trishul-object-store-service/src/main/java/io/trishul/object/store/service/autoconfiguration/IaasObjectStoreAutoConfiguration.java](modules/trishul-object-store-service/src/main/java/io/trishul/object/store/service/autoconfiguration/IaasObjectStoreAutoConfiguration.java))
- `IaasObjectStoreAccessConfigService` via `iaasPublicAccessBlockService()` — @ConditionalOnMissingBean(IaasObjectStoreAccessConfigService.class) ([modules/trishul-object-store-service/src/main/java/io/trishul/object/store/service/autoconfiguration/IaasObjectStoreAutoConfiguration.java](modules/trishul-object-store-service/src/main/java/io/trishul/object/store/service/autoconfiguration/IaasObjectStoreAutoConfiguration.java))

## Key Classes & APIs

- `io.trishul.object.store.service.IaasObjectStoreService`
- `io.trishul.object.store.service.autoconfiguration.IaasObjectStoreAutoConfiguration`
- `io.trishul.object.store.service.cors.config.service.IaasObjectStoreAccessConfigService`
- `io.trishul.object.store.service.cors.config.service.IaasObjectStoreCorsConfigService`

## Tests (Examples)

- `io.trishul.object.store.service.IaasObjectStoreServiceTest`
- `io.trishul.object.store.service.cors.config.service.IaasObjectStoreAccessConfigServiceTest`
- `io.trishul.object.store.service.cors.config.service.IaasObjectStoreCorsConfigServiceTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:object-store-service-application.properties
```

Note: the actual file name in this module is: `object-store-service-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- [trishul-iaas-tenant-object-store-service](../trishul-iaas-tenant-object-store-service/README.md)
- [trishul-object-store-service-aws](../trishul-object-store-service-aws/README.md)

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
