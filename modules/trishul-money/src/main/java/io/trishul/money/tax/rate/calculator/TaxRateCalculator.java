package io.trishul.money.tax.rate.calculator;

import org.joda.money.Money;

import io.trishul.money.tax.rate.TaxRate;

public class TaxRateCalculator {
    public static final TaxRateCalculator INSTANCE = new TaxRateCalculator();

    protected TaxRateCalculator() {
    }

    public Money getTaxAmount(TaxRate taxRate, Money amount) {
        Money taxRateAmount = null;

        if (taxRate != null) {
            taxRateAmount = taxRate.getTaxAmount(amount);
        }

        return taxRateAmount;
    }
}
