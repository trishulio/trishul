package io.trishul.model.reflection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.google.common.cache.LoadingCache;
import io.trishul.test.bom.model.Dummy;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class ReflectionManipulatorTest {
  public static class TestData {
    public TestData() {}

    public TestData(int x) {
      this.setX(x);
    }

    int x;

    public int getX() {
      return this.x;
    }

    public void setX(int x) {
      this.x = x;
    }

    int y;

    public int getY() {
      return this.y;
    }

    public void setY(int y) {
      this.y = y;
    }
  }

  public static class TestDataWithStringField {
    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  public static class TestDataWithoutSetter {
    private final int value;

    public TestDataWithoutSetter(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

  private ReflectionManipulator util;

  @BeforeEach
  void init() {
    this.util = ReflectionManipulator.INSTANCE;
  }

  @Test
  void testEquals_ReturnsTrue_WhenBothArgsAreNull() {
    assertTrue(this.util.equals(null, null));
  }

  @Test
  void testEquals_ReturnsFalse_WhenArgIsNullAndTestDataIsNot() {
    assertFalse(this.util.equals(new Object(), null));
    assertFalse(this.util.equals(null, new Object()));
  }

  @Test
  void testEquals_ReturnsTrue_WhenObjectsAreSame() {
    final Object o = new Object();
    assertTrue(this.util.equals(o, o));
  }

  @Test
  void testEquals_ReturnsFalse_WhenObjectsAreNotEquals() {
    final TestData a = new TestData();
    final TestData b = new TestData();
    a.x = 10;
    b.x = 20;
    assertFalse(this.util.equals(a, b));
  }

  @Test
  void testHashCode_ReturnsSameHashCode_WhenObjectIsUnchanged() {
    final TestData a = new TestData();

    int hashCode = util.hashCode(a);
    assertEquals(hashCode, util.hashCode(a));
    assertEquals(hashCode, util.hashCode(a));
    assertEquals(hashCode, util.hashCode(a));
  }

  @Test
  void testHashCode_ReturnsDifferentHashCode_WhenObjectPropertiesChange() {
    final TestData a = new TestData();

    a.x = 0;
    int hashCode0 = util.hashCode(a);

    a.x = 1;
    int hashCode1 = util.hashCode(a);

    a.x = 2;
    int hashCode2 = util.hashCode(a);

    assertNotEquals(hashCode0, hashCode1);
    assertNotEquals(hashCode1, hashCode2);
    assertNotEquals(hashCode2, hashCode0);
  }

  @Test
  void testHashCode_ReturnsSameHash_WhenDifferentObjectsHaveSamePropertyValues() {
    final TestData a = new TestData();
    final TestData b = new TestData();
    assertEquals(util.hashCode(a), util.hashCode(b));

    a.x = 10;
    b.x = 10;
    assertEquals(util.hashCode(a), util.hashCode(b));

    a.y = 20;
    b.y = 20;
    assertEquals(util.hashCode(a), util.hashCode(b));
  }

  @Test
  void testHashCode_ReturnsDifferentHashCode_WhenObjectsHaveDifferentPropertyValues() {
    final TestData a = new TestData();
    final TestData b = new TestData();

    a.x = 1;
    b.x = 2;
    assertNotEquals(util.hashCode(a), util.hashCode(b));

    a.y = 2;
    b.y = 1;
    assertNotEquals(util.hashCode(a), util.hashCode(b));
  }

  @Test
  void testOuterJoin_ThrowsException_WhenEitherObjectIsNull() {
    NullPointerException exception1
        = assertThrows(NullPointerException.class, () -> this.util.copy(null, null, pd -> true));

    assertEquals("Outer Joins can not be on null objects", exception1.getMessage());
    NullPointerException exception2 = assertThrows(NullPointerException.class,
        () -> this.util.copy(null, new Dummy(), pd -> true));

    assertEquals("Outer Joins can not be on null objects", exception2.getMessage());

    NullPointerException exception3 = assertThrows(NullPointerException.class,
        () -> this.util.copy(new Dummy(), null, pd -> true));
    assertEquals("Outer Joins can not be on null objects", exception3.getMessage());
  }

  @Test
  void testOuterJoin_SkipsProperty_WhenGetterIsMissing() {
    class TestDataWithoutGetter {
      TestDataWithoutGetter(int x) {
        this.x = x;
      }

      int x;

      public void setX(int x) {
        this.x = x;
      }
    }
    final TestDataWithoutGetter o1 = new TestDataWithoutGetter(10);
    final TestDataWithoutGetter o2 = new TestDataWithoutGetter(20);

    this.util.copy(o1, o2, pd -> true);

    assertEquals(10, o1.x);
    assertEquals(20, o2.x);
  }

  @Test
  void testOuterJoin_SkipsProperty_WhenSetterIsMissing() {
    class TestDataWithoutSetter {
      TestDataWithoutSetter(int x) {
        this.x = x;
      }

      int x;

      public int getX() {
        return this.x;
      }
    }
    final TestDataWithoutSetter o1 = new TestDataWithoutSetter(10);
    final TestDataWithoutSetter o2 = new TestDataWithoutSetter(20);

    this.util.copy(o1, o2, pd -> true);

    assertEquals(10, o1.getX());
    assertEquals(20, o2.getX());
  }

  @Test
  void testOuterJoin_SkipsProperty_WhenPredicateReturnsFalse() {
    final TestData o1 = new TestData(10);
    final TestData o2 = new TestData(20);

    this.util.copy(o1, o2, pd -> false);

    assertEquals(10, o1.getX());
    assertEquals(20, o2.getX());
  }

  @Test
  void testOuterJoin_CopiesProperty_WhenGetterAndSetterAreBothPresentAndPredicateReturnsTrue() {
    final TestData o1 = new TestData(10);
    final TestData o2 = new TestData(20);

    this.util.copy(o1, o2, pd -> true);

    assertEquals(20, o1.getX());
    assertEquals(20, o2.getX());
  }

  @Test
  void testGetPropertyNames_ReturnsNull_WhenClassIsNull() {
    assertEquals(Collections.emptySet(), this.util.getPropertyNames(null, null));
  }

  @Test
  void testGetPropertyNames_ReturnsListOfPropertyNamesForTheGivenClass() {
    final Set<String> props = this.util.getPropertyNames(TestData.class, null);

    assertEquals(Set.of("x", "y", "class"), props);
  }

  @Test
  void testGetPropertyNames_ReturnsListOfPropertyNamesWithExclusions_WhenExcludedContainsClassProps() {
    Set<String> props = this.util.getPropertyNames(TestData.class, Set.of("y", "z"));

    props = this.util.getPropertyNames(TestData.class, Set.of("y", "z"));

    assertEquals(Set.of("x", "class"), props);
  }

  @Test
  void testConstruct_ReturnsNull_WhenClassIsNull() {
    assertNull(this.util.construct(null));
  }

  @Test
  void testConstruct_ReturnsObjectOfGivenClass_WhenClassIsNotNull() {
    final TestData o = this.util.construct(TestData.class);

    assertNotNull(o);
  }

  @Test
  void testConstruct_ReturnsObjectsWithValues_WhenClassAndPropertiesAreNotNull() {
    final TestData o = this.util.construct(TestData.class, Map.of("x", 1, "y", 2));

    assertEquals(1, o.getX());
    assertEquals(2, o.getY());
  }

  @Test
  void testInvokeSetter_SetsValue_WhenPropertyDescriptorHasReadMethod()
      throws IntrospectionException {
    TestDataWithStringField obj = new TestDataWithStringField();
    PropertyDescriptor pd = new PropertyDescriptor("name", TestDataWithStringField.class);

    util.invokeSetter(obj, pd, "test-value");

    assertEquals("test-value", obj.getName());
  }

  @Test
  void testInvokeSetter_SetsValue_UsingDerivedSetterName_WhenReadMethodExists()
      throws IntrospectionException {
    TestData obj = new TestData();
    PropertyDescriptor pd = new PropertyDescriptor("x", TestData.class);

    util.invokeSetter(obj, pd, 42);

    assertEquals(42, obj.getX());
  }

  @Test
  void testInvokeSetter_DoesNotThrow_WhenSetterMethodDoesNotExist() throws IntrospectionException {
    TestDataWithoutSetter obj = new TestDataWithoutSetter(10);
    PropertyDescriptor pd
        = new PropertyDescriptor("value", TestDataWithoutSetter.class, "getValue", null);

    // Should not throw, just logs info about missing setter
    util.invokeSetter(obj, pd, 20);

    // Value should remain unchanged since no setter exists
    assertEquals(10, obj.getValue());
  }

  @Test
  void testInvokeSetter_SetsNullValue_WhenValueIsNull() throws IntrospectionException {
    TestDataWithStringField obj = new TestDataWithStringField();
    obj.setName("initial");
    PropertyDescriptor pd = new PropertyDescriptor("name", TestDataWithStringField.class);

    util.invokeSetter(obj, pd, null);

    assertNull(obj.getName());
  }

  @Test
  void testPropNameKeySetters() {
    ReflectionManipulator.PropNameKey key
        = new ReflectionManipulator.PropNameKey(TestData.class, null);
    key.setClazz(TestDataWithStringField.class);
    key.setExclusions(Set.of("name"));
    assertEquals(TestDataWithStringField.class, key.getClazz());
    assertEquals(Set.of("name"), key.getExclusions());
  }

  @Test
  void testCopy_ThrowsRuntimeException_WhenIntrospectionExceptionOccurs() throws Exception {
    try (MockedStatic<Introspector> mockedIntrospector = mockStatic(Introspector.class)) {
      mockedIntrospector.when(() -> Introspector.getBeanInfo(any(), any()))
          .thenThrow(new IntrospectionException("Custom error"));

      assertThrows(RuntimeException.class,
          () -> util.copy(new TestData(), new TestData(), pd -> true));
    }
  }

  @Test
  void testCopy_ThrowsRuntimeException_WhenReflectiveOperationExceptionOccurs() {
    assertThrows(RuntimeException.class, () -> util.copy(new TestData(), new TestData(), pd -> {
      throw new ReflectiveOperationException("custom");
    }));
  }

  @Test
  void testGetPropertyNames_ThrowsRuntimeException_WhenExecutionExceptionOccurs() throws Exception {
    Field field = ReflectionManipulator.class.getDeclaredField("propNamesCacheWithExclusions");
    field.setAccessible(true);
    @SuppressWarnings("unchecked")
    LoadingCache<ReflectionManipulator.PropNameKey, Set<String>> mockCache
        = mock(LoadingCache.class);
    when(mockCache.get(any()))
        .thenThrow(new ExecutionException("custom", new RuntimeException("cause")));

    ReflectionManipulator customUtil = new ReflectionManipulator();
    field.set(customUtil, mockCache);

    assertThrows(RuntimeException.class, () -> customUtil.getPropertyNames(TestData.class, null));
  }

  public static class ClassWithoutNoArgConstructor {
    public ClassWithoutNoArgConstructor(String arg) {}
  }

  @Test
  void testConstruct_ThrowsRuntimeException_WhenReflectiveOperationExceptionOccurs() {
    assertThrows(RuntimeException.class, () -> util.construct(ClassWithoutNoArgConstructor.class));
  }

  @Test
  void testConstruct_ThrowsRuntimeException_WhenIntrospectionExceptionOccurs() throws Exception {
    try (MockedStatic<Introspector> mockedIntrospector = mockStatic(Introspector.class)) {
      mockedIntrospector.when(() -> Introspector.getBeanInfo(any(), any()))
          .thenThrow(new IntrospectionException("Custom error"));

      assertThrows(RuntimeException.class, () -> util.construct(TestData.class));
    }
  }

  public static class ConstructorExceptionClass {
    public ConstructorExceptionClass() {
      throw new RuntimeException("Constructor failed");
    }
  }

  @Test
  void testConstruct_ThrowsRuntimeException_WhenInvocationTargetExceptionOccurs() {
    assertThrows(RuntimeException.class, () -> util.construct(ConstructorExceptionClass.class));
  }

  @Test
  void testInvokeSetter_UsesDerivedSetterName_WhenReadMethodIsNull() throws IntrospectionException {
    PropertyDescriptor pd = new PropertyDescriptor("x", TestData.class, null, "setX");
    TestData obj = new TestData();

    util.invokeSetter(obj, pd, 99);

    assertEquals(99, obj.getX());
  }

  @Test
  void testInvokeSetter_ThrowsRuntimeException_WhenIllegalArgumentExceptionOccurs()
      throws IntrospectionException {
    PropertyDescriptor pd = new PropertyDescriptor("x", TestData.class);
    TestData obj = new TestData();

    assertThrows(RuntimeException.class, () -> util.invokeSetter(obj, pd, "invalid-type"));
  }

  public static class TestDataWithExceptionSetter {
    public int getX() {
      return 1;
    }

    public void setX(int x) {
      throw new RuntimeException("Setter error");
    }
  }

  @Test
  void testInvokeSetter_ThrowsRuntimeException_WhenReflectiveOperationExceptionOccurs()
      throws IntrospectionException {
    PropertyDescriptor pd = new PropertyDescriptor("x", TestDataWithExceptionSetter.class);
    TestDataWithExceptionSetter obj = new TestDataWithExceptionSetter();

    assertThrows(RuntimeException.class, () -> util.invokeSetter(obj, pd, 42));
  }

  public static class GetterExceptionClass {
    public int getX() {
      throw new RuntimeException("Getter failed");
    }

    public void setX(int x) {}
  }

  @Test
  void testCopy_ThrowsRuntimeException_WhenInvocationTargetExceptionOccurs() {
    assertThrows(RuntimeException.class,
        () -> util.copy(new GetterExceptionClass(), new GetterExceptionClass(), pd -> true));
  }
}
