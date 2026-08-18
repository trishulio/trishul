# trishul-iaas-user-service-aws

## Overview
- User
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Spring Boot auto-configuration (provides beans via `@Configuration`).
- Includes AWS-related integration (module name/deps indicate AWS support).

## Artifact Coordinates

```xml
<dependency>
  <groupId>sh.trishul</groupId>
  <artifactId>trishul-iaas-user-service-aws</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-auth-aws](../trishul-auth-aws/README.md)
- [trishul-crud](../trishul-crud/README.md)
- [trishul-iaas-user-aws](../trishul-iaas-user-aws/README.md)
- [trishul-test-bom](../trishul-test-bom/README.md)

### External Dependencies
- `com.amazonaws:aws-java-sdk-cognitoidp`

## Configuration

### Application Properties

This module does not define module-specific `*-application.properties` keys (file may be empty).

### Environment Variables

None detected in this module’s properties file.

## AutoConfiguration Beans

- `IaasClient<String, IaasUser, BaseIaasUser<?>, UpdateIaasUser<?>>` via `awsUserClient()` — @ConditionalOnMissingBean(AwsCognitoUserClient.class) ([modules/trishul-iaas-user-service-aws/src/main/java/sh/trishul/iaas/user/service/aws/autoconfiguration/IaasUserServiceAwsAutoConfiguration.java](modules/trishul-iaas-user-service-aws/src/main/java/sh/trishul/iaas/user/service/aws/autoconfiguration/IaasUserServiceAwsAutoConfiguration.java))

## Key Classes & APIs

- `sh.trishul.iaas.user.service.aws.AwsCognitoUserClient`
- `sh.trishul.iaas.user.service.aws.autoconfiguration.IaasUserServiceAwsAutoConfiguration`

## Tests (Examples)

- `sh.trishul.iaas.user.service.aws.AwsCognitoUserClientTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:iaas-user-service-aws-application.properties
```

Note: the actual file name in this module is: `iaas-user-service-aws-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- None

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
