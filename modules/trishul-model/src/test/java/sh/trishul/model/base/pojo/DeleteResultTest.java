package sh.trishul.model.base.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class DeleteResultTest {
  @Test
  void testAccessCount() throws Exception {
    DeleteResult accessor = new DeleteResult();
    assertSame(accessor, accessor.setCount(123L));
    assertEquals(123L, accessor.getCount());
  }

  @Test
  void testConstructor() {
    DeleteResult res = new DeleteResult(10L);
    assertEquals(10L, res.getCount());
  }

  @Test
  void testEqualsAndHashCode() {
    DeleteResult r1 = new DeleteResult(10L);
    DeleteResult r2 = new DeleteResult(10L);
    DeleteResult r3 = new DeleteResult(20L);

    assertEquals(r1, r2);
    assertEquals(r1.hashCode(), r2.hashCode());
    assertNotEquals(r1, r3);
  }

  @Test
  void testToString() {
    DeleteResult r = new DeleteResult(10L);
    assertNotNull(r.toString());
  }
}
