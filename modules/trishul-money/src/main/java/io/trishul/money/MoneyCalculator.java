package io.trishul.money;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.joda.money.Money;

public class MoneyCalculator {
    public static final MoneyCalculator INSTANCE = new MoneyCalculator();

    protected MoneyCalculator() {}

    public Money total(Collection<? extends MoneySupplier> moneySuppliers) {
        Money total = null;
        if (moneySuppliers != null) {
            List<Money> monies =
                    moneySuppliers.stream()
                            .filter(i -> i != null && i.getAmount() != null)
                            .map(i -> i.getAmount())
                            .toList();
            if (monies.size() > 0) {
                total = Money.total(monies);
            }
        }

        return total;
    }

    public Money totalAmount(Collection<Money> monies) {
        Money total = null;

        if (monies != null) {
            monies = monies.stream().filter(Objects::nonNull).toList();
            if (monies.size() > 0) {
                total = Money.total(monies);
            }
        }

        return total;
    }
}
