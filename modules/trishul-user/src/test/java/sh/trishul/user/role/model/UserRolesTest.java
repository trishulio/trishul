package sh.trishul.user.role.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class UserRolesTest {

  @Test
  void testGetId() {
    assertEquals(1L, UserRoles.ADMIN.getId());
    assertEquals(2L, UserRoles.OPERATOR.getId());
  }

  @Test
  void testGetName() {
    assertEquals("ADMIN", UserRoles.ADMIN.getName());
    assertEquals("OPERATOR", UserRoles.OPERATOR.getName());
  }

  @Test
  void testFromId_WhenNull_ReturnsNull() {
    assertNull(UserRoles.fromId(null));
  }

  @Test
  void testFromId_WhenValidId_ReturnsRole() {
    assertEquals(UserRoles.ADMIN, UserRoles.fromId(1L));
    assertEquals(UserRoles.OPERATOR, UserRoles.fromId(2L));
  }

  @Test
  void testFromId_WhenNonExistentId_ReturnsNull() {
    assertNull(UserRoles.fromId(999L));
  }

  @Test
  void testFromName_WhenNull_ReturnsNull() {
    assertNull(UserRoles.fromName(null));
  }

  @Test
  void testFromName_WhenValidName_ReturnsRole() {
    assertEquals(UserRoles.ADMIN, UserRoles.fromName("ADMIN"));
    assertEquals(UserRoles.OPERATOR, UserRoles.fromName("OPERATOR"));
  }

  @Test
  void testFromName_WhenCaseInsensitive_ReturnsRole() {
    assertEquals(UserRoles.ADMIN, UserRoles.fromName("admin"));
    assertEquals(UserRoles.OPERATOR, UserRoles.fromName("operator"));
  }

  @Test
  void testFromName_WhenNonExistentName_ReturnsNull() {
    assertNull(UserRoles.fromName("NON_EXISTENT"));
  }

  @Test
  void testValueOf() {
    assertEquals(UserRoles.ADMIN, UserRoles.valueOf("ADMIN"));
    assertEquals(UserRoles.OPERATOR, UserRoles.valueOf("OPERATOR"));
  }

  @Test
  void testValues() {
    UserRoles[] values = UserRoles.values();
    assertEquals(2, values.length);
    assertEquals(UserRoles.ADMIN, values[0]);
    assertEquals(UserRoles.OPERATOR, values[1]);
  }
}
