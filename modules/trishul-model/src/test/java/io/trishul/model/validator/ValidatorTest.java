package io.trishul.model.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.model.base.exception.ValidationException;

class ValidatorTest {
  private Validator validator;

  @BeforeEach
  void init() {
    validator = new Validator();
  }

  @Test
  void testRule_SetsMsgAsError_WhenConditionIsFalse() {
    validator.rule(false, "This is an error message: %s", "TEST");
    assertThrows(ValidationException.class,
        () -> validator.raiseErrors(), "1. This is an error message: TEST");
  }

  @Test
  void testRule_DoesNotSetMsgAsError_WhenConditionIsTrue() {
    validator.rule(true, "This is not an error message: %s", "TEST");
    validator.rule(false, "This is an error message: %s", "TEST");
    validator.rule(true, "This is not an error message: %s", "TEST");

    ValidationException exception = assertThrows(ValidationException.class,
        () -> validator.raiseErrors(), "1. This is an error message: TEST");
  }

  @Test
  void testRaiseErrors_DoesNotThrowException_WhenNoErrorsExist() {
    validator.raiseErrors();
    validator.rule(true, "No exception will be raised because condition is true");
    validator.raiseErrors();
    // Test passes if no exception is thrown
    assertTrue(true, "Method completes without throwing exception");
  }

  @Test
  void testRaiseErrors_ConcatsErrorsIntoNumberedList() {
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
  void testHasErrors_ReturnsTrue_WhenAtleastOneErrorExists() {
    validator.rule(false, "Error");

    assertTrue(validator.hasErrors());
  }

  @Test
  void testHasErrors_ReturnsFalse_WhenNoErrorExists() {
    assertFalse(validator.hasErrors());
  }
}
