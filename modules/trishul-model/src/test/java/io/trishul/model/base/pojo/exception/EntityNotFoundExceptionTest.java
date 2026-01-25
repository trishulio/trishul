package io.trishul.model.base.pojo.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import io.trishul.model.base.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;

class EntityNotFoundExceptionTest {
  @Test
  void testConstructor_StringString_SetsId() {
    EntityNotFoundException exception = new EntityNotFoundException("EntityTest", "idTest");
    assertEquals("EntityTest not found with id: idTest", exception.getMessage());
  }

  @Test
  void testConstructor_StringObject_SetsNullIdWhenObjectIsNull() {
    EntityNotFoundException exception = new EntityNotFoundException("EntityTest", null);
    assertEquals("EntityTest not found with id: null", exception.getMessage());
  }

  @Test
  void testConstructor_StringObject_SetsIdStringWhenObjectIsNotNull() {
    class Id {
      String str;

      public Id(String str) {
        this.str = str;
      }

      @Override
      public String toString() {
        return str;
      }
    }
    EntityNotFoundException exception = new EntityNotFoundException("EntityTest", new Id("ID"));
    assertEquals("EntityTest not found with id: ID", exception.getMessage());
  }

  @Test
  void testConstructor_StringStringString() {
    EntityNotFoundException exception = new EntityNotFoundException("EntityTest", "FIELD", "ID");
    assertEquals("EntityTest not found with FIELD: ID", exception.getMessage());
  }

  @Test
  void testAssertion_DoesNotThrow_WhenConditionIsTrue() {
    // Should not throw any exception
    EntityNotFoundException.assertion(true, "EntityTest", "id", "123");
  }

  @Test
  void testAssertion_ThrowsException_WhenConditionIsFalse() {
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
        () -> EntityNotFoundException.assertion(false, "EntityTest", "id", "123"));

    assertEquals("EntityTest not found with id: 123", exception.getMessage());
  }

  @Test
  void testAssertion_ThrowsExceptionWithCustomField_WhenConditionIsFalse() {
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
        () -> EntityNotFoundException.assertion(false, "User", "email", "test@example.com"));

    assertEquals("User not found with email: test@example.com", exception.getMessage());
  }
}
