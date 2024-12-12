package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.model.Currency;
import io.company.brewcraft.model.MoneyEntity;

public class MoneyMapperTest {
    MoneyMapper mapper;

    @BeforeEach
    public void init() {
        mapper = MoneyMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsMoneyDto_WhenMoneyIsNotNull() {
        MoneyDto dto = mapper.toDto(Money.parse("USD 100"));
        assertEquals("USD", dto.getCurrency());
        assertEquals(new BigDecimal("100"), dto.getAmount());
    }

    @Test
    public void testToDto_ReturnsNull_WhenMoneyIsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void testFromDto_ReturnsMoney_WhenDtoIsNotNull() {
        Money money = mapper.fromDto(new MoneyDto("CAD", new BigDecimal(123)));
        assertEquals(Money.parse("CAD 123"), money);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromDto(null));
    }

    @Test
    public void testFromEntity_ReturnsMoney_WhenEntityIsNotNull() {
        MoneyEntity entity = new MoneyEntity(new Currency(124, "CAD"), new BigDecimal("10"));

        assertEquals(Money.parse("CAD 10"), mapper.fromEntity(entity));
    }

    @Test
    public void testFromEntity_ReturnsNull_WhenEntityIsNull() {
        assertNull(mapper.fromEntity(null));
    }

    @Test
    public void testToEntity_ReturnsEntity_WhenPojoIsNotNull() {
        Money money = Money.parse("CAD 10");

        MoneyEntity entity = mapper.toEntity(money);
        assertEquals(new MoneyEntity(new Currency(124, "CAD"), new BigDecimal("10.00")), entity);
    }

    @Test
    public void testToEntity_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.toEntity(null));
    }
}
