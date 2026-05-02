# trishul-user-service

User management service with REST API for users, roles, salutations, and account operations.

> **Use this when**: You need full user CRUD operations with role management, IaaS identity provider integration, and file storage for user assets.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-user-service</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Auto-configuration registers all services
@Autowired
private UserService userService;

@Autowired
private UserRoleService roleService;

// 2. Create a user
User user = userService.add(List.of(
    new BaseUser<>()
        .setUserName("john.doe")
        .setEmail("john@example.com")
        .setDisplayName("John Doe")
)).get(0);

// 3. Assign roles
userService.assignRole(user.getId(), roleId);
```

## When Would I Use This?

### When you need user CRUD via REST API

The `UserController` provides full user management:

```
GET    /api/v1/users           - List users (paginated)
GET    /api/v1/users/{id}      - Get user by ID
POST   /api/v1/users           - Create user(s)
PUT    /api/v1/users           - Update user(s)
PATCH  /api/v1/users           - Patch user(s)
DELETE /api/v1/users?ids=...   - Delete user(s)
```

### When you need role management

Use `UserRoleService` and `UserRoleController`:

```java
// Create a role
UserRole adminRole = roleService.add(List.of(
    new BaseUserRole<>().setName("ADMIN")
)).get(0);

// List all roles
Page<UserRole> roles = roleService.getAll(null, sort, true, 0, 10);
```

```
GET    /api/v1/users/roles     - List roles
POST   /api/v1/users/roles     - Create role(s)
PUT    /api/v1/users/roles     - Update role(s)
DELETE /api/v1/users/roles     - Delete role(s)
```

### When you need user salutation management

Use `UserSalutationService` for title prefixes (Mr., Mrs., Dr., etc.):

```java
UserSalutation salutation = salutationService.add(List.of(
    new BaseUserSalutation<>().setTitle("Dr.")
)).get(0);
```

```
GET    /api/v1/users/salutations  - List salutations
POST   /api/v1/users/salutations  - Create salutation(s)
```

### When you need account-level operations

Use `AccountService` and `AccountController` for current user operations:

```java
// Get current authenticated user's account
Account account = accountService.getCurrentAccount();

// Update current user's profile
accountService.updateProfile(profileUpdateDto);
```

### When users need file storage integration

The module integrates with `trishul-object-store-file-service` for user assets:

```java
// User profile images, documents, etc.
fileService.uploadUserFile(userId, file);
```

## REST API Endpoints

### Users

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/v1/users` | GET | List users with pagination |
| `/api/v1/users/{id}` | GET | Get user by ID |
| `/api/v1/users` | POST | Create users |
| `/api/v1/users` | PUT | Update users (full) |
| `/api/v1/users` | PATCH | Patch users (partial) |
| `/api/v1/users` | DELETE | Delete users by IDs |

### Roles

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/v1/users/roles` | GET | List roles |
| `/api/v1/users/roles` | POST | Create roles |
| `/api/v1/users/roles` | PUT | Update roles |
| `/api/v1/users/roles` | DELETE | Delete roles |

### Salutations

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/v1/users/salutations` | GET | List salutations |
| `/api/v1/users/salutations` | POST | Create salutations |

### Account

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/v1/account` | GET | Get current user account |
| `/api/v1/account` | PUT | Update current user account |

## Core Components

### Services

| Class | Purpose |
|-------|---------|
| `UserService` | User CRUD operations |
| `UserRoleService` | Role management |
| `UserSalutationService` | Salutation management |
| `AccountService` | Current user account operations |

### Controllers

| Class | Purpose |
|-------|---------|
| `UserController` | User REST endpoints |
| `UserRoleController` | Role REST endpoints |
| `UserSalutationController` | Salutation REST endpoints |
| `AccountController` | Account REST endpoints |
| `UserDtoDecorator` | DTO enrichment |

### Repositories

| Class | Purpose |
|-------|---------|
| `UserRepository` | User JPA repository |
| `UserRoleRepository` | Role JPA repository |
| `UserRoleBindingRepository` | User-role mapping repository |
| `UserSalutationRepository` | Salutation JPA repository |
| `UserStatusRepository` | User status JPA repository |

## Auto-Configuration

`UserServiceAutoConfiguration` registers all services, controllers, and repositories.

```java
@SpringBootApplication
@Import(UserServiceAutoConfiguration.class)
public class MyApplication {
    // All user management beans are registered
}
```

## Complete Example

```java
@RestController
@RequestMapping("/api/v1/onboarding")
public class OnboardingController {

    private final UserService userService;
    private final UserRoleService roleService;

    @PostMapping("/register")
    public UserDto register(@RequestBody RegistrationRequest request) {
        // Create user
        User user = userService.add(List.of(
            new BaseUser<>()
                .setUserName(request.getUsername())
                .setEmail(request.getEmail())
                .setDisplayName(request.getDisplayName())
        )).get(0);

        // Assign default role
        UserRole defaultRole = roleService.getByName("USER");
        userService.assignRole(user.getId(), defaultRole.getId());

        return UserMapper.INSTANCE.toDto(user);
    }
}
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-user` | User entity model |
| `trishul-iaas-user-service` | IaaS identity provider integration |
| `trishul-object-store-file-service` | User file storage |
| `trishul-crud` | CRUD service abstractions |
| `trishul-auth` | Authentication context |
