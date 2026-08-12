package sh.trishul.model.base.pojo.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import sh.trishul.model.base.exception.IllegalArgException;

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
