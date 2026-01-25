package io.trishul.money.tax.rate.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaxRateDtoTest {
  private TaxRateDto taxRate;

  @BeforeEach
  void init() {
    taxRate = new TaxRateDto();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(taxRate.getValue());
  }

  @Test
  void testAllArgConstructor() {
    taxRate = new TaxRateDto(new BigDecimal("1"));

    assertEquals(new BigDecimal("1"), taxRate.getValue());
  }
}
