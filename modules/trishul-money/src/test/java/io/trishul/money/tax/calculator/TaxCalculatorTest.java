package io.company.brewcraft.model;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaxCalculatorTest {
    private TaxCalculator calculator;

    @BeforeEach
    public void init() {
        calculator = TaxCalculator.INSTANCE;
    }

    @Test
    public void testGetTaxAmount_ReturnsNull_WhenTaxIsNull() {
        assertNull(calculator.getTaxAmount(null, Money.parse("CAD 10")));
    }

    @Test
    public void testGetTaxAmount_ReturnsNull_WhenAmountIsNull() {
        assertNull(calculator.getTaxAmount(new Tax(), null));
    }

    @Test
    public void testGetTaxAmount_ReturnsTaxAmount_WhenTaxAndAmountIsNotNull() {
        Tax tax = new Tax(new TaxRate(new BigDecimal("1")), new TaxRate(new BigDecimal("2")));
        Money money = Money.parse("CAD 10");

        TaxAmount taxAmount = calculator.getTaxAmount(tax, money);

        TaxAmount expected = new TaxAmount(Money.parse("CAD 10"), Money.parse("CAD 20"));
        assertEquals(expected, taxAmount);
    }

    @Test
    public void testTotal_ReturnsNull_WhenTaxAmountsAreNull() {
        assertNull(calculator.getTaxAmountTotal(null));
    }

    @Test
    public void testTotal_ReturnsEmptyTaxAmount_WhenTaxAmountsAreEmpty() {
        assertEquals(new TaxAmount(), calculator.getTaxAmountTotal(new ArrayList<>()));
    }

    @Test
    public void testTotal_ReturnsTaxAmountWithTotals_WhenTaxAmountsAreNotEmpty() {
        List<TaxAmount> amounts = new ArrayList<>() {
            private static final long serialVersionUID = 1L;
            {
                add(null);
                add(null);
                add(new TaxAmount());
                add(null);
                add(new TaxAmount(Money.parse("CAD 10")));
                add(new TaxAmount(null, Money.parse("CAD 20")));
                add(new TaxAmount(Money.parse("CAD 35"), null));
                add(new TaxAmount(Money.parse("CAD 40"), Money.parse("CAD 50")));
                add(null);
                add(new TaxAmount());
            }
        };

        TaxAmount total = calculator.getTaxAmountTotal(amounts);

        TaxAmount expected = new TaxAmount(Money.parse("CAD 75"), Money.parse("CAD 70"), Money.parse("CAD 10"));
        assertEquals(expected, total);
    }
}
