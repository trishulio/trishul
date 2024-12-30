package io.trishul.money;

import org.joda.money.Money;

public interface MoneySupplier {
  final String ATTR_AMOUNT = "amount";

  Money getAmount();
}
