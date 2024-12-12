package io.company.brewcraft.service.mapper;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AmountDto;
import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.dto.TaxAmountDto;
import io.company.brewcraft.model.Amount;
import io.company.brewcraft.model.TaxAmount;

public class AmountMapperTest {

    private AmountMapper mapper;

    @BeforeEach
    public void init() {
        mapper = AmountMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsNull_WhenArgIsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenArgIsNotNull() {
        Amount amount = new Amount(Money.parse("CAD 100"), new TaxAmount(Money.parse("CAD 10")));

        AmountDto dto = mapper.toDto(amount);

        AmountDto expected = new AmountDto(new MoneyDto("CAD", new BigDecimal("110.00")), new MoneyDto("CAD", new BigDecimal("100.00")), new TaxAmountDto(new MoneyDto("CAD", new BigDecimal("10.00")), new MoneyDto("CAD", new BigDecimal("10.00"))));
        assertEquals(expected, dto);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenArgIsNull() {
        assertNull(mapper.fromDto(null));
    }

    @Test
    public void testFromDto_ReturnsPojo_WhenArgIsNotNull() {
        AmountDto dto = new AmountDto(new MoneyDto("CAD", new BigDecimal("110.00")), new MoneyDto("CAD", new BigDecimal("100.00")), new TaxAmountDto(new MoneyDto("CAD", new BigDecimal("10.00")), new MoneyDto("CAD", new BigDecimal("10.00"))));

        Amount amount = mapper.fromDto(dto);

        Amount expected = new Amount(Money.parse("CAD 100"), new TaxAmount(Money.parse("CAD 10")));

        assertEquals(expected, amount);
    }
}
