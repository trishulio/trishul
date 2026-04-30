# trishul-iaas-tenant

IaaS resource model for tenant cloud infrastructure (authentication, identity provider, file storage).

> **Use this when**: You need to represent and manage tenant-specific cloud resources like Cognito user pools, IAM policies, and S3 buckets.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-iaas-tenant</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Create tenant IaaS resources container
TenantIaasResources resources = new TenantIaasResources()
    .setAuthResources(authResources)      // IAM policies, roles
    .setIdpResources(idpResources)        // Cognito user pool, groups
    .setVfsResources(vfsResources);       // S3 buckets

// 2. Use the builder for structured construction
TenantIaasResources resources = TenantIaasResourceBuilder.builder()
    .authResources(authResources)
    .idpResources(idpResources)
    .vfsResources(vfsResources)
    .build();
```

## When Would I Use This?

### When you need to aggregate all tenant cloud resources

Use `TenantIaasResources` as a container for all IaaS components:

```java
@Service
public class TenantIaasProvisioningService {
    
    private final TenantIaasAuthService authService;
    private final TenantIaasIdpService idpService;
    private final TenantIaasVfsService vfsService;
    
    public TenantIaasResources provisionTenant(Tenant tenant) {
        TenantIaasAuthResources auth = authService.provision(tenant);
        TenantIaasIdpResources idp = idpService.provision(tenant);
        TenantIaasVfsResources vfs = vfsService.provision(tenant);
        
        return new TenantIaasResources()
            .setAuthResources(auth)
            .setIdpResources(idp)
            .setVfsResources(vfs);
    }
}
```

### When you need to pass tenant resources between services

The container provides a single object to pass around:

```java
public void onTenantProvisioned(TenantIaasResources resources) {
    // Access individual resource types
    TenantIaasAuthResources auth = resources.getAuthResources();
    TenantIaasIdpResources idp = resources.getIdpResources();
    TenantIaasVfsResources vfs = resources.getVfsResources();
    
    // Use resources for further configuration
    configureWebhooks(idp.getUserPoolId());
}
```

### When you need type-safe resource building

Use `TenantIaasResourceBuilder`:

```java
TenantIaasResources resources = TenantIaasResourceBuilder.builder()
    .authResources(new TenantIaasAuthResources()
        .setIamPolicy(policy)
        .setIamRole(role))
    .idpResources(new TenantIaasIdpResources()
        .setUserPool(userPool)
        .setUserPoolGroup(group))
    .vfsResources(new TenantIaasVfsResources()
        .setObjectStore(bucket))
    .build();
```

## Resource Types

| Resource Type | Description | AWS Implementation |
|---------------|-------------|-------------------|
| `TenantIaasAuthResources` | Authentication/authorization | IAM policies, roles |
| `TenantIaasIdpResources` | Identity provider | Cognito user pool, groups |
| `TenantIaasVfsResources` | Virtual file storage | S3 buckets |

## Core Components

| Class | Purpose |
|-------|---------|
| `TenantIaasResources` | Container for all tenant IaaS resources |
| `TenantIaasResourceBuilder` | Builder for constructing resources |

## Complete Example

```java
@Service
public class TenantLifecycleService {
    
    private final TenantIaasService iaasService;
    
    public void provisionTenant(Tenant tenant) {
        // Provision all IaaS resources
        TenantIaasResources resources = iaasService.put(tenant);
        
        // Log what was created
        log.info("Provisioned tenant {} with resources: " +
            "Auth={}, IDP={}, VFS={}",
            tenant.getId(),
            resources.getAuthResources(),
            resources.getIdpResources(),
            resources.getVfsResources());
    }
    
    public void deprovisionTenant(UUID tenantId) {
        // Clean up all IaaS resources
        iaasService.delete(Set.of(tenantId));
    }
}
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-iaas-tenant-aws` | AWS implementation |
| `trishul-iaas-tenant-service` | Service layer for provisioning |
| `trishul-iaas-auth` | Authentication resources |
| `trishul-iaas-tenant-idp` | Identity provider resources |
| `trishul-iaas-tenant-object-store` | Object storage resources |
