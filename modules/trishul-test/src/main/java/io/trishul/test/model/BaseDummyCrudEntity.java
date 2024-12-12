package io.trishul.test.model;

public interface BaseDummyCrudEntity {
    final String ATTR_EXCLUDED_VALUE = "excludedValue";
    final String ATTR_VALUE = "value";

    String getExcludedValue();

    void setExcludedValue(String excludedValue);

    String getValue();

    void setValue(String value);
}
