package io.trishul.test.model;

public interface BaseDummyCrudEntity<T extends BaseDummyCrudEntity<T>> {
  final String ATTR_EXCLUDED_VALUE = "excludedValue";
  final String ATTR_VALUE = "value";

  String getExcludedValue();

  T setExcludedValue(String excludedValue);

  String getValue();

  T setValue(String value);
}
