package io.trishul.user.role.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserRoleDtoTest {
  private UserRoleDto dto;

  @BeforeEach
  void init() {
    dto = new UserRoleDto();
  }

  @Test
  void testAllArgs_SetsAllFields() {
    dto = new UserRoleDto(1L, "ROLE_NAME", LocalDateTime.of(1999, 1, 1, 0, 0),
        LocalDateTime.of(2000, 1, 1, 0, 0), 1);

    assertEquals(1L, dto.getId());
    assertEquals("ROLE_NAME", dto.getName());
    assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), dto.getCreatedAt());
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getLastUpdated());
    assertEquals(1, dto.getVersion());
  }

  @Test
  void testAccessId() {
    assertNull(dto.getId());
    dto.setId(1L);
    assertEquals(1L, dto.getId());
  }

  @Test
  void testAccessName() {
    assertNull(dto.getName());
    dto.setName("roleName");
    assertEquals("roleName", dto.getName());
  }

  @Test
  void testAccessCreatedAt() {
    assertNull(dto.getCreatedAt());
    dto.setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() {
    assertNull(dto.getLastUpdated());
    dto.setLastUpdated(LocalDateTime.of(1999, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), dto.getLastUpdated());
  }

  @Test
  void testAccessVersion() {
    assertNull(dto.getVersion());
    dto.setVersion(1);
    assertEquals(1, dto.getVersion());
  }
}
