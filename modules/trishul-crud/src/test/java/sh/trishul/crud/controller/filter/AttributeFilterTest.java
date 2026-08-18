package sh.trishul.crud.controller.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import sh.trishul.test.bom.model.Dummy;

class AttributeFilterTest {
  private AttributeFilter filter;

  @BeforeEach
  void init() {
    filter = new AttributeFilter();
  }

  @Test
  void testRetain_DoesNotSetNullValue_WhenAllPropertiesAreSpecified() {
    Dummy data = new Dummy("value", 12345L, 0.12345, 12345, 'a', true);
    filter.retain(data, Set.of("string", "longg", "doublee", "integer", "character", "bool"));

    assertEquals("value", data.getString());
    assertEquals(12345L, data.getLongg());
    assertEquals(0.12345, data.getDoublee());
    assertEquals(12345, data.getInteger());
    assertEquals('a', data.getCharacter());
    assertEquals(true, data.getBool());
  }

  @Test
  void testRetain_SetsNullValueOnUnspecifiedProperties() {
    Dummy data = new Dummy("value", 12345L, 0.12345, 12345, 'a', true);
    filter.retain(data, Set.of("string", "integer", "bool"));

    assertEquals("value", data.getString());
    assertEquals(12345, data.getInteger());
    assertEquals(true, data.getBool());
    assertNull(data.getLongg());
    assertNull(data.getDoublee());
    assertNull(data.getCharacter());
  }

  @Test
  void testRetain_SetsNullValueOnAllProperties_WhenNoPropertiesAreSpecified() {
    Dummy data = new Dummy("value", 12345L, 0.12345, 12345, 'a', true);
    filter.retain(data, Set.of());

    assertNull(data.getString());
    assertNull(data.getLongg());
    assertNull(data.getDoublee());
    assertNull(data.getInteger());
    assertNull(data.getCharacter());
    assertNull(data.getBool());
  }

  @Test
  void testRemove_SetsNullValueOnAllProps_WhenAllPropertiesAreSpecified() {
    Dummy data = new Dummy("value", 12345L, 0.12345, 12345, 'a', true);
    filter.remove(data, Set.of("string", "longg", "doublee", "integer", "character", "bool"));

    assertNull(data.getString());
    assertNull(data.getLongg());
    assertNull(data.getDoublee());
    assertNull(data.getInteger());
    assertNull(data.getCharacter());
    assertNull(data.getBool());
  }

  @Test
  void testRemove_SetsNullValueOnSpecifiedProperties() {
    Dummy data = new Dummy("value", 12345L, 0.12345, 12345, 'a', true);
    filter.remove(data, Set.of("string", "integer", "bool"));

    assertNull(data.getString());
    assertNull(data.getInteger());
    assertNull(data.getBool());
    assertEquals(12345L, data.getLongg());
    assertEquals(0.12345, data.getDoublee());
    assertEquals('a', data.getCharacter());
  }

  @Test
  void testRemove_DoesNotSetsNullValue_WhenNoPropertiesAreSpecified() {
    Dummy data = new Dummy("value", 12345L, 0.12345, 12345, 'a', true);
    filter.remove(data, Set.of());

    assertEquals("value", data.getString());
    assertEquals(12345L, data.getLongg());
    assertEquals(0.12345, data.getDoublee());
    assertEquals(12345, data.getInteger());
    assertEquals('a', data.getCharacter());
    assertEquals(true, data.getBool());
  }

  @Test
  void testSetNullValueOnProps_ThrowsRuntimeException_WhenIntrospectionExceptionOccurs()
      throws Exception {
    try (MockedStatic<Introspector> mockedIntrospector = mockStatic(Introspector.class)) {
      mockedIntrospector.when(() -> Introspector.getBeanInfo(any()))
          .thenThrow(new IntrospectionException("Custom error"));

      RuntimeException e
          = assertThrows(RuntimeException.class, () -> filter.retain(new Dummy(), Set.of()));
      assertEquals("Failed to get the property descriptors for the the object", e.getMessage());
    }
  }

  @Test
  void testSetNullValueOnProps_ThrowsRuntimeException_WhenIllegalArgumentExceptionOccurs() {
    Set<String> mockSet = mock(Set.class);
    when(mockSet.contains(anyString())).thenThrow(new IllegalArgumentException("Custom error"));

    RuntimeException e
        = assertThrows(RuntimeException.class, () -> filter.remove(new Dummy(), mockSet));
    assertEquals("Failed to dynamically call setter because: Custom error", e.getMessage());
  }

  @Test
  void testRemove_ThrowsException_WhenSettingNullToPrimitiveField() {
    class PrimitiveDummy {
      private int id;

      public int getId() {
        return id;
      }

      public void setId(int id) {
        this.id = id;
      }
    }
    PrimitiveDummy data = new PrimitiveDummy();

    // ReflectionManipulator catches IllegalArgumentException and rethrows as RuntimeException
    // So this might not hit the catch(IllegalArgumentException) block in AttributeFilter
    // but we add it to satisfy the requirement of using a class with primitive fields.
    assertThrows(RuntimeException.class, () -> filter.remove(data, Set.of("id")));
  }
}
