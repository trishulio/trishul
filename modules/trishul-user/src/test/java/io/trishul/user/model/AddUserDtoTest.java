package io.trishul.user.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddUserDtoTest {
  private AddUserDto dto;

  @BeforeEach
  void init() {
    dto = new AddUserDto();
  }

  @Test
  void testAllArgConstructors_SetsAllValues() {
    dto = new AddUserDto("userName", "displayName", "firstName", "lastName", "email", 1L, 2L,
        "phoneNumber", URI.create("imageSrc"), List.of(10L));

    assertEquals("userName", dto.getUserName());
    assertEquals("displayName", dto.getDisplayName());
    assertEquals("firstName", dto.getFirstName());
    assertEquals("lastName", dto.getLastName());
    assertEquals("email", dto.getEmail());
    assertEquals(1L, dto.getStatusId());
    assertEquals(2L, dto.getSalutationId());
    assertEquals("phoneNumber", dto.getPhoneNumber());
    assertEquals(URI.create("imageSrc"), dto.getImageSrc());
    assertEquals(List.of(10L), dto.getRoleIds());
  }

  @Test
  void testAccessUserName() {
    assertNull(dto.getUserName());
    dto.setUserName("userName");
    assertEquals("userName", dto.getUserName());
  }

  @Test
  void testAccessDisplayName() {
    assertNull(dto.getDisplayName());
    dto.setDisplayName("displayName");
    assertEquals("displayName", dto.getDisplayName());
  }

  @Test
  void testAccessFirstName() {
    assertNull(dto.getFirstName());
    dto.setFirstName("firstName");
    assertEquals("firstName", dto.getFirstName());
  }

  @Test
  void testAccessLastName() {
    assertNull(dto.getLastName());
    dto.setLastName("lastName");
    assertEquals("lastName", dto.getLastName());
  }

  @Test
  void testAccessEmail() {
    assertNull(dto.getEmail());
    dto.setEmail("email");
    assertEquals("email", dto.getEmail());
  }

  @Test
  void testAccessStatusId() {
    assertNull(dto.getStatusId());
    dto.setStatusId(1L);
    assertEquals(1L, dto.getStatusId());
  }

  @Test
  void testAccessSalutationId() {
    assertNull(dto.getSalutationId());
    dto.setSalutationId(10L);
    assertEquals(10L, dto.getSalutationId());
  }

  @Test
  void testAccessPhoneNumber() {
    assertNull(dto.getPhoneNumber());
    dto.setPhoneNumber("phoneNumber");
    assertEquals("phoneNumber", dto.getPhoneNumber());
  }

  @Test
  void testAccessImageSrc() {
    assertNull(dto.getImageSrc());
    dto.setImageSrc(URI.create("imageSrc"));
    assertEquals(URI.create("imageSrc"), dto.getImageSrc());
  }

  @Test
  void testAccessRoleIds() {
    assertNull(dto.getRoleIds());
    dto.setRoleIds(List.of(10L));
    assertEquals(List.of(10L), dto.getRoleIds());

    dto.setRoleIds(List.of(20L));
    assertEquals(List.of(20L), dto.getRoleIds());
  }
}
