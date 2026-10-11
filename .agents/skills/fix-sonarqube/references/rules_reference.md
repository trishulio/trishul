# Trishul SonarQube Rules Reference

This document codifies the triage rules for static code analysis in the Trishul framework. It explicitly defines which SonarQube rules must be **preserved** (ignored as architectural design patterns) and which rules must be **adhered to** (fixed).

---

## 1. Architectural Patterns to PRESERVE (DO NOT MODIFY)

These SonarQube rules report false positives against established Trishul design patterns. Attempting to "fix" these violations breaks compilation, mapper code generation, API contracts, or testing harnesses.

| Rule ID | Rule Name | Trishul Architecture Context & Rationale |
|---|---|---|
| `java:S3252` | Static member accessed via subclass | **CRITICAL - DO NOT CHANGE**: MapStruct mapper generation requires static entity constants (`ATTR_ID`, `ATTR_VERSION`) to be referenced directly through the concrete entity class (`Tenant.ATTR_ID`), not the base interface (`Identified.ATTR_ID`). Changing this breaks MapStruct or violates the canonical mapper pattern. |
| `java:S119` | Type parameter naming convention | Core generic type parameters (`T extends BaseUser<T>`, `E extends CrudEntity<ID, E>`, `ID`, `R`) define the entity hierarchy across 55+ modules. Renaming breaks public API contracts and framework polymorphism. |
| `java:S1135` / `xml:S1135` | Track uses of "TODO" tags | Trishul policy strictly forbids removing TODO comments simply to silence SonarQube. TODOs must remain until their associated feature or fix is actually implemented. |
| `java:S1874` / `java:S1123` | Deprecated code usage | Deprecated methods and tags are intentionally preserved to ensure binary and source backward compatibility for existing consumers. They are only removed during major version bumps. |
| `java:S1602` | Remove lambda expression block braces | In multi-overloaded functional interfaces (e.g. `TenantSchemaRegister`), explicit braces `{ return ...; }` are required to disambiguate overloaded methods (`CheckedSupplier` vs `CheckedConsumer`). Removing braces causes compiler failure (`reference to query is ambiguous`). |
| `java:S3011` | Reflection accessibility bypass | Trishul's core model layer (`ReflectionManipulator`, `BaseModel.outerJoin`, `BaseModel.override`) dynamically inspects and mutates properties. Reflection access is required by design. |
| `java:S2160` | Subclasses adding fields should override equals | DTOs and POJOs extending `BaseModel` rely on `BaseModel`'s reflection-based `equals`, `hashCode`, and `toString`. Adding boilerplate overrides across hundreds of DTOs breaks uniformity. |
| `java:S6548` | "Singleton" pattern should not be used | Framework constants and fallback/default entities (e.g. `AdminTenant.INSTANCE`) intentionally use singleton instances. |
| `java:S1172` | Unused method parameters (Framework signatures) | Method parameters in service interfaces (e.g. `Pageable pageable`, `ContextHolder contextHolder`) or controllers are part of polymorphic API contracts or framework interception hooks. |
| `java:S106` | Standard output usage (`System.out` / `System.err`) | CLI utilities, standalone tools, and migration runners intentionally print progress and diagnostic output to stdout/stderr. |

---

## 2. Actionable Code Smells to FIX (ALWAYS ADHERE TO)

These rules identify genuine code smells, memory leaks, concurrency bugs, and dead code. They should always be remediated when discovered.

### Concurrency & Lifecycle Rules

#### `java:S2142`: "InterruptedException" should not be ignored
- **Problem**: Catching `InterruptedException` without restoring the interrupted flag causes calling threads or thread pools to lose cancellation state.
- **Fix**: Call `Thread.currentThread().interrupt();` inside the catch block before wrapping and rethrowing.
- **Testing Guardrail**: In unit tests, assert `assertTrue(Thread.currentThread().isInterrupted())` and clean up with `Thread.interrupted()` in `finally`.
```java
// ❌ BAD
} catch (InterruptedException e) {
  throw new RuntimeException(e);
}

// ✅ GOOD
} catch (InterruptedException e) {
  Thread.currentThread().interrupt();
  throw new RuntimeException(String.format("Task interrupted: %s", e.getMessage()), e);
}
```

#### `java:S5164`: "ThreadLocal" variables should be cleaned up
- **Problem**: ThreadLocal instances in thread-pooled environments (like web servers or connection managers) cause severe memory leaks and cross-tenant pollution.
- **Fix**: Always call `.remove()` on ThreadLocal objects in a `finally` block or when releasing context.
```java
// ✅ GOOD
try {
  localCache.set(data);
  process();
} finally {
  localCache.remove();
}
```

---

### Dead Code & Simplification Rules

#### `java:S1068` & `java:S1481`: Unused private fields & local variables
- **Fix**: Safely delete dead fields and variables that have no reads.

#### `java:S1144`: Unused private methods
- **Fix**: Delete unused private methods.

#### `java:S1488`: Immediately returned local variable
- **Fix**: Inline the expression directly into the `return` statement.
```java
// ❌ BAD
User user = repository.findById(id);
return user;

// ✅ GOOD
return repository.findById(id);
```

#### `java:S1155`: Collection.isEmpty() should be used instead of size()
- **Fix**: Replace `.size() == 0` or `.size() > 0` with `.isEmpty()` or `!collection.isEmpty()`. This also makes code immune to PIT boundary mutators.

#### `java:S2129`: Primitive / Boxed Constructors
- **Fix**: Replace `new Long(x)` or `new String(s)` with `Long.valueOf(x)` or string literals.

#### `java:S1161`: Missing `@Override` annotation
- **Fix**: Add `@Override` to methods implementing interface contracts or superclass methods.

#### `java:S3776`: Cognitive Complexity
- **Fix**: Refactor complex nested if/else statements by introducing guard clauses, helper methods, or Java stream pipelines.

#### `java:S125`: Commented-out code
- **Fix**: Remove dead commented-out code blocks (git history preserves previous implementations).
