package io.trishul.model.validator;


public interface UtilityProvider {
    Validator getValidator();

    void setValidator(Validator validator);
}
