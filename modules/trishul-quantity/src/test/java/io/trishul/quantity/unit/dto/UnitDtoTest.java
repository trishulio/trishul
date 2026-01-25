package io.trishul.quantity.unit.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UnitDtoTest {
  private UnitDto dto;

  @BeforeEach
  void init() {
    dto = new UnitDto();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(dto.getSymbol());
  }

  @Test
  void testAllArgConstructor() {
    dto = new UnitDto("SYMBOL");

    assertEquals("SYMBOL", dto.getSymbol());
  }

  @Test
  void testAccessSymbol() {
    dto.setSymbol("SYMBOL_1");
    assertEquals("SYMBOL_1", dto.getSymbol());
  }
}
