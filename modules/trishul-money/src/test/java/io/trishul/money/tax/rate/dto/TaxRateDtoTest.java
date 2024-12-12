package io.company.brewcraft.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaxRateDtoTest {
    private TaxRateDto taxRate;

    @BeforeEach
    public void init() {
        taxRate = new TaxRateDto();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(taxRate.getValue());
    }

    @Test
    public void testAllArgConstructor() {
        taxRate = new TaxRateDto(new BigDecimal("1"));

        assertEquals(new BigDecimal("1"), taxRate.getValue());
    }
}
