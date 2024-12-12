package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.TaxDto;
import io.company.brewcraft.dto.TaxRateDto;
import io.company.brewcraft.model.Tax;
import io.company.brewcraft.model.TaxRate;

public class TaxMapperTest {
    private TaxMapper mapper;

    @BeforeEach
    public void init() {
        mapper = TaxMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnsPojo_WhenDtoIsNotNull() {

        TaxDto dto = new TaxDto(new TaxRateDto(new BigDecimal("1")), new TaxRateDto(new BigDecimal("2")));
        Tax tax = mapper.fromDto(dto);

        Tax expected = new Tax(new TaxRate(new BigDecimal("1")), new TaxRate(new BigDecimal("2")));
        assertEquals(expected, tax);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromDto(null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        Tax tax = new Tax(new TaxRate(new BigDecimal("1")), new TaxRate(new BigDecimal("2")));

        TaxDto dto = mapper.toDto(tax);

        TaxDto expected = new TaxDto(new TaxRateDto(new BigDecimal("1")), new TaxRateDto(new BigDecimal("2")));
        assertEquals(expected, dto);
    }

    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.toDto(null));
    }
}
