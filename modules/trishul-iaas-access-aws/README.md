# trishul-iaas-access-aws

## Overview
- IaaS Access
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Spring Boot auto-configuration (provides beans via `@Configuration`).
- Configurable via `*-application.properties` (importable into your app).
- Includes AWS-related integration (module name/deps indicate AWS support).

## Artifact Coordinates

```xml
<dependency>
  <groupId>io.trishul</groupId>
  <artifactId>trishul-iaas-access-aws</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-iaas-access](../trishul-iaas-access/README.md)
- [trishul-test-bom](../trishul-test-bom/README.md)

### External Dependencies
- `com.amazonaws:aws-java-sdk-iam`

## Configuration

### Application Properties

```properties
aws.deployment.accountId=${AWS_DEPLOYMENT_ACCOUNT_ID}
aws.deployment.parition=${AWS_DEPLOYMENT_PARTITION}
aws.iam.access-key=${AWS_IAM_ACCESS_KEY_ID}
aws.iam.access-secret=${AWS_IAM_ACCESS_SECRET_KEY}
```

### Environment Variables

```bash
export AWS_DEPLOYMENT_ACCOUNT_ID=...
export AWS_DEPLOYMENT_PARTITION=...
export AWS_IAM_ACCESS_KEY_ID=...
export AWS_IAM_ACCESS_SECRET_KEY=...
```

## AutoConfiguration Beans

- `IaasAccessAwsFactory` via `iaasAccessAwsFactory()` — @ConditionalOnMissingBean(IaasAccessAwsFactory.class) ([modules/trishul-iaas-access-aws/src/main/java/io/trishul/iaas/access/aws/autoconfiguration/IaasAccessAwsAutoConfiguration.java](modules/trishul-iaas-access-aws/src/main/java/io/trishul/iaas/access/aws/autoconfiguration/IaasAccessAwsAutoConfiguration.java))
- `AmazonIdentityManagement` via `iamClient()` — @ConditionalOnMissingBean(AmazonIdentityManagement.class) ([modules/trishul-iaas-access-aws/src/main/java/io/trishul/iaas/access/aws/autoconfiguration/IaasAccessAwsAutoConfiguration.java](modules/trishul-iaas-access-aws/src/main/java/io/trishul/iaas/access/aws/autoconfiguration/IaasAccessAwsAutoConfiguration.java))
- `AwsArnMapper` via `arnMapper()` — @ConditionalOnMissingBean(AwsArnMapper.class) ([modules/trishul-iaas-access-aws/src/main/java/io/trishul/iaas/access/aws/autoconfiguration/IaasAccessAwsAutoConfiguration.java](modules/trishul-iaas-access-aws/src/main/java/io/trishul/iaas/access/aws/autoconfiguration/IaasAccessAwsAutoConfiguration.java))
- `IaasClient<String, IaasPolicy, BaseIaasPolicy<?>, UpdateIaasPolicy<?>>` via `iaasPolicyClient()` — @ConditionalOnMissingBean(AwsIamPolicyClient.class) ([modules/trishul-iaas-access-aws/src/main/java/io/trishul/iaas/access/aws/autoconfiguration/IaasAccessAwsAutoConfiguration.java](modules/trishul-iaas-access-aws/src/main/java/io/trishul/iaas/access/aws/autoconfiguration/IaasAccessAwsAutoConfiguration.java))
- `IaasClient<String, IaasRole, BaseIaasRole<?>, UpdateIaasRole<?>>` via `iaasRoleClient()` — @ConditionalOnMissingBean(AwsIamRoleClient.class) ([modules/trishul-iaas-access-aws/src/main/java/io/trishul/iaas/access/aws/autoconfiguration/IaasAccessAwsAutoConfiguration.java](modules/trishul-iaas-access-aws/src/main/java/io/trishul/iaas/access/aws/autoconfiguration/IaasAccessAwsAutoConfiguration.java))
- `IaasClient<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment<?>, UpdateIaasRolePolicyAttachment<?>>` via `awsIamRolePolicyClientClient()` — @ConditionalOnMissingBean(AwsIamRolePolicyAttachmentClient.class) ([modules/trishul-iaas-access-aws/src/main/java/io/trishul/iaas/access/aws/autoconfiguration/IaasAccessAwsAutoConfiguration.java](modules/trishul-iaas-access-aws/src/main/java/io/trishul/iaas/access/aws/autoconfiguration/IaasAccessAwsAutoConfiguration.java))

## Key Classes & APIs

- `io.trishul.iaas.access.aws.AwsArnMapper`
- `io.trishul.iaas.access.aws.AwsIaasPolicyMapper`
- `io.trishul.iaas.access.aws.AwsIaasRoleMapper`
- `io.trishul.iaas.access.aws.AwsIamPolicyClient`
- `io.trishul.iaas.access.aws.AwsIamRoleClient`
- `io.trishul.iaas.access.aws.AwsIamRolePolicyAttachmentClient`
- `io.trishul.iaas.access.aws.IaasAccessAwsFactory`
- `io.trishul.iaas.access.aws.autoconfiguration.IaasAccessAwsAutoConfiguration`

## Tests (Examples)

- `io.trishul.iaas.access.aws.AwsArnMapperTest`
- `io.trishul.iaas.access.aws.AwsIaasPolicyMapperTest`
- `io.trishul.iaas.access.aws.AwsIaasRoleMapperTest`
- `io.trishul.iaas.access.aws.AwsIamPolicyClientTest`
- `io.trishul.iaas.access.aws.AwsIamRoleClientTest`
- `io.trishul.iaas.access.aws.AwsIamRolePolicyAttachmentClientTest`
- `io.trishul.iaas.access.aws.IaasAccessAwsFactoryTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:iaas-access-aws-application.properties
```

Note: the actual file name in this module is: `iaas-access-aws-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- [trishul-iaas-tenant-idp-service-aws](../trishul-iaas-tenant-idp-service-aws/README.md)

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
