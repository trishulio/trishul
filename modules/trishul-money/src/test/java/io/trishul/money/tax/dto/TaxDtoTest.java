package io.trishul.money.tax.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.trishul.money.tax.rate.dto.TaxRateDto;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaxDtoTest {
  private TaxDto tax;

  @BeforeEach
  void init() {
    tax = new TaxDto();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(tax.getPstRate());
    assertNull(tax.getGstRate());
    assertNull(tax.getHstRate());
  }

  @Test
  void testPstGstConstructor() {
    tax = new TaxDto(new TaxRateDto(new BigDecimal("1")), new TaxRateDto(new BigDecimal("2")));

    assertEquals(new TaxRateDto(new BigDecimal("1")), tax.getPstRate());
    assertEquals(new TaxRateDto(new BigDecimal("2")), tax.getGstRate());
    assertNull(tax.getHstRate());
  }

  @Test
  void testHstConstructor() {
    tax = new TaxDto(new TaxRateDto(new BigDecimal("1")));

    assertEquals(new TaxRateDto(new BigDecimal("1")), tax.getHstRate());
    assertNull(tax.getPstRate());
    assertNull(tax.getGstRate());
  }

  @Test
  void testGetSetGstRate() {
    tax.setGstRate(new TaxRateDto(new BigDecimal("1")));
    assertEquals(new TaxRateDto(new BigDecimal("1")), tax.getGstRate());
  }

  @Test
  void testGetSetPstRate() {
    tax.setPstRate(new TaxRateDto(new BigDecimal("1")));
    assertEquals(new TaxRateDto(new BigDecimal("1")), tax.getPstRate());
  }

  @Test
  void testGetSetHstRate() {
    tax.setHstRate(new TaxRateDto(new BigDecimal("1")));
    assertEquals(new TaxRateDto(new BigDecimal("1")), tax.getHstRate());
  }
}
