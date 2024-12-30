package io.trishul.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NullOrNotBlankValidator implements ConstraintValidator<NullOrNotBlank, String> {
  @Override
  public void initialize(NullOrNotBlank parameters) {
    // Do Nothing
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value == null || value.trim().length() > 0;
  }
}
