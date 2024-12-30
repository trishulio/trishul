package io.trishul.user.role.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserRoleTest {
  private UserRole role;

  @BeforeEach
  public void init() {
    role = new UserRole();
  }

  @Test
  public void testAllArgs_SetsAllFields() {
    role = new UserRole(1L, "ROLE_NAME", LocalDateTime.of(1999, 1, 1, 0, 0),
        LocalDateTime.of(2000, 1, 1, 0, 0), 1);

    assertEquals(1L, role.getId());
    assertEquals("ROLE_NAME", role.getName());
    assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), role.getCreatedAt());
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), role.getLastUpdated());
    assertEquals(1, role.getVersion());
  }

  @Test
  public void testAccessId() {
    assertNull(role.getId());
    role.setId(1L);
    assertEquals(1L, role.getId());
  }

  @Test
  public void testAccessName() {
    assertNull(role.getName());
    role.setName("roleName");
    assertEquals("roleName", role.getName());
  }

  @Test
  public void testAccessCreatedAt() {
    assertNull(role.getCreatedAt());
    role.setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), role.getCreatedAt());
  }

  @Test
  public void testAccessLastUpdated() {
    assertNull(role.getLastUpdated());
    role.setLastUpdated(LocalDateTime.of(1999, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), role.getLastUpdated());
  }

  @Test
  public void testAccessVersion() {
    assertNull(role.getVersion());
    role.setVersion(1);
    assertEquals(1, role.getVersion());
  }
}
