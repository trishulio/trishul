package sh.trishul.money;

import org.joda.money.Money;

public interface MoneySupplier {
  String ATTR_AMOUNT = "amount";

  Money getAmount();
}
