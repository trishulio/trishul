package io.trishul.money.tax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.trishul.money.tax.dto.TaxDto;
import io.trishul.money.tax.rate.TaxRate;
import io.trishul.money.tax.rate.dto.TaxRateDto;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaxMapperTest {
  private TaxMapper mapper;

  @BeforeEach
  public void init() {
    mapper = TaxMapper.INSTANCE;
  }

  @Test
  public void testFromDto_ReturnsPojo_WhenDtoIsNotNull() {

    TaxDto dto
        = new TaxDto(new TaxRateDto(new BigDecimal("1")), new TaxRateDto(new BigDecimal("2")));
    Tax tax = mapper.fromDto(dto);

    Tax expected = new Tax(new TaxRate(new BigDecimal("1")), new TaxRate(new BigDecimal("2")));
    assertEquals(expected, tax);
  }

  @Test
  public void testFromDto_ReturnsNull_WhenDtoIsNull() {
    assertNull(mapper.fromDto(null));
  }

  @Test
  public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
    Tax tax = new Tax(new TaxRate(new BigDecimal("1")), new TaxRate(new BigDecimal("2")));

    TaxDto dto = mapper.toDto(tax);

    TaxDto expected
        = new TaxDto(new TaxRateDto(new BigDecimal("1")), new TaxRateDto(new BigDecimal("2")));
    assertEquals(expected, dto);
  }

  @Test
  public void testToDto_ReturnsNull_WhenPojoIsNull() {
    assertNull(mapper.toDto(null));
  }
}
