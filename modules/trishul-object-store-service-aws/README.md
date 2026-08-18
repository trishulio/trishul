# trishul-object-store-service-aws

## Overview
- Objects
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Spring Boot auto-configuration (provides beans via `@Configuration`).
- Includes AWS-related integration (module name/deps indicate AWS support).

## Artifact Coordinates

```xml
<dependency>
  <groupId>io.trishul</groupId>
  <artifactId>trishul-object-store-service-aws</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-object-store-aws](../trishul-object-store-aws/README.md)
- [trishul-object-store-service](../trishul-object-store-service/README.md)
- [trishul-test-bom](../trishul-test-bom/README.md)

### External Dependencies
- `org.springframework.boot:spring-boot-starter-web`

## Configuration

### Application Properties

This module does not define module-specific `*-application.properties` keys (file may be empty).

### Environment Variables

None detected in this module’s properties file.

## AutoConfiguration Beans

- `IaasClient<String, IaasObjectStore, BaseIaasObjectStore<?>, UpdateIaasObjectStore<?>>` via `iaasObjectStoreClient()` — @ConditionalOnMissingBean(AwsObjectStoreClient.class) ([modules/trishul-object-store-service-aws/src/main/java/io/trishul/object/store/service/aws/autoconfiguration/IaasObjectStoreServiceAwsAutoConfiguration.java](modules/trishul-object-store-service-aws/src/main/java/io/trishul/object/store/service/aws/autoconfiguration/IaasObjectStoreServiceAwsAutoConfiguration.java))
- `IaasClient<String, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig>` via `iaasObjectStoreAccessConfigClient()` — @ConditionalOnMissingBean(AwsPublicAccessBlockClient.class) ([modules/trishul-object-store-service-aws/src/main/java/io/trishul/object/store/service/aws/autoconfiguration/IaasObjectStoreServiceAwsAutoConfiguration.java](modules/trishul-object-store-service-aws/src/main/java/io/trishul/object/store/service/aws/autoconfiguration/IaasObjectStoreServiceAwsAutoConfiguration.java))
- `IaasClient<String, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration>` via `iaasObjectStoreCorsConfigClient()` — @ConditionalOnMissingBean(AwsCorsConfigClient.class) ([modules/trishul-object-store-service-aws/src/main/java/io/trishul/object/store/service/aws/autoconfiguration/IaasObjectStoreServiceAwsAutoConfiguration.java](modules/trishul-object-store-service-aws/src/main/java/io/trishul/object/store/service/aws/autoconfiguration/IaasObjectStoreServiceAwsAutoConfiguration.java))

## Key Classes & APIs

- `io.trishul.object.store.service.aws.autoconfiguration.IaasObjectStoreServiceAwsAutoConfiguration`
- `io.trishul.object.store.service.aws.cors.config.AwsCorsConfigClient`
- `io.trishul.object.store.service.aws.cors.config.AwsObjectStoreClient`
- `io.trishul.object.store.service.aws.cors.config.AwsPublicAccessBlockClient`

## Tests (Examples)

- `io.trishul.object.store.service.aws.cors.config.AwsCorsConfigClientTest`
- `io.trishul.object.store.service.aws.cors.config.AwsObjectStoreClientTest`
- `io.trishul.object.store.service.aws.cors.config.AwsPublicAccessBlockClientTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:object-store-service-aws-application.properties
```

Note: the actual file name in this module is: `object-store-service-aws-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- None

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
