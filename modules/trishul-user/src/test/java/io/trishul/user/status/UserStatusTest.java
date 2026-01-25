package io.trishul.user.status;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
