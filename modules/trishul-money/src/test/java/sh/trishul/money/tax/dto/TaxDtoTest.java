package sh.trishul.money.tax.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.money.tax.rate.dto.TaxRateDto;

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

  @Test
  void testAccessGstRate() throws Exception {
    TaxDto accessor = new TaxDto();
    TaxRateDto value = mock(TaxRateDto.class);
    assertSame(accessor, accessor.setGstRate(value));
    assertEquals(value, accessor.getGstRate());
  }

  @Test
  void testAccessPstRate() throws Exception {
    TaxDto accessor = new TaxDto();
    TaxRateDto value = mock(TaxRateDto.class);
    assertSame(accessor, accessor.setPstRate(value));
    assertEquals(value, accessor.getPstRate());
  }

  @Test
  void testAccessHstRate() throws Exception {
    TaxDto accessor = new TaxDto();
    TaxRateDto value = mock(TaxRateDto.class);
    assertSame(accessor, accessor.setHstRate(value));
    assertEquals(value, accessor.getHstRate());
  }

}
