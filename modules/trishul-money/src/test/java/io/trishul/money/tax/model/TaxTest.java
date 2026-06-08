package io.trishul.money.tax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import io.trishul.money.tax.rate.TaxRate;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaxTest {
  private Tax tax;

  @BeforeEach
  void init() {
    tax = new Tax();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(tax.getPstRate());
    assertNull(tax.getGstRate());
    assertNull(tax.getHstRate());
  }

  @Test
  void testPstGstConstructor() {
    tax = new Tax(new TaxRate(new BigDecimal("1")), new TaxRate(new BigDecimal("2")));

    assertEquals(new TaxRate(new BigDecimal("1")), tax.getPstRate());
    assertEquals(new TaxRate(new BigDecimal("2")), tax.getGstRate());
    assertNull(tax.getHstRate());
  }

  @Test
  void testHstConstructor() {
    tax = new Tax(new TaxRate(new BigDecimal("1")));

    assertEquals(new TaxRate(new BigDecimal("1")), tax.getHstRate());
    assertNull(tax.getPstRate());
    assertNull(tax.getGstRate());
  }

  @Test
  void testGetSetGstRate() {
    Tax result = tax.setGstRate(new TaxRate(new BigDecimal("1")));
    assertEquals(new TaxRate(new BigDecimal("1")), tax.getGstRate());
    assertEquals(tax, result);
  }

  @Test
  void testSetGstRate_ThrowException_WhenHstIsPresent() {
    tax.setHstRate(new TaxRate(new BigDecimal("1")));

    tax.setGstRate(null);
    tax.setGstRate(new TaxRate(new BigDecimal("0.00")));
    assertThrows(IllegalArgumentException.class,
        () -> tax.setGstRate(new TaxRate(new BigDecimal("2"))));
  }

  @Test
  void testGetSetPstRate() {
    Tax result = tax.setPstRate(new TaxRate(new BigDecimal("1")));
    assertEquals(new TaxRate(new BigDecimal("1")), tax.getPstRate());
    assertEquals(tax, result);
  }

  @Test
  void testSetPstRate_ThrowException_WhenHstIsPresent() {
    tax.setHstRate(new TaxRate(new BigDecimal("1")));

    tax.setPstRate(null);
    tax.setPstRate(new TaxRate(new BigDecimal("0.00")));
    assertThrows(IllegalArgumentException.class,
        () -> tax.setPstRate(new TaxRate(new BigDecimal("2"))));
  }

  @Test
  void testGetSetHstRate() {
    Tax result = tax.setHstRate(new TaxRate(new BigDecimal("1")));
    assertEquals(new TaxRate(new BigDecimal("1")), tax.getHstRate());
    assertEquals(tax, result);
  }

  @Test
  void testSetHstRate_ThrowException_WhenPstIsPresent() {
    tax.setPstRate(new TaxRate(new BigDecimal("1")));

    tax.setHstRate(null);
    tax.setHstRate(new TaxRate(new BigDecimal("0.00")));
    assertThrows(IllegalArgumentException.class,
        () -> tax.setHstRate(new TaxRate(new BigDecimal("2"))));
  }

  @Test
  void testSetHstRate_ThrowException_WhenGstIsPresent() {
    tax.setGstRate(new TaxRate(new BigDecimal("1")));

    tax.setHstRate(null);
    tax.setHstRate(new TaxRate(new BigDecimal("0.00")));
    assertThrows(IllegalArgumentException.class,
        () -> tax.setHstRate(new TaxRate(new BigDecimal("2"))));
  }

  @Test
  void testEquals_ReturnsTrue_WhenAttributesAreSame() {
    tax = new Tax(new TaxRate(new BigDecimal("1")), new TaxRate(new BigDecimal("2")));
    Tax other = new Tax(new TaxRate(new BigDecimal("1")), new TaxRate(new BigDecimal("2")));

    assertEquals(tax, other);
  }

  @Test
  void testHashCode_ReturnsSameHashCode_WhenAttributesAreSame() {
    tax = new Tax(new TaxRate(new BigDecimal("1")), new TaxRate(new BigDecimal("2")));
    Tax other = new Tax(new TaxRate(new BigDecimal("1")), new TaxRate(new BigDecimal("2")));

    assertEquals(tax.hashCode(), other.hashCode());
  }

  @Test
  void testToString_ReturnsJsonString() {
    tax = new Tax(new TaxRate(new BigDecimal("1")), new TaxRate(new BigDecimal("2")));
    String json = tax.toString();

    assertEquals(tax, Tax.fromString(json, Tax.class));
  }
}
