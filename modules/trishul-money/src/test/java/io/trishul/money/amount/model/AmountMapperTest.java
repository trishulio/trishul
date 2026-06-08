package io.trishul.money.amount.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.trishul.money.dto.MoneyDto;
import io.trishul.money.tax.amount.TaxAmount;
import io.trishul.money.tax.amount.dto.TaxAmountDto;
import java.math.BigDecimal;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AmountMapperTest {
  private AmountMapper mapper;

  @BeforeEach
  void init() {
    mapper = AmountMapper.INSTANCE;
  }

  @Test
  void testToDto_ReturnsNull_WhenArgIsNull() {
    assertNull(mapper.toDto(null));
  }

  @Test
  void testToDto_ReturnsDto_WhenArgIsNotNull() {
    Amount amount = new Amount(Money.parse("CAD 100"), new TaxAmount(Money.parse("CAD 10")));

    AmountDto dto = mapper.toDto(amount);

    AmountDto expected = new AmountDto(new MoneyDto("CAD", new BigDecimal("110.00")),
        new MoneyDto("CAD", new BigDecimal("100.00")),
        new TaxAmountDto(new MoneyDto("CAD", new BigDecimal("10.00")),
            new MoneyDto("CAD", new BigDecimal("10.00"))));
    assertEquals(expected, dto);
  }

  @Test
  void testFromDto_ReturnsNull_WhenArgIsNull() {
    assertNull(mapper.fromDto(null));
  }

  @Test
  void testFromDto_ReturnsPojo_WhenArgIsNotNull() {
    AmountDto dto = new AmountDto(new MoneyDto("CAD", new BigDecimal("110.00")),
        new MoneyDto("CAD", new BigDecimal("100.00")),
        new TaxAmountDto(new MoneyDto("CAD", new BigDecimal("10.00")),
            new MoneyDto("CAD", new BigDecimal("10.00"))));

    Amount amount = mapper.fromDto(dto);

    Amount expected = new Amount(Money.parse("CAD 100"), new TaxAmount(Money.parse("CAD 10")));

    assertEquals(expected, amount);
  }

  @Test
  void testToDto_ReturnsDtoWithNullTaxAmount_WhenArgHasNullTaxAmount() {
    Amount amount = new Amount(Money.parse("CAD 100"), null);

    AmountDto dto = mapper.toDto(amount);

    AmountDto expected = new AmountDto(null, new MoneyDto("CAD", new BigDecimal("100.00")), null);
    assertEquals(expected, dto);
  }

  @Test
  void testFromDto_ReturnsPojoWithNullTaxAmount_WhenArgHasNullTaxAmount() {
    AmountDto dto = new AmountDto(new MoneyDto("CAD", new BigDecimal("100.00")),
        new MoneyDto("CAD", new BigDecimal("100.00")), null);

    Amount amount = mapper.fromDto(dto);

    Amount expected = new Amount(Money.parse("CAD 100"), null);

    assertEquals(expected, amount);
  }
}
