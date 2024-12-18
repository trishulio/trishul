package io.trishul.test.util;

import io.trishul.model.validator.UtilityProvider;
import io.trishul.model.validator.Validator;

public class MockUtilProvider implements UtilityProvider {
    private final Validator validator;

    public MockUtilProvider(Validator validator) {
        this.validator = validator;
    }

    public MockUtilProvider() {
        this(new Validator());
    }

    @Override
    public void setValidator(Validator validator) {}

    @Override
    public Validator getValidator() {
        return validator;
    }
}
