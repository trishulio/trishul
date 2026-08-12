package sh.trishul.money.amount.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.money.tax.amount.TaxAmount;

class AmountTest {
  private Amount amount;

  @BeforeEach
  void init() {
    amount = new Amount();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(amount.getSubTotal());
    assertNull(amount.getTotal());
    assertNull(amount.getTaxAmount());
  }

  @Test
  void testConstructorWithSubTotal() {
    amount = new Amount(Money.parse("CAD 100"));
    assertEquals(Money.parse("CAD 100"), amount.getSubTotal());
    assertNull(amount.getTaxAmount());
    assertNull(amount.getTotal());
  }

  @Test
  void testAllArgConstructor() {
    amount = new Amount(Money.parse("CAD 100"), new TaxAmount(Money.parse("CAD 10")));

    assertEquals(Money.parse("CAD 110"), amount.getTotal());
    assertEquals(Money.parse("CAD 100"), amount.getSubTotal());
    TaxAmount expected = new TaxAmount(Money.parse("CAD 10"));
    assertEquals(expected, amount.getTaxAmount());
  }

  @Test
  void testSetSubTotal_SetsSubTotalAndReturnsAmount() {
    Amount returned = amount.setSubTotal(Money.parse("CAD 100"));
    assertEquals(Money.parse("CAD 100"), amount.getSubTotal());
    assertSame(amount, returned);
  }

  @Test
  void testSetTaxAmount_SetsTaxAmountAndReturnsAmount() {
    TaxAmount taxAmount = new TaxAmount(Money.parse("CAD 10"));
    Amount returned = amount.setTaxAmount(taxAmount);
    assertEquals(taxAmount, amount.getTaxAmount());
    assertSame(amount, returned);
  }

  @Test
  void testSetTotal_WhenSubTotalIsNull_SetsTotalToNull() {
    amount.setTaxAmount(new TaxAmount(Money.parse("CAD 10")));
    amount.setSubTotal(null);
    amount.setTotal();
    assertNull(amount.getTotal());
  }

  @Test
  void testSetTotal_WhenTaxAmountIsNull_SetsTotalToNull() {
    amount.setSubTotal(Money.parse("CAD 100"));
    amount.setTaxAmount(null);
    amount.setTotal();
    assertNull(amount.getTotal());
  }

  @Test
  void testSetTotal_WhenTotalTaxAmountIsNull_SetsTotalToNull() {
    amount.setSubTotal(Money.parse("CAD 100"));
    amount.setTaxAmount(new TaxAmount()); // TaxAmount with all components null, so totalTaxAmount
                                          // is null
    amount.setTotal();
    assertNull(amount.getTotal());
  }

  @Test
  void testGetTotal_RecalculatesTotal() {
    amount.setSubTotal(Money.parse("CAD 100"));
    amount.setTaxAmount(new TaxAmount(Money.parse("CAD 10")));

    assertEquals(Money.parse("CAD 110"), amount.getTotal());
  }

  @Test
  void testConstants() {
    assertEquals("total", Amount.FIELD_TOTAL);
    assertEquals("subTotal", Amount.FIELD_SUB_TOTAL);
    assertEquals("taxAmount", Amount.FIELD_TAX_AMOUNT);
  }

  @Test
  void testEquals_ReturnsTrueForEqualObjects() {
    amount = new Amount(Money.parse("CAD 100"), new TaxAmount(Money.parse("CAD 10")));
    Amount other = new Amount(Money.parse("CAD 100"), new TaxAmount(Money.parse("CAD 10")));
    assertEquals(amount, other);
  }

  @Test
  void testEquals_ReturnsFalseForUnequalObjects() {
    amount = new Amount(Money.parse("CAD 100"), new TaxAmount(Money.parse("CAD 10")));
    Amount other = new Amount(Money.parse("CAD 100"), new TaxAmount(Money.parse("CAD 11")));
    assertNotEquals(amount, other);
  }

  @Test
  void testHashCode_ReturnsSameHashCodeForEqualObjects() {
    amount = new Amount(Money.parse("CAD 100"), new TaxAmount(Money.parse("CAD 10")));
    Amount other = new Amount(Money.parse("CAD 100"), new TaxAmount(Money.parse("CAD 10")));
    assertEquals(amount.hashCode(), other.hashCode());
  }

  @Test
  void testToString_ReturnsJsonString() {
    amount = new Amount(Money.parse("CAD 100"), new TaxAmount(Money.parse("CAD 10")));
    String str = amount.toString();
    assertNotNull(str);
    assertTrue(str.contains("\"amount\":100"));
    assertTrue(str.contains("\"amount\":10"));
  }

  @Test
  void testAccessSubTotal() throws Exception {
    Amount accessor = new Amount();
    Money value = org.joda.money.Money.parse("USD 123.45");
    assertSame(accessor, accessor.setSubTotal(value));
    assertEquals(value, accessor.getSubTotal());
  }

  @Test
  void testAccessTaxAmount() throws Exception {
    Amount accessor = new Amount();
    TaxAmount value = mock(TaxAmount.class);
    assertSame(accessor, accessor.setTaxAmount(value));
    assertEquals(value, accessor.getTaxAmount());
  }

}
