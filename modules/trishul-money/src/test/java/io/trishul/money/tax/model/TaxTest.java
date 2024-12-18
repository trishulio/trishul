package io.trishul.money.tax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.trishul.money.tax.rate.TaxRate;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaxTest {
    private Tax tax;

    @BeforeEach
    public void init() {
        tax = new Tax();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(tax.getPstRate());
        assertNull(tax.getGstRate());
        assertNull(tax.getHstRate());
    }

    @Test
    public void testPstGstConstructor() {
        tax = new Tax(new TaxRate(new BigDecimal("1")), new TaxRate(new BigDecimal("2")));

        assertEquals(new TaxRate(new BigDecimal("1")), tax.getPstRate());
        assertEquals(new TaxRate(new BigDecimal("2")), tax.getGstRate());
        assertNull(tax.getHstRate());
    }

    @Test
    public void testHstConstructor() {
        tax = new Tax(new TaxRate(new BigDecimal("1")));

        assertEquals(new TaxRate(new BigDecimal("1")), tax.getHstRate());
        assertNull(tax.getPstRate());
        assertNull(tax.getGstRate());
    }

    @Test
    public void testGetSetGstRate() {
        tax.setGstRate(new TaxRate(new BigDecimal("1")));
        assertEquals(new TaxRate(new BigDecimal("1")), tax.getGstRate());
    }

    @Test
    public void testSetGstRate_ThrowException_WhenHstIsPresent() {
        tax.setHstRate(new TaxRate(new BigDecimal("1")));

        tax.setGstRate(null);
        tax.setGstRate(new TaxRate(new BigDecimal("0.00")));
        assertThrows(
                IllegalArgumentException.class,
                () -> tax.setGstRate(new TaxRate(new BigDecimal("2"))));
    }

    @Test
    public void testGetSetPstRate() {
        tax.setPstRate(new TaxRate(new BigDecimal("1")));
        assertEquals(new TaxRate(new BigDecimal("1")), tax.getPstRate());
    }

    @Test
    public void testSetPstRate_ThrowException_WhenHstIsPresent() {
        tax.setHstRate(new TaxRate(new BigDecimal("1")));

        tax.setPstRate(null);
        tax.setGstRate(new TaxRate(new BigDecimal("0.00")));
        assertThrows(
                IllegalArgumentException.class,
                () -> tax.setPstRate(new TaxRate(new BigDecimal("2"))));
    }

    @Test
    public void testGetSetHstRate() {
        tax.setHstRate(new TaxRate(new BigDecimal("1")));
        assertEquals(new TaxRate(new BigDecimal("1")), tax.getHstRate());
    }

    @Test
    public void testSetHstRate_ThrowException_WhenPstIsPresent() {
        tax.setPstRate(new TaxRate(new BigDecimal("1")));

        tax.setHstRate(null);
        tax.setGstRate(new TaxRate(new BigDecimal("0.00")));
        assertThrows(
                IllegalArgumentException.class,
                () -> tax.setHstRate(new TaxRate(new BigDecimal("2"))));
    }

    @Test
    public void testSetHstRate_ThrowException_WhenGstIsPresent() {
        tax.setGstRate(new TaxRate(new BigDecimal("1")));

        tax.setHstRate(null);
        tax.setHstRate(new TaxRate(new BigDecimal("0.00")));
        assertThrows(
                IllegalArgumentException.class,
                () -> tax.setHstRate(new TaxRate(new BigDecimal("2"))));
    }
}
