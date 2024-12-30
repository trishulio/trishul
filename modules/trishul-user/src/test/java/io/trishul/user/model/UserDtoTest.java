package io.trishul.user.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.trishul.object.store.file.model.dto.IaasObjectStoreFileDto;
import io.trishul.user.role.model.UserRoleDto;
import io.trishul.user.salutation.model.UserSalutationDto;
import io.trishul.user.status.UserStatusDto;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDtoTest {
  private UserDto dto;

  @BeforeEach
  public void init() {
    dto = new UserDto();
  }

  @Test
  public void testAllArgConstructor_SetsAllFields() {
    dto = new UserDto(1L, "USER_NAME", "DISPLAY_NAME", "FIRST_NAME", "LAST_NAME", "EMAIL",
        "PHONE_NUMBER", URI.create("IMAGE_SRC"), new IaasObjectStoreFileDto(URI.create("file.txt")),
        new UserStatusDto(1L), new UserSalutationDto(2L), List.of(new UserRoleDto(3L)),
        LocalDateTime.of(1999, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 1, 0, 0), 1);

    assertEquals(1L, dto.getId());
    assertEquals("USER_NAME", dto.getUserName());
    assertEquals("DISPLAY_NAME", dto.getDisplayName());
    assertEquals("FIRST_NAME", dto.getFirstName());
    assertEquals("LAST_NAME", dto.getLastName());
    assertEquals("EMAIL", dto.getEmail());
    assertEquals("PHONE_NUMBER", dto.getPhoneNumber());
    assertEquals(URI.create("IMAGE_SRC"), dto.getImageSrc());
    assertEquals(new IaasObjectStoreFileDto(URI.create("file.txt")), dto.getObjectStoreFile());
    assertEquals(new UserStatusDto(1L), dto.getStatus());
    assertEquals(new UserSalutationDto(2L), dto.getSalutation());
    assertEquals(List.of(new UserRoleDto(3L)), dto.getRoles());
    assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), dto.getCreatedAt());
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getLastUpdated());
    assertEquals(1, dto.getVersion());
  }

  @Test
  public void testAccessId() {
    assertNull(dto.getId());
    dto.setId(1L);
    assertEquals(1L, dto.getId());
  }

  @Test
  public void testAccessUserName() {
    assertNull(dto.getUserName());
    dto.setUserName("userName");
    assertEquals("userName", dto.getUserName());
  }

  @Test
  public void testAccessDisplayName() {
    assertNull(dto.getDisplayName());
    dto.setDisplayName("displayName");
    assertEquals("displayName", dto.getDisplayName());
  }

  @Test
  public void testAccessFirstName() {
    assertNull(dto.getFirstName());
    dto.setFirstName("firstName");
    assertEquals("firstName", dto.getFirstName());
  }

  @Test
  public void testAccessLastName() {
    assertNull(dto.getLastName());
    dto.setLastName("lastName");
    assertEquals("lastName", dto.getLastName());
  }

  @Test
  public void testAccessEmail() {
    assertNull(dto.getEmail());
    dto.setEmail("email");
    assertEquals("email", dto.getEmail());
  }

  @Test
  public void testAccessImageSrc() {
    assertNull(dto.getImageSrc());
    dto.setImageSrc(URI.create("imageSrc"));
    assertEquals(URI.create("imageSrc"), dto.getImageSrc());
  }

  @Test
  public void testAccessObjectStoreFile() {
    assertNull(dto.getObjectStoreFile());
    dto.setObjectStoreFile(new IaasObjectStoreFileDto(URI.create("file.txt")));

    assertEquals(new IaasObjectStoreFileDto(URI.create("file.txt")), dto.getObjectStoreFile());
  }

  @Test
  public void testAccessPhoneNumber() {
    assertNull(dto.getPhoneNumber());
    dto.setPhoneNumber("phoneNumber");
    assertEquals("phoneNumber", dto.getPhoneNumber());
  }

  @Test
  public void testAccessStatus() {
    assertNull(dto.getStatus());
    dto.setStatus(new UserStatusDto(1L));
    assertEquals(new UserStatusDto(1L), dto.getStatus());
  }

  @Test
  public void testAccessSalutation() {
    assertNull(dto.getSalutation());
    dto.setSalutation(new UserSalutationDto(1L));
    assertEquals(new UserSalutationDto(1L), dto.getSalutation());
  }

  @Test
  public void testAccessRoles() {
    assertNull(dto.getRoles());
    dto.setRoles(List.of(new UserRoleDto(1L), new UserRoleDto(2L)));
    assertEquals(List.of(new UserRoleDto(1L), new UserRoleDto(2L)), dto.getRoles());

    dto.setRoles(List.of(new UserRoleDto(10L), new UserRoleDto(20L)));
    assertEquals(List.of(new UserRoleDto(10L), new UserRoleDto(20L)), dto.getRoles());
  }

  @Test
  public void testAccessLastUpdated() {
    assertNull(dto.getLastUpdated());
    dto.setLastUpdated(LocalDateTime.of(1999, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), dto.getLastUpdated());
  }

  @Test
  public void testAccessCreatedAt() {
    assertNull(dto.getCreatedAt());
    dto.setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getCreatedAt());
  }

  @Test
  public void testAccessVersion() {
    assertNull(dto.getVersion());
    dto.setVersion(1);
    assertEquals(1, dto.getVersion());
  }
}
