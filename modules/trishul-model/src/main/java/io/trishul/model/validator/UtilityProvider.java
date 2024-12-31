package io.trishul.model.validator;

// TODO: Get rid of request based validator. Validator should be created by the function using it.
public interface UtilityProvider {
  Validator getValidator();

  void setValidator(Validator validator);
}
