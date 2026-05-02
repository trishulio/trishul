# trishul-iaas-user

IaaS user model for identity provider user management with tenant membership.

> **Use this when**: You need to represent users in cloud identity providers (like Cognito) with multi-tenant group membership.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-iaas-user</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Create an IaaS user (email is the ID)
IaasUser user = new IaasUser()
    .setEmail("john@example.com")
    .setUserName("john.doe")
    .setPhoneNumber("+1234567890");

// 2. Create tenant membership
IaasUserTenantMembership membership = new IaasUserTenantMembership()
    .setIaasUser(user)
    .setTenantId(tenantId);

// 3. Map between domain User and IaasUser
IaasUser iaasUser = TenantIaasUserMapper.INSTANCE.fromUser(domainUser);
```

## When Would I Use This?

### When you need to represent users in cloud identity providers

Use `IaasUser` for identity provider operations:

```java
@Service
public class IaasUserService {

    private final CognitoIdentityProviderClient cognito;

    public IaasUser createUser(IaasUser user) {
        AdminCreateUserRequest request = AdminCreateUserRequest.builder()
            .userPoolId(userPoolId)
            .username(user.getEmail())
            .userAttributes(
                AttributeType.builder().name("email").value(user.getEmail()).build(),
                AttributeType.builder().name("phone_number").value(user.getPhoneNumber()).build()
            )
            .build();

        cognito.adminCreateUser(request);
        return user.setCreatedAt(LocalDateTime.now());
    }
}
```

### When you need to manage user-tenant membership

Use `IaasUserTenantMembership` to track which tenants a user belongs to:

```java
// User can belong to multiple tenants
IaasUserTenantMembership membership1 = new IaasUserTenantMembership()
    .setIaasUser(user)
    .setTenantId(tenant1Id);

IaasUserTenantMembership membership2 = new IaasUserTenantMembership()
    .setIaasUser(user)
    .setTenantId(tenant2Id);

// Composite ID for the membership
IaasUserTenantMembershipId id = new IaasUserTenantMembershipId(user.getId(), tenantId);
```

### When you need to map between domain users and IaaS users

Use `TenantIaasUserMapper`:

```java
// Domain User → IaasUser
User domainUser = userRepository.findById(userId);
IaasUser iaasUser = TenantIaasUserMapper.INSTANCE.fromUser(domainUser);

// IaasUser → Domain User (for creating/updating)
User newUser = TenantIaasUserMapper.INSTANCE.toUser(iaasUser);
```

## IaasUser Fields

| Field | Type | Description |
|-------|------|-------------|
| `email` | `String` | Email address (used as ID) |
| `userName` | `String` | Display username |
| `phoneNumber` | `String` | Phone number (not persisted in IDP) |
| `createdAt` | `LocalDateTime` | Creation timestamp |
| `lastUpdated` | `LocalDateTime` | Last update timestamp |

**Note:** Email is the identity - `getId()` returns `getEmail()`.

## Core Components

### Models

| Class | Purpose |
|-------|---------|
| `IaasUser` | User representation in identity provider |
| `BaseIaasUser` | Base interface for user creation |
| `UpdateIaasUser` | Interface for user updates |
| `IaasUserTenantMembership` | User-tenant relationship |
| `IaasUserTenantMembershipId` | Composite ID for membership |
| `BaseIaasUserTenantMembership` | Base interface for membership creation |
| `UpdateIaasUserTenantMembership` | Interface for membership updates |

### Mappers

| Class | Purpose |
|-------|---------|
| `TenantIaasUserMapper` | Maps between domain User and IaasUser |

## Complete Example

```java
@Service
public class UserIdentityService {

    private final IaasUserClient iaasClient;
    private final TenantIaasUserMapper mapper = TenantIaasUserMapper.INSTANCE;

    public User createUserInIdentityProvider(User domainUser, UUID tenantId) {
        // Convert to IaaS model
        IaasUser iaasUser = mapper.fromUser(domainUser);

        // Create in identity provider
        IaasUser created = iaasClient.createUser(iaasUser);

        // Add to tenant group
        IaasUserTenantMembership membership = new IaasUserTenantMembership()
            .setIaasUser(created)
            .setTenantId(tenantId);

        iaasClient.addUserToGroup(membership);

        // Update domain user with IaaS timestamps
        return domainUser
            .setCreatedAt(created.getCreatedAt())
            .setLastUpdated(created.getLastUpdated());
    }

    public void addUserToTenant(String userEmail, UUID tenantId) {
        IaasUser user = new IaasUser(userEmail);

        IaasUserTenantMembership membership = new IaasUserTenantMembership()
            .setIaasUser(user)
            .setTenantId(tenantId);

        iaasClient.addUserToGroup(membership);
    }
}
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-user` | Domain user entity |
| `trishul-iaas-user-aws` | AWS Cognito implementation |
| `trishul-iaas-user-service` | User IaaS service layer |
| `trishul-iaas-tenant-idp` | Tenant identity provider |
