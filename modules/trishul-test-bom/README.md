# trishul-test-bom

Bill of Materials (BOM) for test dependencies used across all Trishul modules.

> **Use this when**: You need a consistent set of test dependencies (JUnit 5, Mockito, AssertJ, Spring Boot Test) in your module.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-test-bom</artifactId>
    <scope>test</scope>
</dependency>
```

## When Would I Use This?

### When you need standard test dependencies in a new module

Include this BOM to get all testing libraries pre-configured:

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-test-bom</artifactId>
    <scope>test</scope>
</dependency>
```

This single dependency provides:
- JUnit 5 (via Spring Boot Test)
- Mockito with JUnit 5 integration
- AssertJ for fluent assertions
- Jackson JSR-310 for date/time serialization in tests
- HSQLDB for in-memory database testing
- Spring Boot Test infrastructure

### When you need in-memory database for integration tests

HSQLDB is included for E2E testing without external database:

```java
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class MyIntegrationTest {
    // Uses HSQLDB in-memory database
}
```

## Bundled Dependencies

| Dependency | Purpose |
|------------|---------|
| `spring-boot-starter-test` | Spring Boot test infrastructure |
| `mockito-junit-jupiter` | Mockito with JUnit 5 integration |
| `assertj-core` | Fluent assertions |
| `jackson-datatype-jsr310` | Java 8 date/time serialization |
| `hsqldb` | In-memory database for testing |
| `trishul-base-types` | Core interfaces for test entities |

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-test` | Test helper classes and utilities |
| `trishul-base-types` | Core interfaces |
