package io.trishul.money;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.trishul.money.currency.model.Currency;
import io.trishul.money.dto.MoneyDto;
import java.math.BigDecimal;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoneyMapperTest {
  MoneyMapper mapper;

  @BeforeEach
  void init() {
    mapper = MoneyMapper.INSTANCE;
  }

  @Test
  void testToDto_ReturnsMoneyDto_WhenMoneyIsNotNull() {
    MoneyDto dto = mapper.toDto(Money.parse("USD 100"));
    assertEquals("USD", dto.getCurrency());
    assertEquals(new BigDecimal("100"), dto.getAmount());
  }

  @Test
  void testToDto_ReturnsNull_WhenMoneyIsNull() {
    assertNull(mapper.toDto(null));
  }

  @Test
  void testFromDto_ReturnsMoney_WhenDtoIsNotNull() {
    Money money = mapper.fromDto(new MoneyDto("CAD", new BigDecimal(123)));
    assertEquals(Money.parse("CAD 123"), money);
  }

  @Test
  void testFromDto_ReturnsNull_WhenDtoIsNull() {
    assertNull(mapper.fromDto(null));
  }

  @Test
  void testFromEntity_ReturnsMoney_WhenEntityIsNotNull() {
    MoneyEntity entity = new MoneyEntity(new Currency(124, "CAD"), new BigDecimal("10"));

    assertEquals(Money.parse("CAD 10"), mapper.fromEntity(entity));
  }

  @Test
  void testFromEntity_ReturnsNull_WhenEntityIsNull() {
    assertNull(mapper.fromEntity(null));
  }

  @Test
  void testToEntity_ReturnsEntity_WhenPojoIsNotNull() {
    Money money = Money.parse("CAD 10");

    MoneyEntity entity = mapper.toEntity(money);
    assertEquals(new MoneyEntity(new Currency(124, "CAD"), new BigDecimal("10.00")), entity);
  }

  @Test
  void testToEntity_ReturnsNull_WhenPojoIsNull() {
    assertNull(mapper.toEntity(null));
  }
}
