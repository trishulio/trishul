package sh.trishul.user.status;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class UserStatusTest {
  @Test
  void testSetGetId() {
    final Long id = 1L;
    final UserStatus userStatus = new UserStatus();
    userStatus.setId(id);
    assertEquals(id, userStatus.getId());
  }

  @Test
  void testSetGetName() {
    final String name = "name";
    final UserStatus userStatus = new UserStatus();
    userStatus.setName(name);
    assertEquals(name, userStatus.getName());
  }

  @Test
  void testAccessId() throws Exception {
    UserStatus accessor = new UserStatus();
    assertSame(accessor, accessor.setId(123L));
    assertEquals(123L, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    UserStatus accessor = new UserStatus();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    UserStatus accessor = new UserStatus();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    UserStatus accessor = new UserStatus();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

  @Test
  void testAccessVersion() throws Exception {
    UserStatus accessor = new UserStatus();
    assertSame(accessor, accessor.setVersion(123));
    assertEquals(123, accessor.getVersion());
  }

}
