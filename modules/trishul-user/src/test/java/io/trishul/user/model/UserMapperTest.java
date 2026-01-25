package io.trishul.user.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.trishul.user.role.model.UserRole;
import io.trishul.user.role.model.UserRoleDto;
import io.trishul.user.salutation.model.UserSalutation;
import io.trishul.user.salutation.model.UserSalutationDto;
import io.trishul.user.status.UserStatus;
import io.trishul.user.status.UserStatusDto;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserMapperTest {
  private UserMapper mapper;

  @BeforeEach
  void init() {
    mapper = UserMapper.INSTANCE;
  }

  @Test
  void testFromDto_ReturnsNull_WhenIdIsNull() {
    assertNull(mapper.fromDto(null));
  }

  @Test
  void testFromDto_ReturnsPojo_WhenIdIsNotNull() {
    User expected = new User(1L);

    assertEquals(expected, mapper.fromDto(1L));
  }

  @Test
  void testFromAddDto_ReturnsEntity_WhenAddDtoIsNotNull() {
    AddUserDto dto = new AddUserDto("userName", "displayName", "firstName", "lastName", "email", 1L,
        2L, "phoneNumber", URI.create("imageSrc"), List.of(10L));

    User user = mapper.fromAddDto(dto);

    User expected = new User().setUserName("userName").setDisplayName("displayName")
        .setFirstName("firstName").setLastName("lastName").setEmail("email")
        .setPhoneNumber("phoneNumber").setImageSrc(URI.create("imageSrc")).setIaasUsername(null)
        .setStatus(new UserStatus(1L)).setSalutation(new UserSalutation(2L))
        .setRoles(List.of(new UserRole(10L)));

    assertEquals(expected, user);
  }

  @Test
  void testFromAddDto_ReturnsNull_WhenAddDtoIsNull() {
    assertNull(mapper.fromAddDto((AddUserDto) null));
  }

  @Test
  void testFromUpdateDto_ReturnsEntity_WhenUpdateUserDtoIsNotNull() {
    UpdateUserDto dto = new UpdateUserDto(1L, "userName", "displayName", "firstName", "lastName",
        "email", 1L, 2L, "phoneNumber", URI.create("imageSrc"), List.of(10L), 1);

    User user = mapper.fromUpdateDto(dto);

    User expected = new User().setId(1L).setUserName("userName").setDisplayName("displayName")
        .setFirstName("firstName").setLastName("lastName").setEmail("email")
        .setPhoneNumber("phoneNumber").setImageSrc(URI.create("imageSrc")).setIaasUsername(null)
        .setStatus(new UserStatus(1L)).setSalutation(new UserSalutation(2L))
        .setRoles(List.of(new UserRole(10L))).setVersion(1);

    assertEquals(expected, user);
  }

  @Test
  void testFromUpdateDto_ReturnsNull_WhenUpdateUserDtoIsNull() {
    assertNull(mapper.fromUpdateDto((UpdateUserDto) null));
  }

  @Test
  void testToDto_ReturnsDto_WhenEntityIsNotNull() {
    User user = new User(1L, "USER_NAME", "DISPLAY_NAME", "FIRST_NAME", "LAST_NAME", "EMAIL",
        "PHONE_NUMBER", URI.create("IMAGE_SRC"), null, new UserStatus(1L), new UserSalutation(2L),
        List.of(new UserRole(3L)), LocalDateTime.of(1999, 1, 1, 0, 0),
        LocalDateTime.of(2000, 1, 1, 0, 0), 1);

    UserDto dto = mapper.toDto(user);

    UserDto expected = new UserDto(1L, "USER_NAME", "DISPLAY_NAME", "FIRST_NAME", "LAST_NAME",
        "EMAIL", "PHONE_NUMBER", URI.create("IMAGE_SRC"), null, new UserStatusDto(1L),
        new UserSalutationDto(2L), List.of(new UserRoleDto(3L)), LocalDateTime.of(1999, 1, 1, 0, 0),
        LocalDateTime.of(2000, 1, 1, 0, 0), 1);

    assertEquals(expected, dto);
    assertNull(dto.getIaasUsername());
  }

  @Test
  void testToDto_MapsIaasUsername_WhenEntityHasValue() {
    User user = new User(1L, "USER_NAME", "DISPLAY_NAME", "FIRST_NAME", "LAST_NAME", "EMAIL",
        "PHONE_NUMBER", URI.create("IMAGE_SRC"), null, new UserStatus(1L), new UserSalutation(2L),
        List.of(new UserRole(3L)), LocalDateTime.of(1999, 1, 1, 0, 0),
        LocalDateTime.of(2000, 1, 1, 0, 0), 1);
    user.setIaasUsername("iaasUsername");

    UserDto dto = mapper.toDto(user);

    assertEquals("iaasUsername", dto.getIaasUsername());
  }

  @Test
  void testToDto_ReturnsNull_WhenPojoIsNull() {
    assertNull(mapper.toDto(null));
  }
}
