package sh.trishul.model.base.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class DeleteResultDtoTest {
  @Test
  void testAccessCount() throws Exception {
    DeleteResultDto accessor = new DeleteResultDto();
    assertSame(accessor, accessor.setCount(123L));
    assertEquals(123L, accessor.getCount());
  }

  @Test
  void testConstructor() {
    DeleteResultDto res = new DeleteResultDto(10L);
    assertEquals(10L, res.getCount());
  }

  @Test
  void testEqualsAndHashCode() {
    DeleteResultDto r1 = new DeleteResultDto(10L);
    DeleteResultDto r2 = new DeleteResultDto(10L);
    DeleteResultDto r3 = new DeleteResultDto(20L);

    assertEquals(r1, r2);
    assertEquals(r1.hashCode(), r2.hashCode());
    assertNotEquals(r1, r3);
  }

  @Test
  void testToString() {
    DeleteResultDto r = new DeleteResultDto(10L);
    assertNotNull(r.toString());
  }
}
