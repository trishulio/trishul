package io.trishul.model.base.dto;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class BaseDtoTest {
  private static class ConcreteBaseDto extends BaseDto {
  }

  @Test
  public void testBaseDtoConstructor() {
    ConcreteBaseDto dto = new ConcreteBaseDto();
    assertNotNull(dto);
  }
}
