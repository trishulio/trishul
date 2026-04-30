package io.trishul.model.base.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class BaseModelTest {

  // Test implementation of BaseModel - must be public for ReflectionManipulator access
  public static class TestModel extends BaseModel {
    private String name;
    private Integer value;

    public TestModel() {}

    public TestModel(String name, Integer value) {
      this.name = name;
      this.value = value;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Integer getValue() {
      return value;
    }

    public void setValue(Integer value) {
      this.value = value;
    }
  }

  @Test
  void testOuterJoin_CopiesNonNullProperties() {
    TestModel target = new TestModel("original", 10);
    TestModel source = new TestModel("updated", null);

    target.outerJoin(source);

    assertEquals("updated", target.getName());
    assertEquals(10, target.getValue()); // null values not copied
  }

  @Test
  void testOuterJoin_DoesNotCopyNullProperties() {
    TestModel target = new TestModel("original", 10);
    TestModel source = new TestModel(null, 20);

    target.outerJoin(source);

    assertEquals("original", target.getName()); // null not copied
    assertEquals(20, target.getValue());
  }

  @Test
  void testOuterJoinWithInclude_OnlyCopiesIncludedProperties() {
    TestModel target = new TestModel("original", 10);
    TestModel source = new TestModel("updated", 20);

    target.outerJoin(source, Set.of("name"));

    assertEquals("updated", target.getName());
    assertEquals(10, target.getValue()); // not in include set
  }

  @Test
  void testCopyToNullFields_CopiesOnlyToNullFields() {
    TestModel target = new TestModel("original", null);
    TestModel source = new TestModel("source", 20);

    target.copyToNullFields(source);

    assertEquals("original", target.getName()); // not null, not copied
    assertEquals(20, target.getValue()); // was null, copied from source
  }

  @Test
  void testOverride_CopiesAllProperties() {
    TestModel target = new TestModel("original", 10);
    TestModel source = new TestModel("updated", 20);

    target.override(source);

    assertEquals("updated", target.getName());
    assertEquals(20, target.getValue());
  }

  @Test
  void testOverride_CopiesNullProperties() {
    TestModel target = new TestModel("original", 10);
    TestModel source = new TestModel(null, null);

    target.override(source);

    assertEquals(null, target.getName());
    assertEquals(null, target.getValue());
  }

  @Test
  void testOverrideWithInclude_OnlyCopiesIncludedProperties() {
    TestModel target = new TestModel("original", 10);
    TestModel source = new TestModel("updated", 20);

    target.override(source, Set.of("value"));

    assertEquals("original", target.getName()); // not in include set
    assertEquals(20, target.getValue());
  }

  @Test
  void testDeepClone_CreatesNewInstance() {
    TestModel original = new TestModel("test", 42);

    TestModel clone = original.deepClone();

    assertNotSame(original, clone);
  }

  @Test
  void testDeepClone_CopiesAllProperties() {
    TestModel original = new TestModel("test", 42);

    TestModel clone = original.deepClone();

    assertEquals(original.getName(), clone.getName());
    assertEquals(original.getValue(), clone.getValue());
  }

  @Test
  void testDeepClone_CreatesIndependentCopy() {
    TestModel original = new TestModel("test", 42);

    TestModel clone = original.deepClone();
    clone.setName("modified");
    clone.setValue(100);

    assertEquals("test", original.getName());
    assertEquals(42, original.getValue());
  }

  @Test
  void testEquals_ReturnsTrueForSameObject() {
    TestModel model = new TestModel("test", 42);

    assertTrue(model.equals(model));
  }

  @Test
  void testEquals_ReturnsTrueForEqualObjects() {
    TestModel model1 = new TestModel("test", 42);
    TestModel model2 = new TestModel("test", 42);

    assertTrue(model1.equals(model2));
  }

  @Test
  void testEquals_ReturnsFalseForDifferentObjects() {
    TestModel model1 = new TestModel("test1", 42);
    TestModel model2 = new TestModel("test2", 42);

    assertFalse(model1.equals(model2));
  }

  @Test
  void testEquals_ReturnsFalseForNull() {
    TestModel model = new TestModel("test", 42);

    assertFalse(model.equals(null));
  }

  @Test
  void testHashCode_ReturnsSameValueForEqualObjects() {
    TestModel model1 = new TestModel("test", 42);
    TestModel model2 = new TestModel("test", 42);

    assertEquals(model1.hashCode(), model2.hashCode());
  }

  @Test
  void testHashCode_ReturnsDifferentValueForDifferentObjects() {
    TestModel model1 = new TestModel("test1", 42);
    TestModel model2 = new TestModel("test2", 42);

    assertNotEquals(model1.hashCode(), model2.hashCode());
  }

  @Test
  void testToString_ReturnsJsonRepresentation() {
    TestModel model = new TestModel("test", 42);

    String json = model.toString();

    assertNotNull(json);
    assertTrue(json.contains("test"));
    assertTrue(json.contains("42"));
  }

  @Test
  void testFromString_CreatesObjectFromJson() {
    String json = "{\"name\":\"test\",\"value\":42}";

    TestModel model = BaseModel.fromString(json, TestModel.class);

    assertEquals("test", model.getName());
    assertEquals(42, model.getValue());
  }

  @Test
  void testFromString_HandlesNullValues() {
    String json = "{\"name\":null,\"value\":null}";

    TestModel model = BaseModel.fromString(json, TestModel.class);

    assertEquals(null, model.getName());
    assertEquals(null, model.getValue());
  }
}
