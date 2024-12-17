package io.trishul.money.tax.rate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaxRateTest {
    private TaxRate taxRate;

    @BeforeEach
    public void init() {
        taxRate = new TaxRate();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(taxRate.getValue());
    }

    @Test
    public void testAllArgConstructor() {
        taxRate = new TaxRate(new BigDecimal("1"));

        assertEquals(new BigDecimal("1"), taxRate.getValue());
    }

    @Test
    public void testGetTaxAmount_ReturnsNull_WhenArgIsNull() {
        taxRate.setValue(new BigDecimal("2"));

        assertNull(taxRate.getTaxAmount(null));
    }

    @Test
    public void testGetTaxAmount_ReturnsTaxAmount_WhenArgIsNotNull() {
        taxRate.setValue(new BigDecimal("2"));

        assertEquals(Money.parse("CAD 200"), taxRate.getTaxAmount(Money.parse("CAD 100")));
    }

    @Test
    public void testIsSet_ReturnsFalse_WhenValueIsNull() {
        assertFalse(taxRate.isSet());
        taxRate.setValue(null);
        assertFalse(taxRate.isSet());
    }

    @Test
    public void testIsSet_ReturnsFalse_WhenValueIsSetToZero() {
        taxRate.setValue(new BigDecimal("0"));
        assertFalse(taxRate.isSet());
    }

    @Test
    public void testIsSet_ReturnsTrue_WhenValueIsNonZero() {
        taxRate.setValue(new BigDecimal("1"));
        assertTrue(taxRate.isSet());

        taxRate.setValue(new BigDecimal("2"));
        assertTrue(taxRate.isSet());

        taxRate.setValue(new BigDecimal("3"));
        assertTrue(taxRate.isSet());
    }

    @Test
    public void testIsSet_Static_ReturnsFalse_WhenArgIsNull() {
        assertFalse(TaxRate.isSet(null));
    }

    @Test
    public void testIsSet_Static_ReturnsFalse_WhenValueIsNull() {
        assertFalse(taxRate.isSet());
        taxRate.setValue(null);
        assertFalse(TaxRate.isSet(taxRate));
    }

    @Test
    public void testIsSet_Static_ReturnsFalse_WhenValueIsSetToZero() {
        taxRate.setValue(new BigDecimal("0"));
        assertFalse(TaxRate.isSet(taxRate));
    }

    @Test
    public void testIsSet_Static_ReturnsTrue_WhenValueIsNonZero() {
        taxRate.setValue(new BigDecimal("1"));
        assertTrue(TaxRate.isSet(taxRate));

        taxRate.setValue(new BigDecimal("2"));
        assertTrue(TaxRate.isSet(taxRate));

        taxRate.setValue(new BigDecimal("3"));
        assertTrue(TaxRate.isSet(taxRate));
    }
}
