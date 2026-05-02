# trishul-crud

> **Use this when**: You need reusable CRUD controllers and services with automatic PUT/PATCH merging, optimistic locking, pagination, and standardized error responses.

Generic CRUD patterns that handle entity merging, version checking, and exception-to-HTTP-status mapping.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-crud</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Create your service implementing CrudService
@Service
public class ProductService implements CrudService<UUID, Product, BaseProduct, UpdateProduct, ProductAccessor> {
    private final ProductRepository repo;
    private final EntityMergerService<UUID, Product, BaseProduct, UpdateProduct> merger;

    @Override
    public List<Product> add(List<? extends BaseProduct> additions) {
        return repo.saveAll(merger.getAddEntities(additions));
    }

    @Override
    public List<Product> patch(List<? extends UpdateProduct> patches) {
        List<Product> existing = repo.findAllById(patches.stream().map(UpdateProduct::getId).toList());
        return repo.saveAll(merger.getPatchEntities(existing, patches));  // PATCH: non-null only
    }

    @Override
    public List<Product> put(List<? extends UpdateProduct> updates) {
        List<Product> existing = repo.findAllById(updates.stream().map(UpdateProduct::getId).toList());
        return repo.saveAll(merger.getPutEntities(existing, updates));    // PUT: all fields
    }
}

// 2. Create your controller using CrudControllerService
@RestController
@RequestMapping("/api/products")
public class ProductController extends BaseController {
    private final CrudControllerService<UUID, Product, BaseProduct, UpdateProduct,
            ProductDto, AddProductDto, UpdateProductDto> controllerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductDto> add(@Valid @RequestBody List<AddProductDto> dtos) {
        return controllerService.add(dtos);
    }

    @PatchMapping
    public List<ProductDto> patch(@Valid @RequestBody List<UpdateProductDto> dtos) {
        return controllerService.patch(dtos);
    }
}
```

## When Would I Use This?

### When you need automatic PATCH vs PUT merge logic

Use `EntityMergerService` to handle the difference:

```java
// PATCH: Only updates non-null fields
List<Product> patched = merger.getPatchEntities(existing, updates);

// PUT: Replaces all fields (including setting nulls)
List<Product> replaced = merger.getPutEntities(existing, updates);
```

| HTTP Method | Merger Method | Behavior |
|-------------|---------------|----------|
| `PATCH` | `getPatchEntities()` | Copy only non-null fields from update |
| `PUT` | `getPutEntities()` | Copy all fields from update |

### When you need optimistic locking validation

Use `LockService` to check version before updates:

```java
@Autowired
private LockService lockService;

public Product update(UpdateProductDto dto) {
    Product existing = repository.findById(dto.getId()).orElseThrow();

    // Throws OptimisticLockException if versions don't match
    lockService.optimisticLockCheck(existing, dto);

    existing.outerJoin(dto);
    return repository.save(existing);
}
```

### When you need standard pagination parameters

Extend `BaseController` for consistent query params:

```java
@RestController
public class ProductController extends BaseController {

    @GetMapping
    public PageDto<ProductDto> getAll(
        @RequestParam(defaultValue = "id") SortedSet<String> sort,
        @RequestParam(defaultValue = "true") boolean order_asc,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size,
        @RequestParam(required = false) Set<String> attr  // Attribute filtering
    ) {
        Page<Product> products = service.getAll(spec, sort, order_asc, page, size);
        return controllerService.getAll(products, attr);
    }
}
```

Standard parameter names:
- `sort` - Field(s) to sort by (default: `id`)
- `order_asc` - Sort ascending (default: `true`)
- `page` - Page index, 0-based (default: `0`)
- `size` - Page size (default: `100`)
- `attr` - Fields to include in response

### When you need attribute filtering in responses

Use `AttributeFilter` to return only requested fields:

```java
// Request: GET /api/products/123?attr=id,name,price

@GetMapping("/{id}")
public ProductDto get(@PathVariable UUID id, @RequestParam(required = false) Set<String> attr) {
    return controllerService.get(id, attr);
    // Response only includes: {"id": "...", "name": "...", "price": ...}
}
```

### When you need consistent error responses

`ControllerExceptionHandler` maps exceptions to HTTP status codes automatically:

| Exception | HTTP Status | When |
|-----------|-------------|------|
| `EntityNotFoundException` | 404 Not Found | Entity doesn't exist |
| `OptimisticLockException` | 409 Conflict | Version mismatch |
| `DataIntegrityViolationException` | 409 Conflict | Unique constraint violation |
| `MethodArgumentNotValidException` | 400 Bad Request | Validation failed |
| `IllegalArgumentException` | 400 Bad Request | Invalid input |
| `RuntimeException` | 500 Internal Error | Unexpected error |

Response format:
```json
{
    "timestamp": "2024-01-15T10:30:00",
    "status": 404,
    "error": "Not Found",
    "message": "Product not found with id: 123e4567-...",
    "path": "/api/products/123e4567-..."
}
```

### When you need to decorate DTOs before returning

Use `EntityDecorator` to add computed fields (e.g., presigned URLs):

```java
@Bean
public EntityDecorator<ProductDto> productDecorator(S3PresignedUrlService urlService) {
    return dtos -> dtos.forEach(dto -> {
        if (dto.getImageKey() != null) {
            dto.setImageUrl(urlService.generatePresignedUrl(dto.getImageKey()));
        }
    });
}

// Pass to controller service
new CrudControllerService<>(filter, mapper, service, "Product", productDecorator);
```

### When you need batch operations for efficiency

All CRUD methods accept lists - use them for batch processing:

```java
// Good: Single database round-trip
List<Product> products = service.add(List.of(product1, product2, product3));

// Avoid: Multiple round-trips
Product p1 = service.add(List.of(product1)).get(0);
Product p2 = service.add(List.of(product2)).get(0);
```

## Complete CRUD Implementation

### 1. Entity

```java
@Entity
@Table(name = "PRODUCT")
public class Product extends BaseEntity
    implements CrudEntity<UUID, Product>, Audited<Product> {

    @Id private UUID id;
    private String name;
    private BigDecimal price;
    @CreationTimestamp private LocalDateTime createdAt;
    @UpdateTimestamp private LocalDateTime lastUpdated;
    @Version private Integer version;
}
```

### 2. DTOs

```java
public class ProductDto extends BaseDto {
    private UUID id;
    private String name;
    private BigDecimal price;
}

public class AddProductDto extends BaseDto {
    @NotBlank private String name;
    @NotNull @Positive private BigDecimal price;
}

public class UpdateProductDto extends BaseDto {
    @NotNull private UUID id;
    @NullOrNotBlank private String name;
    @Positive private BigDecimal price;
    private Integer version;  // Required for optimistic locking
}
```

### 3. Mapper

```java
@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<Product, ProductDto, AddProductDto, UpdateProductDto> {
    @Override
    @Mapping(target = Product.ATTR_ID, ignore = true)
    @Mapping(target = Product.ATTR_CREATED_AT, ignore = true)
    Product fromAddDto(AddProductDto dto);
}
```

### 4. Service Configuration

```java
@Configuration
public class ProductConfig {
    @Bean
    public EntityMergerService<UUID, Product, BaseProduct, UpdateProduct> productMerger(LockService lockService) {
        return new CrudEntityMergerService<>(
            lockService,
            BaseProduct.class,
            UpdateProduct.class,
            Product.class,
            Set.of("id", "createdAt", "lastUpdated", "version")  // Excluded from merge
        );
    }
}
```

### 5. Controller

```java
@RestController
@RequestMapping("/api/products")
public class ProductController extends BaseController {

    private final CrudControllerService<UUID, Product, BaseProduct, UpdateProduct,
            ProductDto, AddProductDto, UpdateProductDto> controllerService;
    private final ProductService service;

    @GetMapping
    public PageDto<ProductDto> getAll(
            @RequestParam(defaultValue = "id") SortedSet<String> sort,
            @RequestParam(defaultValue = "true") boolean order_asc,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(required = false) Set<String> attr,
            @RequestParam(required = false) String name) {

        Specification<Product> spec = WhereClauseBuilder.builder()
            .like("name", name != null ? Set.of("%" + name + "%") : null)
            .build();

        return controllerService.getAll(service.getAll(spec, sort, order_asc, page, size), attr);
    }

    @GetMapping("/{id}")
    public ProductDto get(@PathVariable UUID id, @RequestParam(required = false) Set<String> attr) {
        return controllerService.get(id, attr);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductDto> add(@Valid @RequestBody List<AddProductDto> dtos) {
        return controllerService.add(dtos);
    }

    @PutMapping
    public List<ProductDto> put(@Valid @RequestBody List<UpdateProductDto> dtos) {
        return controllerService.put(dtos);
    }

    @PatchMapping
    public List<ProductDto> patch(@Valid @RequestBody List<UpdateProductDto> dtos) {
        return controllerService.patch(dtos);
    }

    @DeleteMapping
    public long delete(@RequestParam Set<UUID> ids) {
        return controllerService.delete(ids);
    }
}
```

## Auto-Configured Beans

| Bean | Purpose |
|------|---------|
| `LockService` | Optimistic locking validation |
| `BlockingAsyncExecutor` | Parallel task execution |
| `AttributeFilter` | Response field filtering |

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-repo` | Repository utilities this module builds upon |
| `trishul-model` | Base classes and mappers |
| `trishul-api` | REST API infrastructure |
