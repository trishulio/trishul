package io.trishul.model.base.pojo.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import io.trishul.model.base.exception.ValidationException;

public class ValidationExceptionTest {

  @Test
  public void testConstructor_SetsMessage() {
    String message = "Test validation error";
    ValidationException exception = new ValidationException(message);

    assertEquals(message, exception.getMessage());
  }

  @Test
  public void testAssertion_DoesNotThrow_WhenConditionIsTrue() {
    // Should not throw any exception
    ValidationException.assertion(true, "This should not throw");
  }

  @Test
  public void testAssertion_ThrowsValidationException_WhenConditionIsFalse() {
    String expectedMessage = "Validation failed";

    ValidationException exception = assertThrows(ValidationException.class,
        () -> ValidationException.assertion(false, expectedMessage));

    assertEquals(expectedMessage, exception.getMessage());
  }

  @Test
  public void testAssertion_ThrowsValidationExceptionWithCorrectMessage_WhenConditionIsFalse() {
    String customMessage = "Custom validation error message";

    ValidationException exception = assertThrows(ValidationException.class,
        () -> ValidationException.assertion(false, customMessage));

    assertEquals(customMessage, exception.getMessage());
  }

  @Test
  public void testAssertion_WorksWithComplexConditions() {
    int value = 5;
    String fieldName = "age";

    // Should not throw when condition is true
    ValidationException.assertion(value > 0, fieldName + " must be positive");

    // Should throw when condition is false
    ValidationException exception = assertThrows(ValidationException.class,
        () -> ValidationException.assertion(value < 0, fieldName + " must be positive"));

    assertEquals("age must be positive", exception.getMessage());
  }

  @Test
  public void testAssertion_WorksWithNullMessage() {
    ValidationException exception
        = assertThrows(ValidationException.class, () -> ValidationException.assertion(false, null));

    assertEquals(null, exception.getMessage());
  }

  @Test
  public void testAssertion_WorksWithEmptyMessage() {
    String emptyMessage = "";

    ValidationException exception = assertThrows(ValidationException.class,
        () -> ValidationException.assertion(false, emptyMessage));

    assertEquals(emptyMessage, exception.getMessage());
  }
}
