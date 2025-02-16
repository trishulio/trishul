package io.trishul.model.util;

import io.trishul.model.validator.UtilityProvider;
import io.trishul.model.validator.Validator;

public class ThreadLocalUtilityProvider implements UtilityProvider {
  private final InheritableThreadLocal<Validator> validatorCache;

  public ThreadLocalUtilityProvider() {
    this.validatorCache = new InheritableThreadLocal<>();
  }

  @Override
  public Validator getValidator() {
    Validator validator = this.validatorCache.get();
    if (validator == null) {
      validator = new Validator();
      this.validatorCache.set(validator);
    }

    return validator;
  }

  @Override
  public void setValidator(Validator validator) {
    this.validatorCache.set(validator);
  }
}
