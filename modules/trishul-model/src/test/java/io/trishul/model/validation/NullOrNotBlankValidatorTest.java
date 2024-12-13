package io.trishul.model.validation;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

public class NullOrNotBlankValidatorTest {
    private NullOrNotBlankValidator validator;

    private ConstraintValidatorContext mockContext;

    @BeforeEach
    public void init() {
        validator = new NullOrNotBlankValidator();
        mockContext = mock(ConstraintValidatorContext.class);
    }

    @Test
    public void testReturnsTrueWhenNull() {
        boolean actual = validator.isValid(null, mockContext);
        assertTrue(actual);
    }

    @Test
    public void testReturnsTrueWhenValidString() {
        boolean actual = validator.isValid("test", mockContext);
        assertTrue(actual);
    }

    @Test
    public void testReturnsFalseWhenEmptyString() {
        boolean actual = validator.isValid("", mockContext);
        assertFalse(actual);
    }

    @Test
    public void testReturnsFalseWhenEmptyStringWithWhiteSpace() {
        boolean actual = validator.isValid("      ", mockContext);
        assertFalse(actual);
    }
}
