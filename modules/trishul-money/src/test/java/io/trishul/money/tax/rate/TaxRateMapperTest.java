package io.trishul.money.tax.rate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.trishul.money.tax.rate.dto.TaxRateDto;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaxRateMapperTest {

  private TaxRateMapper mapper;

  @BeforeEach
  void init() {
    mapper = TaxRateMapper.INSTANCE;
  }

  @Test
  void testToDto_ReturnsNull_WhenArgIsNull() {
    assertNull(mapper.toDto(null));
  }

  @Test
  void testToDto_ReturnsDto_WhenArgIsNotNull() {
    TaxRate rate = new TaxRate(new BigDecimal("10"));

    TaxRateDto dto = mapper.toDto(rate);

    TaxRateDto expected = new TaxRateDto(new BigDecimal("10"));
    assertEquals(expected, dto);
  }

  @Test
  void toFromDto_ReturnsNull_WhenArgIsNull() {
    assertNull(mapper.fromDto(null));
  }

  @Test
  void testFromDto_ReturnsPojo_WhenArgIsNotNull() {
    TaxRateDto dto = new TaxRateDto(new BigDecimal("10"));

    TaxRate rate = mapper.fromDto(dto);

    TaxRate expected = new TaxRate(new BigDecimal("10"));
    assertEquals(expected, rate);
  }
}
