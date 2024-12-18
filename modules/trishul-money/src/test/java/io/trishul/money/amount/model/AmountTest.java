package io.trishul.money.amount.model;

import org.joda.money.Money;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.money.tax.amount.TaxAmount;

public class AmountTest {
    private Amount amount;

    @BeforeEach
    public void init() {
        amount = new Amount();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(amount.getSubTotal());
        assertNull(amount.getTotal());
        assertNull(amount.getTaxAmount());
    }

    @Test
    public void testAllArgConstructor() {
        amount = new Amount(Money.parse("CAD 100"), new TaxAmount(Money.parse("CAD 10")));

        assertEquals(Money.parse("CAD 110"), amount.getTotal());
        assertEquals(Money.parse("CAD 100"), amount.getSubTotal());
        assertEquals(new TaxAmount(Money.parse("CAD 10")), amount.getTaxAmount());
    }
}
