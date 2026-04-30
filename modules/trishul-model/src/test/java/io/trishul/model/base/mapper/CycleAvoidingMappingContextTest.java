package io.trishul.model.base.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CycleAvoidingMappingContextTest {

  private CycleAvoidingMappingContext context;

  @BeforeEach
  void setUp() {
    context = new CycleAvoidingMappingContext();
  }

  @Test
  void testConstructor_CreatesInstance() {
    assertNotNull(context);
  }

  @Test
  void testGetMappedInstance_ReturnsNull_WhenSourceNotStored() {
    Object source = new Object();

    Object result = context.getMappedInstance(source, Object.class);

    assertNull(result);
  }

  @Test
  void testStoreMappedInstance_StoresMapping() {
    Object source = new Object();
    Object target = new Object();

    context.storeMappedInstance(source, target);

    Object result = context.getMappedInstance(source, Object.class);
    assertEquals(target, result);
  }

  @Test
  void testGetMappedInstance_ReturnsMappedInstance_WhenSourceWasStored() {
    String source = "source";
    String target = "target";

    context.storeMappedInstance(source, target);

    String result = context.getMappedInstance(source, String.class);
    assertEquals(target, result);
  }

  @Test
  void testStoreMappedInstance_OverwritesPreviousMapping() {
    Object source = new Object();
    Object target1 = new Object();
    Object target2 = new Object();

    context.storeMappedInstance(source, target1);
    context.storeMappedInstance(source, target2);

    Object result = context.getMappedInstance(source, Object.class);
    assertEquals(target2, result);
  }

  @Test
  void testGetMappedInstance_UsesIdentityEquality() {
    String source1 = new String("test");
    String source2 = new String("test");
    Object target = new Object();

    // source1 and source2 are equal but not same reference
    context.storeMappedInstance(source1, target);

    // Should return null because source2 is a different object (identity check)
    Object result = context.getMappedInstance(source2, Object.class);
    assertNull(result);

    // Should return target for same reference
    Object result2 = context.getMappedInstance(source1, Object.class);
    assertEquals(target, result2);
  }

  @Test
  void testGetMappedInstance_HandlesMultipleMappings() {
    Object source1 = new Object();
    Object source2 = new Object();
    Object target1 = new Object();
    Object target2 = new Object();

    context.storeMappedInstance(source1, target1);
    context.storeMappedInstance(source2, target2);

    assertEquals(target1, context.getMappedInstance(source1, Object.class));
    assertEquals(target2, context.getMappedInstance(source2, Object.class));
  }
}
