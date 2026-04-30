# trishul-test

Test helper classes, mock utilities, and dummy entities for testing Trishul modules.

> **Use this when**: You need mock utilities, dummy CRUD entities, or test helpers for unit and integration tests.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-test</artifactId>
    <scope>test</scope>
</dependency>
```

## When Would I Use This?

### When you need a dummy entity for testing CRUD operations

Use `DummyCrudEntity` - a complete test entity with all CRUD patterns:

```java
@Test
void testCrudOperations() {
    DummyCrudEntity entity = new DummyCrudEntity()
        .setId(1L)
        .setName("Test Entity")
        .setVersion(1);
    
    // Test your CRUD service with this dummy entity
}
```

### When you need to mock database repositories

Use `DbMockUtil` for common repository mocking patterns:

```java
@ExtendWith(MockitoExtension.class)
class MyServiceTest {
    
    @Mock
    private MyRepository repository;
    
    @BeforeEach
    void setup() {
        DbMockUtil.mockFindById(repository, entity);
    }
}
```

### When you need mock JSON generation for tests

Use `MockJsonGenerator`:

```java
@Test
void testJsonSerialization() {
    String json = MockJsonGenerator.generate(myObject);
    // Assert JSON structure
}
```

### When you need a test utility provider

Use `MockUtilProvider` for centralized test utilities:

```java
MockUtilProvider provider = new MockUtilProvider();
// Access common mock utilities
```

## Core Components

### Test Entities

| Class | Purpose |
|-------|---------|
| `DummyCrudEntity` | Complete CRUD entity for testing |
| `BaseDummyCrudEntity` | Base interface for test entities |
| `UpdateDummyCrudEntity` | Update DTO for test entities |
| `DummyCrudEntityAccessor` | Accessor interface for relationships |

### Test Utilities

| Class | Purpose |
|-------|---------|
| `DbMockUtil` | Database/repository mocking helpers |
| `MockJsonGenerator` | JSON generation for test data |
| `MockUtilProvider` | Centralized utility provider |

### Test Infrastructure

| Class | Purpose |
|-------|---------|
| `DummyCrudEntityRepository` | Test JPA repository |
| `DummyCrudEntitySpecification` | JPA Specification for queries |
| `DummyCrudEntityRefresher` | Entity refresh utilities |
| `DummyCrudEntitySet` | Collection utilities for test entities |

## Complete Example

```java
@ExtendWith(MockitoExtension.class)
class MyCrudServiceTest {
    
    @Mock
    private DummyCrudEntityRepository repository;
    
    @InjectMocks
    private MyCrudService service;
    
    @Test
    void testAdd_ReturnsCreatedEntity() {
        // Arrange
        BaseDummyCrudEntity addition = new BaseDummyCrudEntity()
            .setName("New Entity");
        
        DummyCrudEntity expected = new DummyCrudEntity()
            .setId(1L)
            .setName("New Entity");
        
        when(repository.save(any())).thenReturn(expected);
        
        // Act
        DummyCrudEntity result = service.add(addition);
        
        // Assert
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("New Entity");
    }
}
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-test-bom` | Test dependency BOM |
| `trishul-repo` | JPA repository utilities |
| `trishul-crud` | CRUD service abstractions |
