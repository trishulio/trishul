# trishul-secrets

Cloud-agnostic secrets management interface for secure credential storage and retrieval.

> **Use this when**: You need to store, retrieve, or manage secrets (database credentials, API keys, encryption keys) without coupling to a specific provider.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-secrets</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Inject the secrets manager
@Autowired
private SecretsManager<String, String> secretsManager;

// 2. Store a secret
secretsManager.put("tenant/123/db-credentials", credentialsJson);

// 3. Retrieve a secret
String credentials = secretsManager.get("tenant/123/db-credentials");

// 4. Check existence before operations
if (secretsManager.exists(secretId)) {
    secretsManager.update(secretId, newValue);
}
```

## When Would I Use This?

### When you need to store tenant database credentials

Use `SecretsManager.put()` for upsert or `create()`/`update()` for explicit semantics:

```java
@Service
public class TenantProvisioningService {
    
    private final SecretsManager<String, String> secretsManager;
    
    public void provisionTenant(UUID tenantId) throws IOException {
        String secretId = "trishul/tenants/" + tenantId + "/database";
        DatabaseCredentials creds = new DatabaseCredentials(
            "tenant_" + tenantId.toString().replace("-", ""),
            generateSecurePassword()
        );
        
        // Create new secret (fails if already exists)
        secretsManager.create(secretId, objectMapper.writeValueAsString(creds));
    }
}
```

### When you need to retrieve credentials at runtime

Use `SecretsManager.get()`:

```java
public DatabaseCredentials getCredentials(String tenantId) throws IOException {
    String secretId = "tenant/" + tenantId + "/db-credentials";
    
    if (!secretsManager.exists(secretId)) {
        throw new NotFoundException("Credentials not found");
    }
    
    String secretJson = secretsManager.get(secretId);
    return objectMapper.readValue(secretJson, DatabaseCredentials.class);
}
```

### When you need to rotate credentials

Use `SecretsManager.update()` - requires secret to exist:

```java
public void rotateCredentials(String tenantId, DatabaseCredentials newCreds) throws IOException {
    String secretId = "tenant/" + tenantId + "/db-credentials";
    secretsManager.update(secretId, objectMapper.writeValueAsString(newCreds));
}
```

### When you need to clean up secrets during deprovisioning

Use `SecretsManager.remove()`:

```java
public void deprovisionTenant(UUID tenantId) throws IOException {
    String secretId = "trishul/tenants/" + tenantId + "/database";
    
    if (secretsManager.exists(secretId)) {
        secretsManager.remove(secretId);
    }
}
```

### When you need provider-agnostic secret operations

Implement `SecretsManager<K, V>` for your provider:

```java
@Component
public class AwsSecretsManagerImpl implements SecretsManager<String, String> {
    
    private final AWSSecretsManager client;
    
    @Override
    public String get(String secretId) throws IOException {
        GetSecretValueRequest request = new GetSecretValueRequest()
            .withSecretId(secretId);
        return client.getSecretValue(request).getSecretString();
    }
    
    @Override
    public void put(String secretId, String secret) throws IOException {
        if (exists(secretId)) {
            update(secretId, secret);
        } else {
            create(secretId, secret);
        }
    }
    
    // ... other methods
}
```

## API Reference

### SecretsManager<K, V>

| Method | Description |
|--------|-------------|
| `get(K secretId)` | Retrieve secret value by ID |
| `put(K secretId, V secret)` | Create or update (upsert) |
| `create(K secretId, V secret)` | Create new secret (fails if exists) |
| `update(K secretId, V secret)` | Update existing (fails if not exists) |
| `exists(K secretId)` | Check if secret exists |
| `remove(K secretId)` | Delete secret, returns success status |

**Type Parameters:**
- `K` - Secret identifier type (typically `String`)
- `V` - Secret value type (typically `String` for JSON)

## Secret Naming Conventions

| Pattern | Example | Use Case |
|---------|---------|----------|
| `{app}/tenants/{id}/database` | `trishul/tenants/123/database` | Tenant DB credentials |
| `{app}/tenants/{id}/api-keys` | `trishul/tenants/123/api-keys` | Tenant API keys |
| `{app}/global/encryption-key` | `trishul/global/encryption-key` | Shared encryption keys |
| `{app}/services/{svc}/credentials` | `trishul/services/smtp/credentials` | Service credentials |

## Complete Example

```java
@Service
public class SecretOperations {
    
    private final SecretsManager<String, String> secretsManager;
    private final ObjectMapper objectMapper;
    
    public Optional<DatabaseCredentials> safeGetCredentials(String tenantId) {
        try {
            String secretId = buildSecretId(tenantId);
            if (secretsManager.exists(secretId)) {
                String json = secretsManager.get(secretId);
                return Optional.of(objectMapper.readValue(json, DatabaseCredentials.class));
            }
            return Optional.empty();
        } catch (IOException e) {
            log.error("Failed to retrieve secret for tenant: {}", tenantId, e);
            throw new SecretAccessException("Unable to access secret", e);
        }
    }
    
    public void safeStoreCredentials(String tenantId, DatabaseCredentials creds) {
        try {
            String secretId = buildSecretId(tenantId);
            String json = objectMapper.writeValueAsString(creds);
            secretsManager.put(secretId, json);  // Upsert
        } catch (IOException e) {
            log.error("Failed to store secret for tenant: {}", tenantId, e);
            throw new SecretAccessException("Unable to store secret", e);
        }
    }
    
    private String buildSecretId(String tenantId) {
        return "trishul/tenants/" + tenantId + "/database";
    }
}
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-secrets-aws` | AWS Secrets Manager implementation |
| `trishul-data` | Uses secrets for database credentials |
| `trishul-tenant-persistence` | Per-tenant credential storage |
