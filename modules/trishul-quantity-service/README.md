# trishul-quantity-service

## Overview
- Quantity Service
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Spring Boot auto-configuration (provides beans via `@Configuration`).

## Artifact Coordinates

```xml
<dependency>
  <groupId>sh.trishul</groupId>
  <artifactId>trishul-quantity-service</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-crud](../trishul-crud/README.md)
- [trishul-quantity](../trishul-quantity/README.md)
- [trishul-test-bom](../trishul-test-bom/README.md)

### External Dependencies
- None

## Configuration

### Application Properties

This module does not define module-specific `*-application.properties` keys (file may be empty).

### Environment Variables

None detected in this module’s properties file.

## AutoConfiguration Beans

- `QuantityUnitService` via `quantityUnitService()` — @ConditionalOnMissingBean ([modules/trishul-quantity-service/src/main/java/sh/trishul/quantity/service/autoconfiguration/QuantityServiceAutoConfiguration.java](modules/trishul-quantity-service/src/main/java/sh/trishul/quantity/service/autoconfiguration/QuantityServiceAutoConfiguration.java))

## Key Classes & APIs

- `sh.trishul.quantity.service.autoconfiguration.QuantityServiceAutoConfiguration`
- `sh.trishul.quantity.service.unit.repository.QuantityUnitRepository`
- `sh.trishul.quantity.service.unit.service.QuantityUnitService`
- `sh.trishul.quantity.service.unit.service.QuantityUnitServiceImpl`

## Tests (Examples)

- `sh.trishul.quantity.service.unit.service.QuantityUnitServiceImplTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:quantity-service-application.properties
```

Note: the actual file name in this module is: `quantity-service-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- [trishul-quantity-management-service](../trishul-quantity-management-service/README.md)

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
