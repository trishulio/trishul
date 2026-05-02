# trishul-base-types

> **Use this when**: You need standard entity contracts for identity, versioning, auditing, or CRUD merge/override semantics.

Zero-dependency foundation module defining the core interfaces that all Trishul entities implement.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-base-types</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Implement CrudEntity for full CRUD support with PATCH/PUT semantics
@Entity
public class Product extends BaseEntity
    implements CrudEntity<UUID, Product>, Audited<Product> {

    @Id private UUID id;
    private String name;
    @Version private Integer version;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

    // Implement interface methods (see examples below)
}

// 2. Use ATTR_* constants in MapStruct mappers
@Mapping(target = Product.ATTR_ID, ignore = true)  // Always use concrete class
```

## When Would I Use This?

### When you need entities with a unique identifier

Use `Identified<ID>` or `IdentityAccessor<ID, T>` to give your entity a typed ID with getter/setter:

```java
public class Order implements IdentityAccessor<UUID, Order> {
    private UUID id;

    @Override public UUID getId() { return id; }
    @Override public Order setId(UUID id) { this.id = id; return this; }
}
```

### When you need optimistic locking to prevent concurrent update conflicts

Use `Versioned` to add a version field that Hibernate checks before updates:

```java
public class Order implements Versioned {
    @Version private Integer version;

    @Override public Integer getVersion() { return version; }
}
```

### When you need automatic creation/modification timestamps

Use `Audited<T>` to track when entities are created and last modified:

```java
public class Order implements Audited<Order> {
    @CreationTimestamp private LocalDateTime createdAt;
    @UpdateTimestamp private LocalDateTime lastUpdated;

    @Override public LocalDateTime getCreatedAt() { return createdAt; }
    @Override public Order setCreatedAt(LocalDateTime ts) { this.createdAt = ts; return this; }
    @Override public LocalDateTime getLastUpdated() { return lastUpdated; }
    @Override public Order setLastUpdated(LocalDateTime ts) { this.lastUpdated = ts; return this; }
}
```

### When you need PATCH vs PUT update semantics

Use `CrudEntity<ID, T>` to get `outerJoin()` (PATCH) and `override()` (PUT) methods:

```java
Product existing = repository.findById(id);
Product update = new Product().setName("New Name");  // price is null

existing.outerJoin(update);  // PATCH: only name changes, price unchanged
existing.override(update);   // PUT: name changes, price becomes null
```

| Method | Behavior | HTTP Equivalent |
|--------|----------|-----------------|
| `outerJoin(other)` | Copy only non-null fields from source | PATCH |
| `override(other)` | Copy all fields from source (including nulls) | PUT |

### When you need type-safe attribute names for MapStruct

Use the `ATTR_*` constants for compile-time validated `@Mapping` annotations:

```java
@Mapper
public interface OrderMapper {
    // CORRECT: Reference from concrete class
    @Mapping(target = Order.ATTR_ID, ignore = true)
    @Mapping(target = Order.ATTR_CREATED_AT, ignore = true)
    Order fromDto(OrderDto dto);

    // WRONG: Never reference from interface (breaks MapStruct)
    // @Mapping(target = Identified.ATTR_ID, ignore = true)
}
```

### When you need lambdas that can throw checked exceptions

Use the `Checked*` functional interfaces in code that may throw:

```java
// Standard Supplier cannot throw
// CheckedSupplier<T> can throw Exception

public <T> T retry(CheckedSupplier<T> operation, int maxAttempts) throws Exception {
    for (int i = 0; i < maxAttempts; i++) {
        try {
            return operation.get();  // Can throw IOException, etc.
        } catch (Exception e) {
            if (i == maxAttempts - 1) throw e;
        }
    }
    throw new RuntimeException("Unreachable");
}

// Usage
String data = retry(() -> httpClient.fetch(url), 3);
```

| Interface | Standard Equivalent | Throws |
|-----------|---------------------|--------|
| `CheckedSupplier<T>` | `Supplier<T>` | `Exception` |
| `CheckedConsumer<T>` | `Consumer<T>` | `Exception` |
| `CheckedFunction<T,R>` | `Function<T,R>` | `Exception` |
| `CheckedRunnable` | `Runnable` | `Exception` |
| `TriFunction<T,U,V,R>` | - | No (3-arg function) |

### When you need to run tasks and collect successes/failures

Use `TaskSet<T>` and `TaskResult<T>` for parallel operations with error aggregation:

```java
TaskSet<String> tasks = new SequentialTaskSet<>(List.of(
    () -> fetchFromService1(),
    () -> fetchFromService2(),
    () -> fetchFromService3()
));

TaskResult<String> result = tasks.run();
if (result.hasFailures()) {
    result.getFailures().forEach(e -> log.error("Task failed", e));
}
List<String> data = result.getSuccesses();
```

## Interface Hierarchy

```
Identified<ID>
    |
    +-- IdentityAccessor<ID, T>
            |
            +-- UpdatableEntity<ID, T> (+ Versioned)
                    |
                    +-- CrudEntity<ID, T>

Audited<T> (standalone, combine with CrudEntity)
Versioned  (standalone, included in UpdatableEntity)
```

## Complete Entity Example

```java
@Entity
@Table(name = "PRODUCT")
public class Product extends BaseEntity
    implements CrudEntity<UUID, Product>, Audited<Product> {

    @Id
    private UUID id;
    private String name;
    private BigDecimal price;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    @Version
    private Integer version;

    // --- Identified ---
    @Override public UUID getId() { return id; }
    @Override public Product setId(UUID id) { this.id = id; return this; }

    // --- Versioned ---
    @Override public Integer getVersion() { return version; }

    // --- Audited ---
    @Override public LocalDateTime getCreatedAt() { return createdAt; }
    @Override public Product setCreatedAt(LocalDateTime ts) { this.createdAt = ts; return this; }
    @Override public LocalDateTime getLastUpdated() { return lastUpdated; }
    @Override public Product setLastUpdated(LocalDateTime ts) { this.lastUpdated = ts; return this; }

    // --- CrudEntity (inherit from BaseModel in trishul-model) ---
    @Override public void outerJoin(Object other) { /* merge non-null */ }
    @Override public void outerJoin(Object other, Set<String> include) { /* merge specified */ }
    @Override public void override(Object other) { /* replace all */ }
    @Override public void override(Object other, Set<String> include) { /* replace specified */ }

    // Fluent setters
    public Product setName(String name) { this.name = name; return this; }
    public Product setPrice(BigDecimal price) { this.price = price; return this; }
}
```

## Available Constants

| Constant | Value | Defined In |
|----------|-------|------------|
| `ATTR_ID` | `"id"` | `Identified` |
| `ATTR_VERSION` | `"version"` | `Versioned` |
| `ATTR_CREATED_AT` | `"createdAt"` | `Audited` |
| `ATTR_LAST_UPDATED` | `"lastUpdated"` | `Audited` |

## Design Principles

1. **Fluent Setters** - All setters return `this` for method chaining
2. **Generic Self-Types** - `T extends Interface<T>` pattern for type-safe fluent APIs
3. **Static Constants** - Attribute names as constants for compile-time MapStruct safety
4. **Zero Dependencies** - No external dependencies for maximum reusability

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-model` | Provides `BaseModel`/`BaseEntity` implementations of these interfaces |
| `trishul-crud` | CRUD service patterns using `CrudEntity` |
