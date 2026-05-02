# trishul-iaas-access

IAM policy and role models for cloud infrastructure access management.

> **Use this when**: You need to represent and manage IAM policies, roles, and role-policy attachments for tenant cloud resources.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-iaas-access</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Create an IAM policy
IaasPolicy policy = new IaasPolicy()
    .setName("tenant-123-s3-policy")
    .setDocument(policyJson)
    .setDescription("S3 access for tenant 123");

// 2. Create an IAM role
IaasRole role = new IaasRole()
    .setName("tenant-123-role")
    .setAssumePolicyDocument(assumeRolePolicy)
    .setDescription("Role for tenant 123");

// 3. Attach policy to role
IaasRolePolicyAttachment attachment = new IaasRolePolicyAttachment()
    .setIaasRole(role)
    .setIaasPolicy(policy);
```

## When Would I Use This?

### When you need to represent IAM policies

Use `IaasPolicy` for policy documents:

```java
IaasPolicy s3Policy = new IaasPolicy()
    .setName("tenant-" + tenantId + "-s3-access")
    .setDocument("""
        {
          "Version": "2012-10-17",
          "Statement": [{
            "Effect": "Allow",
            "Action": ["s3:GetObject", "s3:PutObject"],
            "Resource": "arn:aws:s3:::tenant-bucket/*"
          }]
        }
        """)
    .setDescription("S3 access policy for tenant");

// After creation, AWS sets these
policy.setIaasId("ANPA1234567890");           // AWS Policy ID
policy.setIaasResourceName("arn:aws:iam::...");  // ARN
```

### When you need to represent IAM roles

Use `IaasRole` with assume role policy:

```java
IaasRole role = new IaasRole()
    .setName("tenant-" + tenantId + "-app-role")
    .setAssumePolicyDocument("""
        {
          "Version": "2012-10-17",
          "Statement": [{
            "Effect": "Allow",
            "Principal": {"Service": "ec2.amazonaws.com"},
            "Action": "sts:AssumeRole"
          }]
        }
        """)
    .setDescription("Application role for tenant");
```

### When you need to attach policies to roles

Use `IaasRolePolicyAttachment`:

```java
IaasRolePolicyAttachment attachment = new IaasRolePolicyAttachment()
    .setIaasRole(role)
    .setIaasPolicy(policy);

// Composite ID for the attachment
IaasRolePolicyAttachmentId id = new IaasRolePolicyAttachmentId(
    role.getName(),
    policy.getName()
);
```

### When you need to track role usage

`IaasRole` includes usage tracking:

```java
IaasRole role = roleService.get(roleName);
LocalDateTime lastUsed = role.getLastUsed();  // When role was last assumed
```

## IaasPolicy Fields

| Field | Type | Description |
|-------|------|-------------|
| `name` | `String` | Policy name (used as ID) |
| `document` | `String` | JSON policy document |
| `description` | `String` | Human-readable description |
| `iaasId` | `String` | Cloud provider policy ID |
| `iaasResourceName` | `String` | ARN or resource identifier |
| `createdAt` | `LocalDateTime` | Creation timestamp |
| `lastUpdated` | `LocalDateTime` | Last update timestamp |

## IaasRole Fields

| Field | Type | Description |
|-------|------|-------------|
| `name` | `String` | Role name (used as ID) |
| `description` | `String` | Human-readable description |
| `assumePolicyDocument` | `String` | Trust policy JSON |
| `iaasId` | `String` | Cloud provider role ID |
| `iaasResourceName` | `String` | ARN or resource identifier |
| `lastUsed` | `LocalDateTime` | When role was last assumed |
| `createdAt` | `LocalDateTime` | Creation timestamp |
| `lastUpdated` | `LocalDateTime` | Last update timestamp |

## Core Components

### Models

| Class | Purpose |
|-------|---------|
| `IaasPolicy` | IAM policy representation |
| `BaseIaasPolicy` | Base interface for policy creation |
| `UpdateIaasPolicy` | Interface for policy updates |
| `IaasPolicyAccessor` | Accessor for entities with policies |
| `IaasRole` | IAM role representation |
| `BaseIaasRole` | Base interface for role creation |
| `UpdateIaasRole` | Interface for role updates |
| `IaasRoleAccessor` | Accessor for entities with roles |
| `IaasRolePolicyAttachment` | Role-policy binding |
| `IaasRolePolicyAttachmentId` | Composite ID for attachment |

## Complete Example

```java
@Service
public class TenantIamService {

    private final IaasPolicyClient policyClient;
    private final IaasRoleClient roleClient;

    public TenantIaasAuthResources provisionTenantIam(Tenant tenant) {
        String tenantPrefix = "tenant-" + tenant.getId();

        // Create S3 access policy
        IaasPolicy s3Policy = policyClient.create(new IaasPolicy()
            .setName(tenantPrefix + "-s3-policy")
            .setDocument(buildS3PolicyDocument(tenant))
            .setDescription("S3 access for " + tenant.getName()));

        // Create application role
        IaasRole appRole = roleClient.create(new IaasRole()
            .setName(tenantPrefix + "-app-role")
            .setAssumePolicyDocument(buildAssumeRolePolicy())
            .setDescription("App role for " + tenant.getName()));

        // Attach policy to role
        roleClient.attachPolicy(appRole.getName(), s3Policy.getName());

        return new TenantIaasAuthResources()
            .setPolicy(s3Policy)
            .setRole(appRole);
    }
}
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-iaas-access-aws` | AWS IAM implementation |
| `trishul-iaas-access-service` | Service layer for IAM operations |
| `trishul-iaas-tenant` | Tenant IaaS resource aggregation |
| `trishul-iaas-auth` | Authorization credentials |
