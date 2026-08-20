package sh.trishul.money.currency.model;

public interface BaseCurrency<T extends BaseCurrency<T>> {
  Integer getNumericCode();

  T setNumericCode(Integer numericCode);

  String getCode();

  T setCode(String code);
}
