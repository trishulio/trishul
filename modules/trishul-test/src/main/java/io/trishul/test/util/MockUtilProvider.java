package io.trishul.test.util;

import io.trishul.model.validator.UtilityProvider;
import io.trishul.model.validator.Validator;

public class MockUtilProvider implements UtilityProvider {
  @Override
  public final void setValidator(Validator validator) {}

  @Override
  public Validator getValidator() {
    return new Validator();
  }
}
