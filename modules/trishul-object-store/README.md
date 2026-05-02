# trishul-object-store

> **Use this when**: You need entities and interfaces for managing cloud object stores (S3 buckets) with CORS and access configurations.

Provides `IaasObjectStore` entity for bucket management, plus CORS and public access block configuration entities.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-object-store</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Create an object store entity
IaasObjectStore bucket = new IaasObjectStore()
    .setName("my-tenant-data-bucket");

// 2. Use with IaasRepository for CRUD
List<IaasObjectStore> created = objectStoreRepository.add(List.of(bucket));

// 3. Configure CORS for web access
IaasObjectStoreCorsConfiguration cors = new IaasObjectStoreCorsConfiguration(
    "my-bucket",
    new BucketCrossOriginConfiguration().withRules(
        new CORSRule()
            .withAllowedOrigins("https://app.example.com")
            .withAllowedMethods(CORSRule.AllowedMethods.GET, CORSRule.AllowedMethods.PUT)
    )
);

// 4. Block public access
IaasObjectStoreAccessConfig accessConfig = new IaasObjectStoreAccessConfig(
    "my-bucket",
    new PublicAccessBlockConfiguration()
        .withBlockPublicAcls(true)
        .withBlockPublicPolicy(true)
);
```

## When Would I Use This?

### When you need to represent a cloud bucket/object store

Use `IaasObjectStore`:

```java
IaasObjectStore bucket = new IaasObjectStore()
    .setName("tenant-abc-data")        // Name is also the ID
    .setCreatedAt(LocalDateTime.now());

String bucketName = bucket.getId();    // Returns getName()
```

Key characteristics:
- ID is the bucket name (`getId()` returns `getName()`)
- Implements `CrudEntity` for standard CRUD operations
- Implements `Audited` for timestamp tracking

### When you need entities that reference an object store

Implement `IaasObjectStoreAccessor`:

```java
public class TenantResources implements IaasObjectStoreAccessor<TenantResources> {
    private IaasObjectStore dataBucket;

    @Override
    public IaasObjectStore getObjectStore() { return dataBucket; }

    @Override
    public TenantResources setObjectStore(IaasObjectStore store) {
        this.dataBucket = store;
        return this;
    }
}
```

### When you need to configure CORS for browser access

Use `IaasObjectStoreCorsConfiguration`:

```java
// Allow your web app to access the bucket
CORSRule rule = new CORSRule()
    .withAllowedOrigins("https://app.example.com", "https://admin.example.com")
    .withAllowedMethods(
        CORSRule.AllowedMethods.GET,
        CORSRule.AllowedMethods.PUT,
        CORSRule.AllowedMethods.POST,
        CORSRule.AllowedMethods.DELETE
    )
    .withAllowedHeaders("*")
    .withMaxAgeSeconds(3600);

BucketCrossOriginConfiguration corsConfig = new BucketCrossOriginConfiguration()
    .withRules(rule);

IaasObjectStoreCorsConfiguration config = new IaasObjectStoreCorsConfiguration(
    "my-bucket",
    corsConfig
);

// Apply via repository
corsRepository.add(List.of(config));
```

### When you need to block public access to buckets

Use `IaasObjectStoreAccessConfig`:

```java
// Secure bucket with all public access blocked
PublicAccessBlockConfiguration blockConfig = new PublicAccessBlockConfiguration()
    .withBlockPublicAcls(true)       // Block public ACLs
    .withIgnorePublicAcls(true)      // Ignore existing public ACLs
    .withBlockPublicPolicy(true)     // Block public bucket policies
    .withRestrictPublicBuckets(true);// Restrict public bucket access

IaasObjectStoreAccessConfig accessConfig = new IaasObjectStoreAccessConfig(
    "my-bucket",
    blockConfig
);

// Apply via repository
accessConfigRepository.add(List.of(accessConfig));
```

## Multi-Tenant Object Storage Pattern

Typical pattern for per-tenant buckets:

```java
@Service
public class TenantObjectStoreService {
    @Autowired private IaasRepository<String, IaasObjectStore, ...> storeRepo;
    @Autowired private IaasRepository<String, IaasObjectStoreAccessConfig, ...> accessRepo;

    public IaasObjectStore provisionTenantStorage(UUID tenantId) {
        String bucketName = "tenant-" + tenantId + "-data";

        // 1. Create bucket
        IaasObjectStore store = new IaasObjectStore().setName(bucketName);
        store = storeRepo.add(List.of(store)).get(0);

        // 2. Block all public access
        PublicAccessBlockConfiguration blockConfig = new PublicAccessBlockConfiguration()
            .withBlockPublicAcls(true)
            .withIgnorePublicAcls(true)
            .withBlockPublicPolicy(true)
            .withRestrictPublicBuckets(true);

        accessRepo.add(List.of(new IaasObjectStoreAccessConfig(bucketName, blockConfig)));

        return store;
    }

    public void deprovisionTenantStorage(UUID tenantId) {
        String bucketName = "tenant-" + tenantId + "-data";
        storeRepo.delete(Set.of(bucketName));
    }
}
```

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                      Object Store Service                        │
│        TenantObjectStoreService, FileUploadService, etc.         │
└─────────────────────────────────────────────────────────────────┘
                              │
              ┌───────────────┼───────────────┐
              ▼               ▼               ▼
┌─────────────────┐ ┌─────────────────┐ ┌─────────────────┐
│ IaasObjectStore │ │ CorsConfig      │ │ AccessConfig    │
│   Repository    │ │   Repository    │ │   Repository    │
└─────────────────┘ └─────────────────┘ └─────────────────┘
              │               │               │
              └───────────────┼───────────────┘
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                   trishul-object-store-aws                       │
│              S3 Client, Bucket, CORS, Access Block               │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                        AWS S3 Service                            │
└─────────────────────────────────────────────────────────────────┘
```

## Entity Reference

| Entity | ID Type | Purpose |
|--------|---------|---------|
| `IaasObjectStore` | `String` (bucket name) | Represents a bucket |
| `IaasObjectStoreCorsConfiguration` | `String` (bucket name) | CORS rules for a bucket |
| `IaasObjectStoreAccessConfig` | `String` (bucket name) | Public access block settings |

## Interface Hierarchy

```
BaseIaasObjectStore<T>
    │   getName(), setName()
    │
    └── UpdateIaasObjectStore<T>
            │
            └── IaasObjectStore
                    implements CrudEntity<String, IaasObjectStore>,
                               Audited<IaasObjectStore>
```

## Related Modules

| Module | Use When |
|--------|----------|
| `trishul-iaas` | You need the `IaasClient`/`IaasRepository` interfaces |
| `trishul-object-store-aws` | You need AWS S3 implementation |
| `trishul-object-store-file` | You need file content/metadata models |
| `trishul-object-store-file-service` | You need file upload/download services |
| `trishul-iaas-tenant-object-store` | You need per-tenant bucket management |
