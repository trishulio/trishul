package io.trishul.commodity;

import io.trishul.commodity.model.Commodity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.joda.money.Money;

public class CostCalculator {
    public static final CostCalculator INSTANCE = new CostCalculator();

    protected CostCalculator() {}

    public Money getCost(Commodity commodity) {
        Money amount = null;

        BigDecimal qty = null;
        Money price = null;

        if (commodity != null) {
            qty =
                    commodity.getQuantity() != null
                            ? (BigDecimal) commodity.getQuantity().getValue()
                            : null;
            price = commodity.getPrice() != null ? commodity.getPrice() : null;
        }

        if (qty != null && price != null) {
            amount = price.multipliedBy(qty, RoundingMode.UNNECESSARY);
        }

        return amount;
    }
}
