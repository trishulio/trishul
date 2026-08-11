# Learned Patterns for Improving Code Coverage

## 1. Mocking AWS SDK Response Metadata
When testing AWS client implementations that log request IDs, it's necessary to mock the `ResponseMetadata`.
Example:
```java
Map<String, String> responseMetadata = new HashMap<>();
responseMetadata.put(ResponseMetadata.AWS_REQUEST_ID, "REQ_ID");
DeleteGroupResult result = mock(DeleteGroupResult.class);
when(result.getSdkResponseMetadata()).thenReturn(new ResponseMetadata(responseMetadata));
```

## 2. Handling ResourceNotFoundException / NoSuchEntityException
AWS SDK often throws `ResourceNotFoundException` or `NoSuchEntityException` (for IAM). To achieve 100% coverage, always include test cases that trigger these catch blocks.
```java
when(mIdp.getGroup(any(GetGroupRequest.class))).thenThrow(ResourceNotFoundException.class);
// or for IAM
when(mAwsIamClient.getPolicy(any(GetPolicyRequest.class))).thenThrow(NoSuchEntityException.class);
```

## 3. Covering Branch Logic in Helper Methods
Methods like `roleArn` that have conditional logic (e.g., null checks) should be tested by passing various combinations of input from the public methods that call them.
- Test with null objects.
- Test with objects having null fields.

## 4. Testing Method existence
Ensure all interface methods are tested, even if they seem trivial. In this class, the `delete` method was completely missing from the original test file.

## 5. Mocking Strategy
- Use `spy()` for the class under test when you want to mock some of its methods (like `exists()` or `get()`) while testing others (like `put()`).
- Use `doAnswer()` when you need to verify the arguments passed to a mock and return a dynamic result based on those arguments.
- Use `doReturn()` or `when(...).thenReturn(...)` for simple mock behavior.

## 6. Fluent Setter Verification
Always verify that setters returning `this` actually return the current instance to ensure full branch and line coverage.

## 7. BaseModel Coverage
For classes extending `BaseModel`, explicitly test `equals`, `hashCode`, and `toString` to ensure reflection-based logic works as expected for the specific fields of the subclass.

## 8. Null Handling in Deep-Cloning Setters
Explicitly test passing `null` to setters that perform null-safe deep cloning to cover both branches.

## 9. Null Check Branch Coverage
Always test both paths of a null check (e.g., `if (buckets != null)`) even if the failure path results in an exception, as this is necessary for 100% branch coverage.

## 10. Cache Validation
When a class uses internal caching (like `InheritableThreadLocal`), use Mockito's `verify(..., times(n))` to ensure the cache is being utilized and reset correctly.

## 11. Null Result Field Coverage
When a client returns a result object, always test the case where the fields within that result object are null to cover internal mapping logic.

## 12. Exception Re-throwing Coverage
For methods that catch and re-throw exceptions (like `put`), use `assertThrows` to verify the exception is propagated after logging.

## 13. Testing AutoConfiguration with Factory Dependencies
When testing Spring `@Configuration` classes that use a factory to create beans, mock the factory to verify the bean creation method is called with correct parameters. This ensures 100% coverage of the `@Bean` method without requiring the full factory logic or external dependencies.
Example:
```java
@Test
void testIamClient_ReturnsIamClientFromFactory() {
  IaasAccessAwsFactory mFactory = mock(IaasAccessAwsFactory.class);
  AmazonIdentityManagement mIam = mock(AmazonIdentityManagement.class);
  when(mFactory.iamClient("key", "secret")).thenReturn(mIam);

  AmazonIdentityManagement iam = config.iamClient(mFactory, "key", "secret");

  assertSame(mIam, iam);
}
```

## 14. Triggering Reflection-related Exceptions
To cover catch blocks for checked exceptions like `IntrospectionException` or standard exceptions like `IllegalArgumentException` in reflection-based logic:
- Use `mockStatic(Introspector.class)` to mock static methods like `getBeanInfo`.
- Use a mock for arguments (e.g., a `Set`) that throws an exception when a method (e.g., `contains`) is called within the `try` block.
- For `IllegalArgumentException` specifically, if it's thrown during setter invocation on a primitive field with `null`, ensure that any intermediate utility (like `ReflectionManipulator`) doesn't catch and wrap it first. If it does, you may need to trigger it from a different source within the same `try` block (like the predicate) to hit the catch block in the target class.

## 15. Testing No-Action Implementation
For classes that implement an interface with a "no-op" or "do nothing" implementation (e.g., `NoActionDecorator`), explicitly call the method with both valid (non-null) and null inputs. This verifies the method remains exception-safe even if it doesn't perform any logic, while ensuring 100% line coverage for the method declaration and body.

## 16. Covering Branch Logic in Summation Loops with Null Checks
For loops that accumulate values with null checks (e.g., `if (total == null) { total = val; } else { if (val != null) { total = total.plus(val); } }`), ensure the following cases are covered:
- The accumulator starts as null and the first value is null (accumulator stays null).
- The accumulator starts as null and the first value is non-null (accumulator becomes non-null).
- The accumulator is non-null and the next value is null (accumulator stays the same, inner branch skipped).
- The accumulator is non-null and the next value is non-null (accumulator is updated, inner branch hit).
This is especially important for classes like `TaxCalculator` that handle multiple independent accumulators (e.g., PST, GST, HST).

## 17. Testing MapStruct Mappers with Nested Nulls
MapStruct often generates null checks for nested objects. To achieve 100% coverage on the generated `*MapperImpl` class, always add tests for scenarios where nested objects in the source DTO or POJO are null.
Example:
If mapping `Amount.taxAmount` to `AmountDto.taxAmount`, test:
- `toDto` with an `Amount` having `taxAmount = null`.
- `fromDto` with an `AmountDto` having `taxAmount = null`.
This covers the generated `if ( taxAmount == null )` branches.

## 19. Covering Branch Logic in Reflection-Based Copying
For `BaseModel` methods that use `ReflectionManipulator` (like `outerJoin` with include), achieving 100% branch coverage requires testing both:
- The case where the `include` predicate returns `true` AND the `readMethod` returns a non-null value.
- The case where the `include` predicate returns `true` AND the `readMethod` returns a null value.
- The case where the property is NOT in the `include` set (short-circuiting the predicate).
Ensure that the `TestModel` has sufficient fields with both `null` and non-null values to exercise all these combinations.

## 20. Pattern for Pojo/Model Implementation Testing
- **Core Pattern**: Many classes follow a pattern where core logic is defined in interfaces with prefixes like `Base<InterfaceName>` and `Update<InterfaceName>`.
- **Testing Rule**: Only test the implementing Pojo/Model classes. The interfaces themselves do not require direct unit tests, as they are implicitly covered by the tests for the implementing classes.
- **Cleanup Rule**: Remove `testContextLoads()` style tests, as they are redundant and do not provide meaningful code coverage.
