package io.trishul.quantity.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuantityDtoTest {
  QuantityDto dto;

  @BeforeEach
  public void init() {
    dto = new QuantityDto();
  }

  @Test
  public void testNoArgConstructor() {
    assertNull(dto.getSymbol());
    assertNull(dto.getValue());
  }

  @Test
  public void testAllArgConstructor() {
    dto = new QuantityDto("g", new BigDecimal("100"));
    assertEquals("g", dto.getSymbol());
    assertEquals(new BigDecimal("100"), dto.getValue());
  }

  @Test
  public void testAccessSymbol() {
    assertNull(dto.getSymbol());
    dto.setSymbol("g");
    assertEquals("g", dto.getSymbol());
  }

  @Test
  public void testAccessValue() {
    assertNull(dto.getValue());
    dto.setValue(new BigDecimal("100"));
    assertEquals(new BigDecimal("100"), dto.getValue());

    dto.setValue(new BigDecimal("100.00"));
    assertEquals(new BigDecimal("100"), dto.getValue());
  }
}
