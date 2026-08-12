# trishul-iaas-user-aws

## Overview
- User
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Library module (consume types directly; may still include auto-config classes).
- Includes AWS-related integration (module name/deps indicate AWS support).

## Artifact Coordinates

```xml
<dependency>
  <groupId>sh.trishul</groupId>
  <artifactId>trishul-iaas-user-aws</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-auth-aws](../trishul-auth-aws/README.md)
- [trishul-iaas-user](../trishul-iaas-user/README.md)
- [trishul-test-bom](../trishul-test-bom/README.md)

### External Dependencies
- `com.amazonaws:aws-java-sdk-cognitoidp`

## Configuration

### Application Properties

This module does not define module-specific `*-application.properties` keys (file may be empty).

### Environment Variables

None detected in this module’s properties file.

## AutoConfiguration Beans

- No Spring `@Bean` methods detected in this module.

## Key Classes & APIs

- `sh.trishul.iaas.user.aws.model.AwsCognitoAdminGetUserResultMapper`
- `sh.trishul.iaas.user.aws.model.AwsCognitoUserMapper`

## Tests (Examples)

- `sh.trishul.iaas.user.aws.model.AwsCognitoAdminGetUserResultMapperTest`
- `sh.trishul.iaas.user.aws.model.AwsCognitoUserMapperTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:iaas-user-aws-application.properties
```

Note: the actual file name in this module is: `iaas-user-aws-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- [trishul-iaas-user-service-aws](../trishul-iaas-user-service-aws/README.md)

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
