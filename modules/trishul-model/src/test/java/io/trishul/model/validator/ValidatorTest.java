package io.trishul.model.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.trishul.base.types.validator.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidatorTest {
  private Validator validator;

  @BeforeEach
  public void init() {
    validator = new Validator();
  }

  @Test
  public void testRule_SetsMsgAsError_WhenConditionIsFalse() {
    validator.rule(false, "This is an error message: %s", "TEST");
    ValidationException exception = assertThrows(ValidationException.class,
        () -> validator.raiseErrors(), "1. This is an error message: TEST");
  }

  @Test
  public void testRule_DoesNotSetMsgAsError_WhenConditionIsTrue() {
    validator.rule(true, "This is not an error message: %s", "TEST");
    validator.rule(false, "This is an error message: %s", "TEST");
    validator.rule(true, "This is not an error message: %s", "TEST");

    ValidationException exception = assertThrows(ValidationException.class,
        () -> validator.raiseErrors(), "1. This is an error message: TEST");
  }

  @Test
  public void testRaiseErrors_DoesNotThrowException_WhenNoErrorsExist() {
    validator.raiseErrors();
    validator.rule(true, "No exception will be raised because condition is true");
    validator.raiseErrors();
  }

  @Test
  public void testRaiseErrors_ConcatsErrorsIntoNumberedList() {
    validator.rule(false, "This is error A: %s", "TEST");
    validator.rule(true, "This message is ignored: %s", "TEST");
    validator.rule(false, "This is error B: %s", "TEST");
    validator.rule(true, "This message is ignored: %s", "TEST");
    validator.rule(false, "This is error C: %s", "TEST");

    String expected = "" + "1. This is error A: TEST\n" + "2. This is error B: TEST\n"
        + "3. This is error C: TEST\n";
    ValidationException exception
        = assertThrows(ValidationException.class, () -> validator.raiseErrors(), expected);
  }

  @Test
  public void testAssertion_CreatesAndThrowsExceptionObjectWithMsg_WhenConditionIsFalse() {
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      Validator.assertion(false, RuntimeException.class, "This is the error message");
    });
    assertEquals("This is the error message", exception.getMessage());
  }

  @Test
  public void testAssertion_ThrowsSpecifiedExceptionType_WhenConditionIsFalse() {
    RuntimeException exception1 = assertThrows(RuntimeException.class,
        () -> Validator.assertion(false, RuntimeException.class));
    ArrayIndexOutOfBoundsException exception2 = assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> Validator.assertion(false, ArrayIndexOutOfBoundsException.class));
    IndexOutOfBoundsException exception3 = assertThrows(IndexOutOfBoundsException.class,
        () -> Validator.assertion(false, IndexOutOfBoundsException.class));
  }

  @Test
  public void testAssertion_DoesNotThrowException_WhenConditionIsTrue() {
    Validator.assertion(true, RuntimeException.class, "This will never be thrown");
  }

  @Test
  public void testHasErrors_ReturnsTrue_WhenAtleastOneErrorExists() {
    validator.rule(false, "Error");

    assertTrue(validator.hasErrors());
  }

  @Test
  public void testHasErrors_ReturnsFalse_WhenNoErrorExists() {
    assertFalse(validator.hasErrors());
  }
}
