# trishul-object-store-aws

AWS S3 implementation for the object store abstraction layer.

> **Use this when**: You need to use AWS S3 as your object storage backend for tenant file storage.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-object-store-aws</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Auto-configuration sets up S3 client and mappers
@Autowired
private AmazonS3 s3Client;

// 2. Map S3 buckets to IaasObjectStore entities
Bucket s3Bucket = s3Client.listBuckets().get(0);
IaasObjectStore objectStore = AwsIaasObjectStoreMapper.INSTANCE.fromIaasEntity(s3Bucket);

// 3. Access bucket properties through the abstraction
String bucketName = objectStore.getName();
LocalDateTime createdAt = objectStore.getCreatedAt();
```

## When Would I Use This?

### When you need to map S3 buckets to domain entities

Use `AwsIaasObjectStoreMapper` to convert AWS SDK types to Trishul entities:

```java
// Convert S3 Bucket to IaasObjectStore
Bucket s3Bucket = s3Client.createBucket("tenant-123-files");
IaasObjectStore objectStore = AwsIaasObjectStoreMapper.INSTANCE.fromIaasEntity(s3Bucket);

// Use in your domain layer
tenantResources.setObjectStore(objectStore);
```

### When you need to create S3 clients programmatically

Use `ObjectStoreAwsFactory`:

```java
@Configuration
public class S3Config {

    @Bean
    public AmazonS3 s3Client(AWSCredentialsProvider credentials) {
        return ObjectStoreAwsFactory.buildS3Client(
            credentials,
            "us-east-1"
        );
    }
}
```

### When you need AWS-specific object store configuration

The auto-configuration registers S3-specific beans:

```java
@Autowired
private AmazonS3 s3Client;

// Create tenant-specific bucket
public void createTenantBucket(UUID tenantId) {
    String bucketName = "myapp-tenant-" + tenantId;
    s3Client.createBucket(bucketName);
}
```

## Core Components

| Class | Purpose |
|-------|---------|
| `AwsIaasObjectStoreMapper` | Maps S3 `Bucket` to `IaasObjectStore` |
| `ObjectStoreAwsFactory` | Factory for creating S3 clients |
| `IaasObjectStoreAwsConfiguration` | Spring Boot auto-configuration |

## Mapper Details

The `AwsIaasObjectStoreMapper` maps:

| S3 Bucket Field | IaasObjectStore Field |
|-----------------|----------------------|
| `name` | `name` |
| `creationDate` | `createdAt` |
| (generated) | `id` (ignored, uses name) |
| - | `lastUpdated` (ignored) |

## AWS Configuration

Ensure AWS credentials are configured via:
- Environment variables (`AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`)
- IAM role (for EC2/ECS/Lambda)
- AWS credentials file

Required IAM permissions:
```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "s3:CreateBucket",
        "s3:DeleteBucket",
        "s3:ListBucket",
        "s3:PutObject",
        "s3:GetObject",
        "s3:DeleteObject"
      ],
      "Resource": [
        "arn:aws:s3:::myapp-*",
        "arn:aws:s3:::myapp-*/*"
      ]
    }
  ]
}
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-object-store` | Abstract object store interfaces |
| `trishul-iaas` | IaaS abstraction layer |
| `trishul-iaas-tenant-object-store-aws` | Tenant-specific S3 provisioning |
