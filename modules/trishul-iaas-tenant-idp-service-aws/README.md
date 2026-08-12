# trishul-iaas-tenant-idp-service-aws

## Overview
- IaaS
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Spring Boot auto-configuration (provides beans via `@Configuration`).
- Configurable via `*-application.properties` (importable into your app).
- Includes AWS-related integration (module name/deps indicate AWS support).

## Artifact Coordinates

```xml
<dependency>
  <groupId>sh.trishul</groupId>
  <artifactId>trishul-iaas-tenant-idp-service-aws</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-iaas-access-aws](../trishul-iaas-access-aws/README.md)
- [trishul-iaas-tenant-idp-management-service](../trishul-iaas-tenant-idp-management-service/README.md)
- [trishul-iaas-user](../trishul-iaas-user/README.md)
- [trishul-test-bom](../trishul-test-bom/README.md)

### External Dependencies
- `com.amazonaws:aws-java-sdk-cognitoidentity`
- `com.amazonaws:aws-java-sdk-cognitoidp`

## Configuration

### Application Properties

```properties
aws.cognito.region=${AWS_COGNITO_REGION}
aws.cognito.url=${AWS_COGNITO_URL}
aws.cognito.user-pool.id=${AWS_COGNITO_USER_POOL_ID}
aws.cognito.user-pool.url=${AWS_COGNITO_USER_POOL_URL}
aws.cognito.access-key=${AWS_COGNITO_ACCESS_KEY_ID}
aws.cognito.access-secret=${AWS_COGNITO_SECRET_KEY}
aws.cognito.identity.pool.id=${AWS_COGNITO_IDENTITY_POOL_ID}
```

### Environment Variables

```bash
export AWS_COGNITO_ACCESS_KEY_ID=...
export AWS_COGNITO_IDENTITY_POOL_ID=...
export AWS_COGNITO_REGION=...
export AWS_COGNITO_SECRET_KEY=...
export AWS_COGNITO_URL=...
export AWS_COGNITO_USER_POOL_ID=...
export AWS_COGNITO_USER_POOL_URL=...
```

## AutoConfiguration Beans

- `IaasClient<String, IaasIdpTenant, BaseIaasIdpTenant<?>, UpdateIaasIdpTenant<?>>` via `iaasIdpTenantClient()` — @ConditionalOnMissingBean(AwsIdpTenantWithRoleClient.class) ([modules/trishul-iaas-tenant-idp-service-aws/src/main/java/sh/trishul/iaas/tenant/idp/service/aws/autoconfiguration/IaasTenantIdpServiceAwsAutoConfiguration.java](modules/trishul-iaas-tenant-idp-service-aws/src/main/java/sh/trishul/iaas/tenant/idp/service/aws/autoconfiguration/IaasTenantIdpServiceAwsAutoConfiguration.java))
- `IaasClient<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership<?>, UpdateIaasUserTenantMembership<?>>` via `awsCognitoUserGroupMembership()` — @ConditionalOnMissingBean(AwsIaasUserTenantMembershipClient.class) ([modules/trishul-iaas-tenant-idp-service-aws/src/main/java/sh/trishul/iaas/tenant/idp/service/aws/autoconfiguration/IaasTenantIdpServiceAwsAutoConfiguration.java](modules/trishul-iaas-tenant-idp-service-aws/src/main/java/sh/trishul/iaas/tenant/idp/service/aws/autoconfiguration/IaasTenantIdpServiceAwsAutoConfiguration.java))

## Key Classes & APIs

- `sh.trishul.iaas.tenant.idp.service.aws.autoconfiguration.IaasTenantIdpServiceAwsAutoConfiguration`
- `sh.trishul.iaas.tenant.idp.service.aws.cognito.client.AwsCognitoIdentityClient`
- `sh.trishul.iaas.tenant.idp.service.aws.cognito.client.AwsCognitoIdentitySdkWrapper`
- `sh.trishul.iaas.tenant.idp.service.aws.cognito.client.AwsGroupTypeMapper`
- `sh.trishul.iaas.tenant.idp.service.aws.cognito.client.AwsIaasUserTenantMembershipClient`
- `sh.trishul.iaas.tenant.idp.service.aws.cognito.client.AwsIdpTenantWithRoleClient`

## Tests (Examples)

- `sh.trishul.iaas.tenant.idp.service.aws.cognito.client.AwsCognitoIdentitySdkWrapperTest`
- `sh.trishul.iaas.tenant.idp.service.aws.cognito.client.AwsGroupTypeMapperTest`
- `sh.trishul.iaas.tenant.idp.service.aws.cognito.client.AwsIaasUserTenantMembershipClientTest`
- `sh.trishul.iaas.tenant.idp.service.aws.cognito.client.AwsIdpTenantWithRoleClientTest`

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:iaas-tenant-idp-service-aws-application.properties
```

Note: the actual file name in this module is: `iaas-tenant-idp-service-aws-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- None

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
