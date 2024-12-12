package io.company.brewcraft.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.TaxRateDto;
import io.company.brewcraft.model.TaxRate;
import io.company.brewcraft.service.mapper.TaxRateMapper;

public class TaxRateMapperTest {

    private TaxRateMapper mapper;

    @BeforeEach
    public void init() {
        mapper = TaxRateMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsNull_WhenArgIsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenArgIsNotNull() {
        TaxRate rate = new TaxRate(new BigDecimal("10"));

        TaxRateDto dto = mapper.toDto(rate);

        TaxRateDto expected = new TaxRateDto(new BigDecimal("10"));
        assertEquals(expected, dto);
    }

    @Test
    public void toFromDto_ReturnsNull_WhenArgIsNull() {
        assertNull(mapper.fromDto(null));
    }

    @Test
    public void testFromDto_ReturnsPojo_WhenArgIsNotNull() {
        TaxRateDto dto = new TaxRateDto(new BigDecimal("10"));

        TaxRate rate = mapper.fromDto(dto);

        TaxRate expected = new TaxRate(new BigDecimal("10"));
        assertEquals(expected, rate);
    }
}
