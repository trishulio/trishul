# trishul-iaas-auth-aws

## Overview
- IaaS authentication AWS
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Spring Boot auto-configuration (provides beans via `@Configuration`).
- Includes AWS-related integration (module name/deps indicate AWS support).

## Artifact Coordinates

```xml
<dependency>
  <groupId>sh.trishul</groupId>
  <artifactId>trishul-iaas-auth-aws</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-iaas](../trishul-iaas/README.md)
- [trishul-iaas-auth](../trishul-iaas-auth/README.md)
- [trishul-test-bom](../trishul-test-bom/README.md)

### External Dependencies
- `com.amazonaws:aws-java-sdk-cognitoidentity`
- `com.amazonaws:aws-java-sdk-cognitoidp`

## Configuration

### Application Properties

This module does not define module-specific `*-application.properties` keys (file may be empty).

### Environment Variables

None detected in this module’s properties file.

## AutoConfiguration Beans

- `IaasAuthAwsFactory` via `iaasAuthAwsFactory()` — @ConditionalOnMissingBean(IaasAuthAwsFactory.class) ([modules/trishul-iaas-auth-aws/src/main/java/sh/trishul/iaas/auth/aws/autoconfiguration/IaasAuthAwsAutoConfiguration.java](modules/trishul-iaas-auth-aws/src/main/java/sh/trishul/iaas/auth/aws/autoconfiguration/IaasAuthAwsAutoConfiguration.java))
- `AmazonCognitoIdentity` via `amazonCognitoIdentity()` — @ConditionalOnMissingBean(AmazonCognitoIdentity.class) ([modules/trishul-iaas-auth-aws/src/main/java/sh/trishul/iaas/auth/aws/autoconfiguration/IaasAuthAwsAutoConfiguration.java](modules/trishul-iaas-auth-aws/src/main/java/sh/trishul/iaas/auth/aws/autoconfiguration/IaasAuthAwsAutoConfiguration.java))
- `AWSCognitoIdentityProvider` via `awsCognitoIdpProvider()` — @ConditionalOnMissingBean(AWSCognitoIdentityProvider.class) ([modules/trishul-iaas-auth-aws/src/main/java/sh/trishul/iaas/auth/aws/autoconfiguration/IaasAuthAwsAutoConfiguration.java](modules/trishul-iaas-auth-aws/src/main/java/sh/trishul/iaas/auth/aws/autoconfiguration/IaasAuthAwsAutoConfiguration.java))
- `AwsCognitoIdentityClient` via `awsCognitoIdentityClient()` — @ConditionalOnMissingBean(AwsCognitoIdentityClient.class) ([modules/trishul-iaas-auth-aws/src/main/java/sh/trishul/iaas/auth/aws/autoconfiguration/IaasAuthAwsAutoConfiguration.java](modules/trishul-iaas-auth-aws/src/main/java/sh/trishul/iaas/auth/aws/autoconfiguration/IaasAuthAwsAutoConfiguration.java))
- `IaasAuthorizationFetcher` via `iaasAuthorizationFetcher()` — @ConditionalOnMissingBean(IaasAuthorizationFetcher.class) ([modules/trishul-iaas-auth-aws/src/main/java/sh/trishul/iaas/auth/aws/autoconfiguration/IaasAuthAwsAutoConfiguration.java](modules/trishul-iaas-auth-aws/src/main/java/sh/trishul/iaas/auth/aws/autoconfiguration/IaasAuthAwsAutoConfiguration.java))

## Key Classes & APIs

- `sh.trishul.iaas.auth.aws.autoconfiguration.IaasAuthAwsAutoConfiguration`
- `sh.trishul.iaas.auth.aws.client.AwsCognitoIdentityClient`
- `sh.trishul.iaas.auth.aws.client.AwsCognitoIdentitySdkWrapper`
- `sh.trishul.iaas.auth.aws.client.AwsIdentityCredentialsMapper`
- `sh.trishul.iaas.auth.aws.client.AwsResourceCredentialsFetcher`
- `sh.trishul.iaas.auth.aws.client.CachedAwsCognitoIdentityClient`
- `sh.trishul.iaas.auth.aws.factory.IaasAuthAwsFactory`

## Tests (Examples)

- `sh.trishul.iaas.auth.aws.client.AwsCognitoIdentitySdkWrapperTest`
- `sh.trishul.iaas.auth.aws.client.AwsIdentityCredentialsMapperTest`
- `sh.trishul.iaas.auth.aws.client.AwsResourceCredentialsFetcherTest`
- `sh.trishul.iaas.auth.aws.client.CachedAwsCognitoIdentityClientTest`
- `sh.trishul.iaas.auth.aws.factory.IaasAuthAwsFactoryTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:iaas-auth-aws-application.properties
```

Note: the actual file name in this module is: `iaas-auth-aws-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- [trishul-object-store-file-service-aws](../trishul-object-store-file-service-aws/README.md)

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
