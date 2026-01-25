package io.trishul.quantity.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuantityDtoTest {
  QuantityDto dto;

  @BeforeEach
  void init() {
    dto = new QuantityDto();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(dto.getSymbol());
    assertNull(dto.getValue());
  }

  @Test
  void testAllArgConstructor() {
    dto = new QuantityDto("g", new BigDecimal("100"));
    assertEquals("g", dto.getSymbol());
    assertEquals(new BigDecimal("100"), dto.getValue());
  }

  @Test
  void testAccessSymbol() {
    assertNull(dto.getSymbol());
    dto.setSymbol("g");
    assertEquals("g", dto.getSymbol());
  }

  @Test
  void testAccessValue() {
    assertNull(dto.getValue());
    dto.setValue(new BigDecimal("100"));
    assertEquals(new BigDecimal("100"), dto.getValue());

    dto.setValue(new BigDecimal("100.00"));
    assertEquals(new BigDecimal("100"), dto.getValue());
  }
}
