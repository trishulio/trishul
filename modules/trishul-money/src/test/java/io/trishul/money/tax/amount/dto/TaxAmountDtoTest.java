package io.trishul.money.tax.amount.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.trishul.money.dto.MoneyDto;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaxAmountDtoTest {
    private TaxAmountDto taxAmount;

    @BeforeEach
    public void init() {
        taxAmount = new TaxAmountDto();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(taxAmount.getPstAmount());
        assertNull(taxAmount.getGstAmount());
        assertNull(taxAmount.getHstAmount());
    }

    @Test
    public void testAllArgConstructor() {
        taxAmount =
                new TaxAmountDto(
                        new MoneyDto("CAD", new BigDecimal("10.00")),
                        new MoneyDto("CAD", new BigDecimal("20.00")),
                        new MoneyDto("CAD", new BigDecimal("30.00")),
                        new MoneyDto("CAD", new BigDecimal("70.00")));

        assertEquals(new MoneyDto("CAD", new BigDecimal("10.00")), taxAmount.getPstAmount());
        assertEquals(new MoneyDto("CAD", new BigDecimal("20.00")), taxAmount.getGstAmount());
        assertEquals(new MoneyDto("CAD", new BigDecimal("30.00")), taxAmount.getHstAmount());
        assertEquals(new MoneyDto("CAD", new BigDecimal("70.00")), taxAmount.getTotalTaxAmount());
    }

    @Test
    public void testGstPstConstructor() {
        taxAmount =
                new TaxAmountDto(
                        new MoneyDto("CAD", new BigDecimal("10.00")),
                        new MoneyDto("CAD", new BigDecimal("20.00")),
                        new MoneyDto("CAD", new BigDecimal("30.00")));

        assertEquals(new MoneyDto("CAD", new BigDecimal("10.00")), taxAmount.getPstAmount());
        assertEquals(new MoneyDto("CAD", new BigDecimal("20.00")), taxAmount.getGstAmount());
        assertEquals(new MoneyDto("CAD", new BigDecimal("30.00")), taxAmount.getTotalTaxAmount());
    }

    @Test
    public void testHstConstructor() {
        taxAmount =
                new TaxAmountDto(
                        new MoneyDto("CAD", new BigDecimal("10.00")),
                        new MoneyDto("CAD", new BigDecimal("10.00")));

        assertEquals(new MoneyDto("CAD", new BigDecimal("10.00")), taxAmount.getHstAmount());
        assertEquals(new MoneyDto("CAD", new BigDecimal("10.00")), taxAmount.getTotalTaxAmount());
    }

    @Test
    public void testGetSetPstAmount() {
        taxAmount.setPstAmount(new MoneyDto("CAD", new BigDecimal("100.00")));

        assertEquals(new MoneyDto("CAD", new BigDecimal("100.00")), taxAmount.getPstAmount());
    }

    @Test
    public void testGetSetGstAmount() {
        taxAmount.setGstAmount(new MoneyDto("CAD", new BigDecimal("100.00")));

        assertEquals(new MoneyDto("CAD", new BigDecimal("100.00")), taxAmount.getGstAmount());
    }

    @Test
    public void testGetSetHstAmount() {
        taxAmount.setHstAmount(new MoneyDto("CAD", new BigDecimal("100.00")));

        assertEquals(new MoneyDto("CAD", new BigDecimal("100.00")), taxAmount.getHstAmount());
    }

    @Test
    public void testGetTotalTaxAmountDto_ReturnsTotalMoney() {
        taxAmount = new TaxAmountDto();
        taxAmount.setTotalTaxAmount(new MoneyDto("CAD", new BigDecimal("60")));

        assertEquals(new MoneyDto("CAD", new BigDecimal("60")), taxAmount.getTotalTaxAmount());
    }
}
