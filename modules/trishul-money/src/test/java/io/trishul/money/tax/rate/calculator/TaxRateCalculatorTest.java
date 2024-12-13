package io.trishul.money.tax.rate.calculator;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.money.tax.rate.TaxRate;

public class TaxRateCalculatorTest {
    private TaxRateCalculator calculator;

    @BeforeEach
    public void init() {
        calculator = TaxRateCalculator.INSTANCE;
    }

    @Test
    public void testGetTaxAmount_ReturnsNull_WhenTaxRateIsNull() {
        assertNull(calculator.getTaxAmount(null, Money.parse("CAD 0")));
    }

    @Test
    public void testGetTaxAmount_ReturnsTaxAmount_WhenArgIsNotNull() {
        Money taxAmount = calculator.getTaxAmount(new TaxRate(new BigDecimal("2")), Money.parse("CAD 100"));

        assertEquals(Money.parse("CAD 200"), taxAmount);
    }
}
