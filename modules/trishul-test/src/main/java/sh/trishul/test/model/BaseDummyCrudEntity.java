package sh.trishul.test.model;

public interface BaseDummyCrudEntity<T extends BaseDummyCrudEntity<T>> {
  String ATTR_EXCLUDED_VALUE = "excludedValue";
  String ATTR_VALUE = "value";

  String getExcludedValue();

  T setExcludedValue(String excludedValue);

  String getValue();

  T setValue(String value);
}
