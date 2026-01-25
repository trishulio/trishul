package io.trishul.model.base.pojo.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import io.trishul.model.base.exception.IllegalArgException;
import org.junit.jupiter.api.Test;

class IllegalArgExceptionTest {
  @Test
  void testAssertion_DoesNotThrow_WhenConditionIsTrue() {
    // Should not throw any exception
    IllegalArgException.assertion(true, "Test message");
  }

  @Test
  void testAssertion_ThrowsIllegalArgumentException_WhenConditionIsFalse() {
    String testMessage = "Invalid argument provided";

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> IllegalArgException.assertion(false, testMessage));

    assertEquals(testMessage, exception.getMessage());
  }

  @Test
  void testAssertion_ThrowsIllegalArgumentException_WithNullMessage_WhenConditionIsFalse() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> IllegalArgException.assertion(false, null));

    assertEquals(null, exception.getMessage());
  }

  @Test
  void testAssertion_ThrowsIllegalArgumentException_WithEmptyMessage_WhenConditionIsFalse() {
    String emptyMessage = "";

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> IllegalArgException.assertion(false, emptyMessage));

    assertEquals(emptyMessage, exception.getMessage());
  }
}
