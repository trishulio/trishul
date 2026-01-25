package io.trishul.money.tax.rate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaxRateTest {
  private TaxRate taxRate;

  @BeforeEach
  void init() {
    taxRate = new TaxRate();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(taxRate.getValue());
  }

  @Test
  void testAllArgConstructor() {
    taxRate = new TaxRate(new BigDecimal("1"));

    assertEquals(new BigDecimal("1"), taxRate.getValue());
  }

  @Test
  void testGetTaxAmount_ReturnsNull_WhenArgIsNull() {
    taxRate.setValue(new BigDecimal("2"));

    assertNull(taxRate.getTaxAmount(null));
  }

  @Test
  void testGetTaxAmount_ReturnsTaxAmount_WhenArgIsNotNull() {
    taxRate.setValue(new BigDecimal("2"));

    assertEquals(Money.parse("CAD 200"), taxRate.getTaxAmount(Money.parse("CAD 100")));
  }

  @Test
  void testIsSet_ReturnsFalse_WhenValueIsNull() {
    assertFalse(taxRate.isSet());
    taxRate.setValue(null);
    assertFalse(taxRate.isSet());
  }

  @Test
  void testIsSet_ReturnsFalse_WhenValueIsSetToZero() {
    taxRate.setValue(new BigDecimal("0"));
    assertFalse(taxRate.isSet());
  }

  @Test
  void testIsSet_ReturnsTrue_WhenValueIsNonZero() {
    taxRate.setValue(new BigDecimal("1"));
    assertTrue(taxRate.isSet());

    taxRate.setValue(new BigDecimal("2"));
    assertTrue(taxRate.isSet());

    taxRate.setValue(new BigDecimal("3"));
    assertTrue(taxRate.isSet());
  }

  @Test
  void testIsSet_Static_ReturnsFalse_WhenArgIsNull() {
    assertFalse(TaxRate.isSet(null));
  }

  @Test
  void testIsSet_Static_ReturnsFalse_WhenValueIsNull() {
    assertFalse(taxRate.isSet());
    taxRate.setValue(null);
    assertFalse(TaxRate.isSet(taxRate));
  }

  @Test
  void testIsSet_Static_ReturnsFalse_WhenValueIsSetToZero() {
    taxRate.setValue(new BigDecimal("0"));
    assertFalse(TaxRate.isSet(taxRate));
  }

  @Test
  void testIsSet_Static_ReturnsTrue_WhenValueIsNonZero() {
    taxRate.setValue(new BigDecimal("1"));
    assertTrue(TaxRate.isSet(taxRate));

    taxRate.setValue(new BigDecimal("2"));
    assertTrue(TaxRate.isSet(taxRate));

    taxRate.setValue(new BigDecimal("3"));
    assertTrue(TaxRate.isSet(taxRate));
  }
}
