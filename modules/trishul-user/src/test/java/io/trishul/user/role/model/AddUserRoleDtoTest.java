package io.trishul.user.role.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddUserRoleDtoTest {
  private AddUserRoleDto dto;

  @BeforeEach
  void init() {
    dto = new AddUserRoleDto();
  }

  @Test
  void testAllArgConstructor_SetsAllFields() {
    dto = new AddUserRoleDto("NAME");
    assertEquals("NAME", dto.getName());
  }

  @Test
  void testAccessName() {
    assertNull(dto.getName());
    dto.setName("NAME_1");
    assertEquals("NAME_1", dto.getName());
  }
}
