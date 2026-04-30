# trishul-repo

> **Use this when**: You need fluent JPA query building, specification patterns, or extended repository operations with pagination.

Provides `WhereClauseBuilder` for type-safe criteria queries, `RepoService` for standard operations, and `PageDto` for API responses.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-repo</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Build WHERE clauses fluently
Specification<Product> spec = WhereClauseBuilder.builder()
    .in("category", Set.of("electronics", "appliances"))
    .between("price", minPrice, maxPrice)
    .like("name", Set.of("%phone%", "%tablet%"))
    .build();

// 2. Use with JPA repository
List<Product> products = productRepository.findAll(spec);

// 3. Add pagination
Page<Product> page = productRepository.findAll(spec, PageRequest.of(0, 20));

// 4. Return as PageDto
PageDto<ProductDto> response = new PageDto<>(
    page.map(mapper::toDto).getContent(),
    page.getTotalPages(),
    page.getTotalElements()
);
```

## When Would I Use This?

### When you need to build dynamic WHERE clauses

Use `WhereClauseBuilder`:

```java
@Service
public class ProductQueryService {
    @Autowired private ProductRepository repo;
    
    public List<Product> search(
        Set<UUID> ids,
        Set<String> categories,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        String namePattern,
        Boolean isActive
    ) {
        Specification<Product> spec = WhereClauseBuilder.builder()
            .in("id", ids)                              // WHERE id IN (...)
            .in("category", categories)                 // AND category IN (...)
            .between("price", minPrice, maxPrice)       // AND price BETWEEN ... AND ...
            .like("name", namePattern != null 
                ? Set.of("%" + namePattern + "%") 
                : null)                                 // AND name LIKE '%...%'
            .is("isActive", isActive)                   // AND isActive = ...
            .build();
        
        return repo.findAll(spec);
    }
}
```

All methods handle `null` gracefully (no condition added if value is null).

### When you need to query nested entity properties

Use string arrays for paths:

```java
Specification<Order> spec = WhereClauseBuilder.builder()
    .in(new String[]{"customer", "id"}, customerIds)           // customer.id IN (...)
    .is(new String[]{"customer", "status", "name"}, "ACTIVE")  // customer.status.name = 'ACTIVE'
    .between(new String[]{"payment", "amount"}, min, max)      // payment.amount BETWEEN ...
    .build();
```

### When you need LIKE queries with multiple patterns

```java
// Match any of the patterns (OR logic within LIKE)
Specification<Product> spec = WhereClauseBuilder.builder()
    .like("name", Set.of("%beer%", "%ale%", "%lager%"))
    .build();
// Generates: name LIKE '%beer%' OR name LIKE '%ale%' OR name LIKE '%lager%'
```

### When you need range queries (BETWEEN, >=, <=)

```java
// Full range
spec = WhereClauseBuilder.builder()
    .between("createdAt", startDate, endDate)  // createdAt BETWEEN start AND end
    .build();

// Greater than or equal (end is null)
spec = WhereClauseBuilder.builder()
    .between("price", minPrice, null)          // price >= minPrice
    .build();

// Less than or equal (start is null)
spec = WhereClauseBuilder.builder()
    .between("price", null, maxPrice)          // price <= maxPrice
    .build();
```

### When you need negation

```java
Specification<Product> spec = WhereClauseBuilder.builder()
    .not()                        // Negate the next condition
    .is("status", "DISCONTINUED") // NOT status = 'DISCONTINUED'
    .build();
```

### When you need standardized pagination

Use `RepoService.pageRequest()`:

```java
@Service
public class ProductService implements RepoService<UUID, Product, ProductAccessor> {
    @Autowired private ProductRepository repo;
    
    public Page<Product> getProducts(
        Specification<Product> spec,
        SortedSet<String> sortFields,  // e.g., ["name", "price"]
        boolean ascending,
        int page,
        int size
    ) {
        PageRequest pageRequest = RepoService.pageRequest(sortFields, ascending, page, size);
        return repo.findAll(spec, pageRequest);
    }
}
```

### When you need paginated API responses

Use `PageDto`:

```java
@GetMapping("/products")
public PageDto<ProductDto> getProducts(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "20") int size,
    @RequestParam(defaultValue = "name") SortedSet<String> sort,
    @RequestParam(defaultValue = "true") boolean asc
) {
    Page<Product> products = service.getProducts(null, sort, asc, page, size);
    
    List<ProductDto> content = products.getContent().stream()
        .map(mapper::toDto)
        .toList();
    
    return new PageDto<>(content, products.getTotalPages(), products.getTotalElements());
}
```

Response:
```json
{
    "content": [...],
    "totalPages": 5,
    "totalElements": 100
}
```

### When you need efficient existence and delete checks

Use `ExtendedRepository`:

```java
public interface ProductRepository 
    extends JpaRepository<Product, UUID>, 
            JpaSpecificationExecutor<Product>,
            ExtendedRepository<UUID> {
    // Inherited:
    // boolean existsByIds(Iterable<UUID> ids);  - Check if ALL ids exist
    // int deleteByIds(Iterable<UUID> ids);      - Delete multiple, return count
    // int deleteOneById(UUID id);               - Delete one, return count (0 or 1)
}
```

## Available Specification Types

| Spec | Description | Example |
|------|-------------|---------|
| `ColumnSpec<T>` | Column path reference | `new ColumnSpec<>(new String[]{"tenant", "id"})` |
| `InSpec<T>` | IN clause | `new InSpec<>(columnSpec, values)` |
| `IsSpec<T>` | Equality | `new IsSpec<>(columnSpec, value)` |
| `IsNullSpec` | NULL check | `new IsNullSpec(columnSpec)` |
| `LikeSpec` | Pattern matching | `new LikeSpec(columnSpec, patterns)` |
| `BetweenSpec<C>` | Range | `new BetweenSpec<>(columnSpec, start, end)` |
| `AndSpec` | AND conjunction | `new AndSpec(spec1, spec2)` |
| `NotSpec` | Negation | `new NotSpec(spec)` |

### Aggregate Specs (for custom queries)

| Spec | SQL Equivalent |
|------|----------------|
| `SumSpec<N>` | `SUM(column)` |
| `CountSpec` | `COUNT(column)` |
| `AverageSpec<N>` | `AVG(column)` |
| `MinSpec<C>` | `MIN(column)` |
| `MaxSpec<C>` | `MAX(column)` |

## Advanced: Using CriteriaSpec Directly

For complex queries, use specs directly:

```java
public Specification<Order> buildComplexSpec(Set<UUID> customerIds, BigDecimal minTotal) {
    return (root, query, cb) -> {
        // Build column references
        CriteriaSpec<UUID> customerIdSpec = new ColumnSpec<>(new String[]{"customer", "id"});
        CriteriaSpec<BigDecimal> totalSpec = new ColumnSpec<>(new String[]{"total"});
        
        // Build conditions
        CriteriaSpec<Boolean> inCustomers = new InSpec<>(customerIdSpec, customerIds);
        CriteriaSpec<Boolean> minTotalSpec = new BetweenSpec<>(totalSpec, minTotal, null);
        
        // Combine
        CriteriaSpec<Boolean> combined = new AndSpec(inCustomers, minTotalSpec);
        
        return combined.getExpression(root, query, cb);
    };
}
```

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                      Controller Layer                            │
│            @GetMapping with page, size, sort params              │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                      Service Layer                               │
│                                                                  │
│   WhereClauseBuilder.builder()                                   │
│       .in("field", values)                                       │
│       .between("date", start, end)                               │
│       .build() → Specification<T>                                │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│          JpaRepository + JpaSpecificationExecutor                │
│                                                                  │
│    findAll(Specification<T>, Pageable) → Page<T>                 │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                        Hibernate/JPA                             │
│              Generated SQL with WHERE clause                     │
└─────────────────────────────────────────────────────────────────┘
```

## Related Modules

| Module | Use When |
|--------|----------|
| `trishul-crud` | You need full CRUD controller/service patterns |
| `trishul-data` | You need DataSource configuration |
| `trishul-tenant-persistence` | You need multi-tenant database routing |
| `trishul-model` | You need base entity classes |
