package io.trishul.money.tax.dto;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.money.tax.rate.dto.TaxRateDto;

public class TaxDtoTest {
    private TaxDto tax;

    @BeforeEach
    public void init() {
        tax = new TaxDto();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(tax.getPstRate());
        assertNull(tax.getGstRate());
        assertNull(tax.getHstRate());
    }

    @Test
    public void testPstGstConstructor() {
        tax = new TaxDto(new TaxRateDto(new BigDecimal("1")), new TaxRateDto(new BigDecimal("2")));

        assertEquals(new TaxRateDto(new BigDecimal("1")), tax.getPstRate());
        assertEquals(new TaxRateDto(new BigDecimal("2")), tax.getGstRate());
        assertNull(tax.getHstRate());
    }

    @Test
    public void testHstConstructor() {
        tax = new TaxDto(new TaxRateDto(new BigDecimal("1")));

        assertEquals(new TaxRateDto(new BigDecimal("1")), tax.getHstRate());
        assertNull(tax.getPstRate());
        assertNull(tax.getGstRate());
    }

    @Test
    public void testGetSetGstRate() {
        tax.setGstRate(new TaxRateDto(new BigDecimal("1")));
        assertEquals(new TaxRateDto(new BigDecimal("1")), tax.getGstRate());
    }

    @Test
    public void testGetSetPstRate() {
        tax.setPstRate(new TaxRateDto(new BigDecimal("1")));
        assertEquals(new TaxRateDto(new BigDecimal("1")), tax.getPstRate());
    }

    @Test
    public void testGetSetHstRate() {
        tax.setHstRate(new TaxRateDto(new BigDecimal("1")));
        assertEquals(new TaxRateDto(new BigDecimal("1")), tax.getHstRate());
    }
}
