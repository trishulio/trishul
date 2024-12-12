package io.trishul.model.util;

import io.trishul.model.validator.Validator;

public interface UtilityProvider {
    Validator getValidator();

    void setValidator(Validator validator);
}
