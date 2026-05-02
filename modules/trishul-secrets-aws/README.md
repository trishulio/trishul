# trishul-secrets-aws

AWS Secrets Manager implementation of the `SecretsManager` interface.

> **Use this when**: You need to store and retrieve secrets using AWS Secrets Manager in production.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-secrets-aws</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Auto-configuration registers the bean automatically
@Autowired
private SecretsManager<String, String> secretsManager;

// 2. Use the standard SecretsManager API
String credentials = secretsManager.get("my-app/database");
secretsManager.put("my-app/api-key", apiKeyJson);
```

## When Would I Use This?

### When you need AWS Secrets Manager for credential storage

Add this dependency and the `AwsSecretsManager` is auto-configured:

```java
@Service
public class CredentialService {

    private final SecretsManager<String, String> secretsManager;

    public DatabaseCredentials getDbCredentials(String tenantId) throws IOException {
        String secretId = "myapp/tenants/" + tenantId + "/database";
        String json = secretsManager.get(secretId);
        return objectMapper.readValue(json, DatabaseCredentials.class);
    }
}
```

### When you need to provision tenant secrets during onboarding

Use standard `SecretsManager` operations:

```java
public void provisionTenant(UUID tenantId) throws IOException {
    String secretId = "myapp/tenants/" + tenantId + "/database";
    String credentials = generateCredentialsJson();

    secretsManager.create(secretId, credentials);  // Fails if exists
    // or
    secretsManager.put(secretId, credentials);     // Upsert
}
```

### When you need custom AWS client configuration

Use `SecretsAwsFactory`:

```java
@Configuration
public class CustomSecretsConfig {

    @Bean
    public AWSSecretsManager awsSecretsManagerClient() {
        return SecretsAwsFactory.buildSecretsClient(
            awsCredentialsProvider,
            "us-east-1"
        );
    }
}
```

## Auto-Configuration

The `SecretsAwsAutoConfiguration` automatically registers:

| Bean | Type | Description |
|------|------|-------------|
| `awsSecretsManager` | `AwsSecretsManager` | AWS implementation of `SecretsManager` |

## Core Components

| Class | Purpose |
|-------|---------|
| `AwsSecretsManager` | `SecretsManager<String, String>` implementation using AWS SDK |
| `SecretsAwsFactory` | Factory for creating AWS Secrets Manager clients |
| `SecretsAwsAutoConfiguration` | Spring Boot auto-configuration |

## Error Handling

The implementation handles AWS-specific exceptions:

| AWS Exception | Behavior |
|---------------|----------|
| `ResourceNotFoundException` | Returns `null` for `get()`, `false` for `exists()` |
| `InvalidRequestException` | Wrapped in `IOException` |
| `InvalidParameterException` | Wrapped in `IOException` |

```java
try {
    String secret = secretsManager.get(secretId);
} catch (IOException e) {
    // AWS API error (invalid request, permissions, etc.)
    log.error("Failed to retrieve secret", e);
}
```

## AWS Configuration

Ensure AWS credentials are configured via:
- Environment variables (`AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`)
- IAM role (for EC2/ECS/Lambda)
- AWS credentials file (`~/.aws/credentials`)

Required IAM permissions:
```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "secretsmanager:GetSecretValue",
        "secretsmanager:CreateSecret",
        "secretsmanager:UpdateSecret",
        "secretsmanager:PutSecretValue",
        "secretsmanager:DeleteSecret"
      ],
      "Resource": "arn:aws:secretsmanager:*:*:secret:myapp/*"
    }
  ]
}
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-secrets` | Abstract `SecretsManager` interface |
| `trishul-data` | Uses secrets for database credentials |
| `trishul-tenant-persistence` | Per-tenant credential management |
