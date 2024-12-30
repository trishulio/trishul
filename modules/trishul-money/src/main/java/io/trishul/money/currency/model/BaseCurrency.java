package io.trishul.money.currency.model;

public interface BaseCurrency<T extends BaseCurrency<T>> {
  public Integer getNumericCode();

  public T setNumericCode(Integer numericCode);

  public String getCode();

  public T setCode(String code);
}
