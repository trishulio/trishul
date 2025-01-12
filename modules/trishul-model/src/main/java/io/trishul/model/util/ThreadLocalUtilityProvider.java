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
    return this.validatorCache.get();
  }

  @Override
  public void setValidator(Validator validator) {
    this.validatorCache.set(validator);
  }
}
