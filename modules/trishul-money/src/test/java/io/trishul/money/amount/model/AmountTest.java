package io.trishul.money.amount.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.trishul.money.tax.amount.TaxAmount;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AmountTest {
  private Amount amount;

  @BeforeEach
  public void init() {
    amount = new Amount();
  }

  @Test
  public void testNoArgConstructor() {
    assertNull(amount.getSubTotal());
    assertNull(amount.getTotal());
    assertNull(amount.getTaxAmount());
  }

  @Test
  public void testAllArgConstructor() {
    amount = new Amount(Money.parse("CAD 100"), new TaxAmount(Money.parse("CAD 10")));

    assertEquals(Money.parse("CAD 110"), amount.getTotal());
    assertEquals(Money.parse("CAD 100"), amount.getSubTotal());
    assertEquals(new TaxAmount(Money.parse("CAD 10")), amount.getTaxAmount());
  }
}
