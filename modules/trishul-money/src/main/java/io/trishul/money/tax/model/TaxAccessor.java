package io.trishul.money.tax.model;

public interface TaxAccessor<T extends TaxAccessor<T>> extends TaxSupplier {
  T setTax(Tax tax);
}
