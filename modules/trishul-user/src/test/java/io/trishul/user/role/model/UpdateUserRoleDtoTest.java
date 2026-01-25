package io.trishul.user.role.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UpdateUserRoleDtoTest {
  private UpdateUserRoleDto dto;

  @BeforeEach
  void init() {
    dto = new UpdateUserRoleDto();
  }

  @Test
  void testAllArgs_SetsAllFields() {
    dto = new UpdateUserRoleDto(1L, "ROLE_NAME", 1);

    assertEquals(1L, dto.getId());
    assertEquals("ROLE_NAME", dto.getName());
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
  void testAccessVersion() {
    assertNull(dto.getVersion());
    dto.setVersion(1);
    assertEquals(1, dto.getVersion());
  }
}
