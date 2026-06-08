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
  void testAssertion_ThrowsIllegalArgException_WhenConditionIsFalse() {
    String testMessage = "Invalid argument provided";

    IllegalArgException exception = assertThrows(IllegalArgException.class,
        () -> IllegalArgException.assertion(false, testMessage));

    assertEquals(testMessage, exception.getMessage());
  }

  @Test
  void testConstructor() {
    String message = "test message";
    IllegalArgException exception = new IllegalArgException(message);
    assertEquals(message, exception.getMessage());
  }
}
