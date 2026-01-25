package io.trishul.money.amount.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.trishul.money.dto.MoneyDto;
import io.trishul.money.tax.amount.dto.TaxAmountDto;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AmountDtoTest {
  private AmountDto amount;

  @BeforeEach
  void init() {
    amount = new AmountDto();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(amount.getSubTotal());
    assertNull(amount.getTaxAmount());
    assertNull(amount.getTotal());
  }

  @Test
  void testAllArgConstructor() {
    amount = new AmountDto(new MoneyDto("CAD", new BigDecimal("10")),
        new MoneyDto("CAD", new BigDecimal("20")), new TaxAmountDto());

    assertEquals(new MoneyDto("CAD", new BigDecimal("10")), amount.getTotal());
    assertEquals(new MoneyDto("CAD", new BigDecimal("20")), amount.getSubTotal());
    assertEquals(new TaxAmountDto(), amount.getTaxAmount());
  }

  @Test
  void testGetSetTotal() {
    amount.setTotal(new MoneyDto("CAD", new BigDecimal("30")));

    assertEquals(new MoneyDto("CAD", new BigDecimal("30")), amount.getTotal());
  }

  @Test
  void testGetSetSubTotal() {
    amount.setSubTotal(new MoneyDto("CAD", new BigDecimal("30")));

    assertEquals(new MoneyDto("CAD", new BigDecimal("30")), amount.getSubTotal());
  }

  @Test
  void testGetSetTaxAmount() {
    amount.setTaxAmount(new TaxAmountDto(new MoneyDto("CAD", new BigDecimal("10")),
        new MoneyDto("CAD", new BigDecimal("20"))));

    assertEquals(new TaxAmountDto(new MoneyDto("CAD", new BigDecimal("10")),
        new MoneyDto("CAD", new BigDecimal("20"))), amount.getTaxAmount());
  }
}
