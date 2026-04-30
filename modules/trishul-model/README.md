# trishul-model

> **Use this when**: You need base classes for entities/DTOs with built-in merge, clone, JSON serialization, and MapStruct integration.

Provides `BaseModel`, `BaseEntity`, `BaseDto`, reflection utilities, validation annotations, and standard exceptions.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-model</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Extend BaseEntity for JPA entities (inherits outerJoin, override, deepClone)
@Entity
public class Product extends BaseEntity implements CrudEntity<UUID, Product> {
    @Id private UUID id;
    private String name;
    // outerJoin() and override() are inherited from BaseModel
}

// 2. Extend BaseDto for DTOs
public class ProductDto extends BaseDto {
    private UUID id;
    private String name;
}

// 3. Create a mapper extending BaseMapper
@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<Product, ProductDto, AddProductDto, UpdateProductDto> {
    @Mapping(target = Product.ATTR_ID, ignore = true)  // Use concrete class, not interface
    Product fromAddDto(AddProductDto dto);
}

// 4. Use @NullOrNotBlank for optional fields that reject blank strings
public class UpdateProductDto extends BaseDto {
    @NullOrNotBlank  // null OK, "" or "   " not OK
    private String name;
}
```

## When Would I Use This?

### When you need PATCH semantics (merge non-null fields only)

Use `outerJoin()` inherited from `BaseModel`:

```java
Product existing = repository.findById(id);  // name="Old", price=100
Product patch = new Product().setName("New").setPrice(null);

existing.outerJoin(patch);
// Result: name="New", price=100 (unchanged because patch.price was null)
```

### When you need PUT semantics (replace all fields)

Use `override()` inherited from `BaseModel`:

```java
Product existing = repository.findById(id);  // name="Old", price=100
Product replacement = new Product().setName("New").setPrice(null);

existing.override(replacement);
// Result: name="New", price=null (all fields replaced)
```

### When you need to deep clone an entity

Use `deepClone()` (clones via JSON serialization):

```java
Product original = new Product().setName("Widget").setPrice(new BigDecimal("99.99"));
Product copy = original.deepClone();  // Independent copy
copy.setName("Widget Copy");  // Does not affect original
```

### When you need a standard CRUD mapper pattern

Extend `BaseMapper<Entity, Dto, AddDto, UpdateDto>`:

```java
@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<Product, ProductDto, AddProductDto, UpdateProductDto> {
    
    @Override
    ProductDto toDto(Product entity);
    
    @Override
    @Mapping(target = Product.ATTR_ID, ignore = true)
    @Mapping(target = Product.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Product.ATTR_LAST_UPDATED, ignore = true)
    Product fromAddDto(AddProductDto dto);
    
    @Override
    @Mapping(target = Product.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Product.ATTR_LAST_UPDATED, ignore = true)
    Product fromUpdateDto(UpdateProductDto dto);
}
```

### When you need to handle circular references in mappers

Use `CycleAvoidingMappingContext`:

```java
@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order, @Context CycleAvoidingMappingContext ctx);
}

// Usage
OrderDto dto = orderMapper.toDto(order, new CycleAvoidingMappingContext());
```

### When you need validation that allows null but rejects blanks

Use `@NullOrNotBlank` (unlike `@NotBlank` which rejects null):

```java
public class UpdateProductDto extends BaseDto {
    @NullOrNotBlank  // null = "don't update", blank = "invalid"
    private String name;
    
    @NotBlank        // null = invalid, blank = invalid
    private String code;
}
```

### When you need to throw standard not-found exceptions

Use `EntityNotFoundException`:

```java
// Basic usage
throw new EntityNotFoundException("Product", productId);
// Message: "Product not found with id: {productId}"

// With custom field
throw new EntityNotFoundException("Product", "sku", "ABC123");
// Message: "Product not found with sku: ABC123"

// Assertion pattern
Product product = repository.findById(id).orElse(null);
EntityNotFoundException.assertion(product != null, "Product", "id", id);
```

### When you need JSON serialization utilities

Use the `JsonMapper` singleton:

```java
JsonMapper mapper = JsonMapper.INSTANCE;

String json = mapper.writeString(product);
Product restored = mapper.readString(json, Product.class);
```

### When you need reflection-based object construction

Use `ReflectionManipulator`:

```java
ReflectionManipulator util = ReflectionManipulator.INSTANCE;

// Get property names (excluding specified)
Set<String> props = util.getPropertyNames(Product.class, Set.of("id", "version"));

// Construct from map
Map<String, Object> values = Map.of("name", "Widget", "price", 99);
Product product = util.construct(Product.class, values);
```

## Complete Entity/DTO/Mapper Pattern

### Entity

```java
@Entity
@Table(name = "PRODUCT")
public class Product extends BaseEntity 
    implements CrudEntity<UUID, Product>, Audited<Product> {
    
    @Id private UUID id;
    @Column(nullable = false) private String name;
    @Column(precision = 10, scale = 2) private BigDecimal price;
    @CreationTimestamp @Column(updatable = false) private LocalDateTime createdAt;
    @UpdateTimestamp private LocalDateTime lastUpdated;
    @Version private Integer version;
    
    // Fluent getters/setters...
}
```

### DTOs

```java
// Response DTO
public class ProductDto extends BaseDto {
    private UUID id;
    private String name;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
}

// Create DTO
public class AddProductDto extends BaseDto {
    @NotBlank private String name;
    @NotNull @Positive private BigDecimal price;
}

// Update DTO
public class UpdateProductDto extends BaseDto {
    @NotNull private UUID id;
    @NullOrNotBlank private String name;  // Optional update
    @Positive private BigDecimal price;   // Optional update
    private Integer version;              // For optimistic locking
}
```

### Mapper

```java
@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<Product, ProductDto, AddProductDto, UpdateProductDto> {
    
    @Override
    ProductDto toDto(Product e);
    
    List<ProductDto> toDtos(List<Product> entities);
    
    @Override
    @Mapping(target = Product.ATTR_ID, ignore = true)
    @Mapping(target = Product.ATTR_VERSION, ignore = true)
    @Mapping(target = Product.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Product.ATTR_LAST_UPDATED, ignore = true)
    Product fromAddDto(AddProductDto dto);
    
    @Override
    @Mapping(target = Product.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = Product.ATTR_LAST_UPDATED, ignore = true)
    Product fromUpdateDto(UpdateProductDto dto);
}
```

## Key Classes Reference

| Class | Use When |
|-------|----------|
| `BaseModel` | Base for any POJO needing merge/clone/JSON |
| `BaseEntity` | Base for JPA entities (adds `Serializable`) |
| `BaseDto` | Base for DTOs (semantic marker) |
| `BaseMapper<E,D,A,U>` | Standard CRUD mapper interface |
| `CycleAvoidingMappingContext` | Mapping objects with circular references |
| `LocalDateTimeMapper` | Auto-convert `LocalDateTime` <-> `String` |
| `@NullOrNotBlank` | Validate optional strings |
| `EntityNotFoundException` | Standard 404 exception |
| `ValidationException` | Business rule validation failures |
| `ReflectionManipulator` | Reflection utilities singleton |
| `JsonMapper` | JSON serialization singleton |

## MapStruct Best Practices

### DO: Reference constants from concrete class

```java
@Mapping(target = Product.ATTR_ID, ignore = true)  // Correct
```

### DON'T: Reference constants from interface

```java
@Mapping(target = Identified.ATTR_ID, ignore = true)  // Wrong - breaks build
```

This is a deliberate pattern - SonarQube may flag it but MapStruct requires the concrete class reference.

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-base-types` | Interfaces that `BaseModel` implements |
| `trishul-crud` | CRUD service patterns using these base classes |
| `trishul-repo` | Repository layer building on `BaseEntity` |
