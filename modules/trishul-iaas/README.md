# trishul-iaas

> **Use this when**: You need a cloud-agnostic abstraction for managing IaaS resources (S3, Cognito, IAM) with bulk operations.

Defines `IaasClient` for single-entity operations and `IaasRepository`/`BulkIaasClient` for parallelized bulk operations against cloud providers.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-iaas</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Implement IaasClient for single operations against your cloud provider
@Component
public class S3BucketClient implements IaasClient<String, IaasObjectStore, BaseIaasObjectStore, UpdateIaasObjectStore> {
    @Autowired private AmazonS3 s3;
    
    @Override
    public IaasObjectStore get(String bucketName) {
        return s3.doesBucketExistV2(bucketName) 
            ? new IaasObjectStore().setName(bucketName) 
            : null;
    }
    
    @Override
    public <BE extends BaseIaasObjectStore> IaasObjectStore add(BE entity) {
        s3.createBucket(entity.getName());
        return get(entity.getName());
    }
    
    @Override
    public boolean delete(String bucketName) {
        s3.deleteBucket(bucketName);
        return true;
    }
    // ... other methods
}

// 2. Wrap with BulkIaasClient for parallel bulk operations
@Bean
public IaasRepository<String, IaasObjectStore, BaseIaasObjectStore, UpdateIaasObjectStore> 
    bucketRepository(BlockingAsyncExecutor executor, S3BucketClient client) {
    return new BulkIaasClient<>(executor, client);
}

// 3. Use the repository for batch operations
List<IaasObjectStore> buckets = repository.get(Set.of("bucket-a", "bucket-b", "bucket-c"));
```

## When Would I Use This?

### When you need single-entity IaaS operations

Implement `IaasClient<ID, Entity, BaseEntity, UpdateEntity>`:

```java
public interface IaasClient<ID, Entity, BaseEntity, UpdateEntity> {
    Entity get(ID id);                              // Fetch by ID
    <BE extends BaseEntity> Entity add(BE entity);  // Create
    <UE extends UpdateEntity> Entity put(UE entity);// Update
    boolean delete(ID id);                          // Delete
    boolean exists(ID id);                          // Check existence
}
```

Type parameters:
| Param | Purpose | Example |
|-------|---------|---------|
| `ID` | Identifier type | `String` for bucket names, `UUID` for Cognito users |
| `Entity` | Full entity returned | `IaasObjectStore` |
| `BaseEntity` | Minimal entity for creation | `BaseIaasObjectStore` |
| `UpdateEntity` | Entity with updatable fields | `UpdateIaasObjectStore` |

### When you need bulk operations with parallel execution

Use `BulkIaasClient` to wrap your `IaasClient`:

```java
@Configuration
public class IaasConfig {
    @Bean
    public IaasRepository<String, IaasObjectStore, BaseIaasObjectStore, UpdateIaasObjectStore> 
        objectStoreRepo(BlockingAsyncExecutor executor, S3BucketClient client) {
        return new BulkIaasClient<>(executor, client);
    }
}

// Now use bulk operations
@Service
public class BucketService {
    @Autowired private IaasRepository<String, IaasObjectStore, ...> repo;
    
    public List<IaasObjectStore> getBuckets(Set<String> names) {
        return repo.get(names);  // Fetches in parallel
    }
    
    public List<IaasObjectStore> createBuckets(List<BaseIaasObjectStore> buckets) {
        return repo.add(buckets);  // Creates in parallel
    }
    
    public long deleteBuckets(Set<String> names) {
        return repo.delete(names);  // Returns count of successful deletes
    }
}
```

### When you need to map cloud SDK entities to domain entities

Implement `IaasEntityMapper`:

```java
@Mapper(componentModel = "spring")
public interface S3BucketMapper extends IaasEntityMapper<Bucket, IaasObjectStore> {
    @Override
    @Mapping(target = "name", source = "name")
    @Mapping(target = "createdAt", source = "creationDate")
    IaasObjectStore fromIaasEntity(Bucket awsBucket);
}

// Usage in your IaasClient
@Override
public IaasObjectStore get(String bucketName) {
    Bucket awsBucket = s3.listBuckets().stream()
        .filter(b -> b.getName().equals(bucketName))
        .findFirst().orElse(null);
    return mapper.fromIaasEntity(awsBucket);
}
```

### When you need lazy/dynamic repository access

Use `IaasRepositoryProvider`:

```java
@Service
public class DynamicResourceService {
    @Autowired private IaasRepositoryProvider<String, IaasObjectStore, ...> provider;
    
    public void processResources(Set<String> ids) {
        IaasRepository<String, IaasObjectStore, ...> repo = provider.getIaasRepository();
        List<IaasObjectStore> stores = repo.get(ids);
        // Process...
    }
}
```

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        Service Layer                             │
│         ObjectStoreService, TenantIaasService, etc.              │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                      IaasRepository                              │
│                   (Bulk Operations)                              │
│                                                                  │
│  get(Set<ID>)  add(List<BE>)  put(List<UE>)  delete(Set<ID>)    │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                      BulkIaasClient                              │
│              (Parallelizes Single Operations)                    │
│                                                                  │
│              BlockingAsyncExecutor (thread pool)                 │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                        IaasClient                                │
│                   (Single Operations)                            │
│                                                                  │
│       get(ID)    add(BE)    put(UE)    delete(ID)               │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                     Cloud Provider SDK                           │
│               AWS SDK / GCP SDK / Azure SDK                      │
└─────────────────────────────────────────────────────────────────┘
```

## Entity Type Pattern

Follow this pattern for your IaaS entities:

```java
// 1. BaseEntity - minimal fields for creation
public interface BaseIaasObjectStore<T> {
    String getName();
    T setName(String name);
}

// 2. UpdateEntity - fields that can be modified
public interface UpdateIaasObjectStore<T> extends BaseIaasObjectStore<T> {
    // Add update-specific methods if any
}

// 3. Full Entity - all fields including read-only
public class IaasObjectStore extends BaseEntity 
    implements UpdateIaasObjectStore<IaasObjectStore>, 
               CrudEntity<String, IaasObjectStore>,
               Audited<IaasObjectStore> {
    
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    
    @Override
    public String getId() { return getName(); }  // ID is the name
}
```

## Complete IaasClient Example

```java
@Component
public class S3BucketIaasClient 
    implements IaasClient<String, IaasObjectStore, BaseIaasObjectStore, UpdateIaasObjectStore> {
    
    @Autowired private AmazonS3 s3Client;
    @Autowired private S3BucketMapper mapper;
    
    @Override
    public IaasObjectStore get(String bucketName) {
        if (!s3Client.doesBucketExistV2(bucketName)) {
            return null;
        }
        Bucket bucket = s3Client.listBuckets().stream()
            .filter(b -> b.getName().equals(bucketName))
            .findFirst().orElse(null);
        return mapper.fromIaasEntity(bucket);
    }
    
    @Override
    public <BE extends BaseIaasObjectStore> IaasObjectStore add(BE entity) {
        s3Client.createBucket(entity.getName());
        return get(entity.getName());
    }
    
    @Override
    public <UE extends UpdateIaasObjectStore> IaasObjectStore put(UE entity) {
        // S3 buckets have limited update capabilities
        return get(entity.getName());
    }
    
    @Override
    public boolean delete(String bucketName) {
        try {
            s3Client.deleteBucket(bucketName);
            return true;
        } catch (AmazonS3Exception e) {
            return false;
        }
    }
    
    @Override
    public boolean exists(String bucketName) {
        return s3Client.doesBucketExistV2(bucketName);
    }
}
```

## Related Modules

| Module | Use When |
|--------|----------|
| `trishul-iaas-access-aws` | You need AWS IAM policy/role management |
| `trishul-iaas-user-aws` | You need AWS Cognito user management |
| `trishul-iaas-tenant-aws` | You need per-tenant AWS resource provisioning |
| `trishul-object-store` | You need object store entity definitions |
| `trishul-object-store-aws` | You need S3 implementation |
