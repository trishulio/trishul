# trishul-repo-aggregation

## Overview
- Aggregation Service
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Spring Boot auto-configuration (provides beans via `@Configuration`).

## Artifact Coordinates

```xml
<dependency>
  <groupId>sh.trishul</groupId>
  <artifactId>trishul-repo-aggregation</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-crud](../trishul-crud/README.md)
- [trishul-test-bom](../trishul-test-bom/README.md)

### External Dependencies
- None

## Configuration

### Application Properties

This module does not define module-specific `*-application.properties` keys (file may be empty).

### Environment Variables

None detected in this module’s properties file.

## AutoConfiguration Beans

- `AggregationRepository` via `aggregationRepository()` — @ConditionalOnMissingBean(AggregationRepository.class) ([modules/trishul-repo-aggregation/src/main/java/sh/trishul/repo/aggregation/autoconfiguration/RepoAggregationAutoConfiguration.java](modules/trishul-repo-aggregation/src/main/java/sh/trishul/repo/aggregation/autoconfiguration/RepoAggregationAutoConfiguration.java))
- `AggregationService` via `aggregationService()` — @ConditionalOnMissingBean(AggregationService.class) ([modules/trishul-repo-aggregation/src/main/java/sh/trishul/repo/aggregation/autoconfiguration/RepoAggregationAutoConfiguration.java](modules/trishul-repo-aggregation/src/main/java/sh/trishul/repo/aggregation/autoconfiguration/RepoAggregationAutoConfiguration.java))

## Key Classes & APIs

- `sh.trishul.repo.aggregation.autoconfiguration.RepoAggregationAutoConfiguration`
- `sh.trishul.repo.aggregation.repo.AggregationRepository`
- `sh.trishul.repo.aggregation.service.AggregationService`
- `sh.trishul.repo.aggregation.service.function.AggregationFunction`

## Tests (Examples)

- `sh.trishul.repo.aggregation.autoconfiguration.RepoAggregationAutoConfigurationTest`
- `sh.trishul.repo.aggregation.repo.AggregationRepositoryTest`
- `sh.trishul.repo.aggregation.service.AggregationServiceTest`
- `sh.trishul.repo.aggregation.service.function.AggregationFunctionTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:repo-aggregation-application.properties
```

Note: the actual file name in this module is: `repo-aggregation-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- None

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
