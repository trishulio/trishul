package io.trishul.model.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NullOrNotBlankValidatorTest {
  private NullOrNotBlankValidator validator;

  private ConstraintValidatorContext mockContext;

  @BeforeEach
  void init() {
    validator = new NullOrNotBlankValidator();
    mockContext = mock(ConstraintValidatorContext.class);
  }

  @Test
  void testReturnsTrueWhenNull() {
    boolean actual = validator.isValid(null, mockContext);
    assertTrue(actual);
  }

  @Test
  void testReturnsTrueWhenValidString() {
    boolean actual = validator.isValid("test", mockContext);
    assertTrue(actual);
  }

  @Test
  void testReturnsFalseWhenEmptyString() {
    boolean actual = validator.isValid("", mockContext);
    assertFalse(actual);
  }

  @Test
  void testReturnsFalseWhenEmptyStringWithWhiteSpace() {
    boolean actual = validator.isValid("      ", mockContext);
    assertFalse(actual);
  }
}
