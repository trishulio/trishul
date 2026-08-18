package sh.trishul.money.tax.amount.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.money.dto.MoneyDto;

class TaxAmountDtoTest {
  private TaxAmountDto taxAmount;

  @BeforeEach
  void init() {
    taxAmount = new TaxAmountDto();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(taxAmount.getPstAmount());
    assertNull(taxAmount.getGstAmount());
    assertNull(taxAmount.getHstAmount());
  }

  @Test
  void testAllArgConstructor() {
    taxAmount = new TaxAmountDto(new MoneyDto("CAD", new BigDecimal("10.00")),
        new MoneyDto("CAD", new BigDecimal("20.00")), new MoneyDto("CAD", new BigDecimal("30.00")),
        new MoneyDto("CAD", new BigDecimal("70.00")));

    assertEquals(new MoneyDto("CAD", new BigDecimal("10.00")), taxAmount.getPstAmount());
    assertEquals(new MoneyDto("CAD", new BigDecimal("20.00")), taxAmount.getGstAmount());
    assertEquals(new MoneyDto("CAD", new BigDecimal("30.00")), taxAmount.getHstAmount());
    assertEquals(new MoneyDto("CAD", new BigDecimal("70.00")), taxAmount.getTotalTaxAmount());
  }

  @Test
  void testGstPstConstructor() {
    taxAmount = new TaxAmountDto(new MoneyDto("CAD", new BigDecimal("10.00")),
        new MoneyDto("CAD", new BigDecimal("20.00")), new MoneyDto("CAD", new BigDecimal("30.00")));

    assertEquals(new MoneyDto("CAD", new BigDecimal("10.00")), taxAmount.getPstAmount());
    assertEquals(new MoneyDto("CAD", new BigDecimal("20.00")), taxAmount.getGstAmount());
    assertEquals(new MoneyDto("CAD", new BigDecimal("30.00")), taxAmount.getTotalTaxAmount());
  }

  @Test
  void testHstConstructor() {
    taxAmount = new TaxAmountDto(new MoneyDto("CAD", new BigDecimal("10.00")),
        new MoneyDto("CAD", new BigDecimal("10.00")));

    assertEquals(new MoneyDto("CAD", new BigDecimal("10.00")), taxAmount.getHstAmount());
    assertEquals(new MoneyDto("CAD", new BigDecimal("10.00")), taxAmount.getTotalTaxAmount());
  }

  @Test
  void testGetSetPstAmount() {
    taxAmount.setPstAmount(new MoneyDto("CAD", new BigDecimal("100.00")));

    assertEquals(new MoneyDto("CAD", new BigDecimal("100.00")), taxAmount.getPstAmount());
  }

  @Test
  void testGetSetGstAmount() {
    taxAmount.setGstAmount(new MoneyDto("CAD", new BigDecimal("100.00")));

    assertEquals(new MoneyDto("CAD", new BigDecimal("100.00")), taxAmount.getGstAmount());
  }

  @Test
  void testGetSetHstAmount() {
    taxAmount.setHstAmount(new MoneyDto("CAD", new BigDecimal("100.00")));

    assertEquals(new MoneyDto("CAD", new BigDecimal("100.00")), taxAmount.getHstAmount());
  }

  @Test
  void testGetTotalTaxAmountDto_ReturnsTotalMoney() {
    taxAmount = new TaxAmountDto();
    taxAmount.setTotalTaxAmount(new MoneyDto("CAD", new BigDecimal("60")));

    assertEquals(new MoneyDto("CAD", new BigDecimal("60")), taxAmount.getTotalTaxAmount());
  }

  @Test
  void testAccessPstAmount() throws Exception {
    TaxAmountDto accessor = new TaxAmountDto();
    MoneyDto value = mock(MoneyDto.class);
    assertSame(accessor, accessor.setPstAmount(value));
    assertEquals(value, accessor.getPstAmount());
  }

  @Test
  void testAccessGstAmount() throws Exception {
    TaxAmountDto accessor = new TaxAmountDto();
    MoneyDto value = mock(MoneyDto.class);
    assertSame(accessor, accessor.setGstAmount(value));
    assertEquals(value, accessor.getGstAmount());
  }

  @Test
  void testAccessHstAmount() throws Exception {
    TaxAmountDto accessor = new TaxAmountDto();
    MoneyDto value = mock(MoneyDto.class);
    assertSame(accessor, accessor.setHstAmount(value));
    assertEquals(value, accessor.getHstAmount());
  }

  @Test
  void testAccessTotalTaxAmount() throws Exception {
    TaxAmountDto accessor = new TaxAmountDto();
    MoneyDto value = mock(MoneyDto.class);
    assertSame(accessor, accessor.setTotalTaxAmount(value));
    assertEquals(value, accessor.getTotalTaxAmount());
  }

}
