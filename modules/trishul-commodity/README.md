# trishul-commodity

## Overview
- Commodity
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Library module (consume types directly; may still include auto-config classes).

## Artifact Coordinates

```xml
<dependency>
  <groupId>sh.trishul</groupId>
  <artifactId>trishul-commodity</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-model](../trishul-model/README.md)
- [trishul-money](../trishul-money/README.md)
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

- No Spring `@Bean` methods detected in this module.

## Key Classes & APIs

- `sh.trishul.commodity.AmountCalculator`
- `sh.trishul.commodity.CostCalculator`
- `sh.trishul.commodity.good.model.Good`
- `sh.trishul.commodity.model.Commodity`

## Tests (Examples)

- `sh.trishul.commodity.AmountCalculatorTest`
- `sh.trishul.commodity.CostCalculatorTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:commodity-application.properties
```

Note: the actual file name in this module is: `commodity-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- None

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
