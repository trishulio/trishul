# trishul-user

> **Use this when**: You need user management entities with roles, statuses, and salutations for your application.

Provides the `User` JPA entity with role bindings, status tracking, and associated DTOs/mappers.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-user</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Create a user with roles
User user = new User()
    .setUserName("jdoe")
    .setEmail("john.doe@example.com")
    .setDisplayName("John Doe")
    .setFirstName("John")
    .setLastName("Doe")
    .setStatus(activeStatus)
    .setSalutation(mrSalutation)
    .setRoles(List.of(adminRole, userRole));

// 2. Make entities track who created/modified them
public class Order implements UserAccessor<Order>, AssignedToAccessor<Order> {
    private User createdBy;
    private User assignedTo;

    @Override public User getUser() { return createdBy; }
    @Override public User getAssignedTo() { return assignedTo; }
}

// 3. Use the mapper
UserDto dto = userMapper.toDto(user);
```

## When Would I Use This?

### When you need a user entity with common fields

Use the `User` entity:

```java
User user = new User()
    .setUserName("jdoe")               // Unique username
    .setEmail("john@example.com")      // Unique, non-updatable
    .setIaasUsername("cognito-abc")    // IdP username, non-updatable
    .setDisplayName("John Doe")
    .setFirstName("John")
    .setLastName("Doe")
    .setPhoneNumber("+1-555-123-4567")
    .setImageSrc(URI.create("https://cdn.example.com/avatars/jdoe.jpg"))
    .setStatus(activeStatus)
    .setSalutation(mrSalutation)
    .setRoles(List.of(adminRole));
```

| Field | Type | Notes |
|-------|------|-------|
| `id` | `Long` | Auto-generated |
| `userName` | `String` | Unique |
| `email` | `String` | Unique, **non-updatable** |
| `iaasUsername` | `String` | IdP username, **non-updatable** |
| `displayName` | `String` | Full display name |
| `firstName`, `lastName` | `String` | Name parts |
| `phoneNumber` | `String` | Contact number |
| `imageSrc` | `URI` | Profile image URL |
| `status` | `UserStatus` | Account status |
| `salutation` | `UserSalutation` | Title (Mr., Ms., Dr.) |
| `roles` | `List<UserRole>` | Assigned roles |

### When you need role-based access control (RBAC)

Use `UserRole` and role bindings:

```java
// Create roles
UserRole adminRole = new UserRole().setName("ADMIN");
UserRole editorRole = new UserRole().setName("EDITOR");
UserRole viewerRole = new UserRole().setName("VIEWER");

// Assign roles to user
user.setRoles(List.of(adminRole, editorRole));

// Check roles
List<UserRole> roles = user.getRoles();
boolean isAdmin = roles.stream()
    .anyMatch(r -> "ADMIN".equals(r.getName()));
```

### When you need user account statuses

Use `UserStatus`:

```java
// Common statuses
UserStatus active = new UserStatus().setName("ACTIVE");
UserStatus suspended = new UserStatus().setName("SUSPENDED");
UserStatus pending = new UserStatus().setName("PENDING_VERIFICATION");

// Assign to user
user.setStatus(active);

// Check status
if ("SUSPENDED".equals(user.getStatus().getName())) {
    throw new AccountSuspendedException();
}
```

### When you need salutations/titles

Use `UserSalutation`:

```java
UserSalutation mr = new UserSalutation().setTitle("Mr.");
UserSalutation ms = new UserSalutation().setTitle("Ms.");
UserSalutation dr = new UserSalutation().setTitle("Dr.");

user.setSalutation(dr);
String greeting = user.getSalutation().getTitle() + " " + user.getLastName();
// "Dr. Doe"
```

### When entities need to track created-by or assigned-to users

Implement `UserAccessor` and/or `AssignedToAccessor`:

```java
@Entity
public class Task implements UserAccessor<Task>, AssignedToAccessor<Task> {

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    private User assignedTo;

    @Override
    public User getUser() { return createdBy; }

    @Override
    public Task setUser(User user) {
        this.createdBy = user;
        return this;
    }

    @Override
    public User getAssignedTo() { return assignedTo; }

    @Override
    public Task setAssignedTo(User user) {
        this.assignedTo = user;
        return this;
    }
}
```

## DTOs

| DTO | Purpose | Key Fields |
|-----|---------|------------|
| `UserDto` | Response | All fields |
| `AddUserDto` | Create | All except `id`, `iaasUsername` |
| `UpdateUserDto` | Update | All except `email`, `iaasUsername` |
| `UserRoleDto` | Role response | `id`, `name` |
| `AddUserRoleDto` | Create role | `name` |
| `UpdateUserRoleDto` | Update role | `id`, `name` |
| `UserStatusDto` | Status response | `id`, `name` |
| `UserSalutationDto` | Salutation response | `id`, `title` |

## Mappers

```java
@Autowired private UserMapper userMapper;
@Autowired private UserRoleMapper roleMapper;
@Autowired private UserStatusMapper statusMapper;
@Autowired private UserSalutationMapper salutationMapper;

// Entity to DTO
UserDto dto = userMapper.toDto(user);

// Create DTO to Entity
User user = userMapper.fromAddDto(addUserDto);

// Update DTO to Entity
User updated = userMapper.fromUpdateDto(updateUserDto);
```

## Accessor Interfaces

| Interface | Purpose |
|-----------|---------|
| `UserAccessor<T>` | Entity references a user (creator, owner) |
| `AssignedToAccessor<T>` | Entity has an assigned user |
| `UserRoleAccessor<T>` | Entity references a role |
| `UserStatusAccessor<T>` | Entity references a status |
| `UserSalutationAccessor<T>` | Entity references a salutation |

## Database Schema

```sql
CREATE TABLE user_status (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    version INTEGER,
    created_at TIMESTAMP,
    last_updated TIMESTAMP
);

CREATE TABLE user_salutation (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) UNIQUE NOT NULL,
    version INTEGER,
    created_at TIMESTAMP,
    last_updated TIMESTAMP
);

CREATE TABLE user_role (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    version INTEGER,
    created_at TIMESTAMP,
    last_updated TIMESTAMP
);

CREATE TABLE _user (  -- "user" is reserved in some DBs
    id BIGSERIAL PRIMARY KEY,
    user_name VARCHAR(255),
    iaas_username VARCHAR(255),
    display_name VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(255),
    image_src VARCHAR(2048),
    user_status_id BIGINT REFERENCES user_status(id),
    user_salutation_id BIGINT REFERENCES user_salutation(id),
    version INTEGER,
    created_at TIMESTAMP,
    last_updated TIMESTAMP
);

CREATE TABLE user_role_binding (
    user_id BIGINT REFERENCES _user(id),
    role_id BIGINT REFERENCES user_role(id),
    PRIMARY KEY (user_id, role_id)
);
```

## Related Modules

| Module | Use When |
|--------|----------|
| `trishul-user-service` | You need user CRUD services |
| `trishul-iaas-user` | You need user IaaS integration |
| `trishul-iaas-user-aws` | You need AWS Cognito user sync |
| `trishul-auth` | You need authentication context |
| `trishul-object-store-file` | You need user profile images |
