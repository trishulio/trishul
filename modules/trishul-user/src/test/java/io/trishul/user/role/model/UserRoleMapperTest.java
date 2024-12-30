package io.trishul.user.role.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserRoleMapperTest {
  private UserRoleMapper mapper;

  @BeforeEach
  public void init() {
    mapper = UserRoleMapper.INSTANCE;
  }

  @Test
  public void testFromDto_CreatesRoleWithId_WhenIdIsNotNull() {
    UserRole role = mapper.fromDto(1L);
    UserRole expected = new UserRole().setId(1L);
    assertEquals(expected, role);
  }

  @Test
  public void testFromDto_ReturnsNull_WhenIdIsNull() {
    assertNull(mapper.fromDto((Long) null));
  }

  @Test
  public void testToDto_ReturnsRoleDto_WhenPojoIsNotNull() {
    UserRole role
        = new UserRole().setId(1L).setName("NAME").setCreatedAt(LocalDateTime.of(1999, 1, 1, 0, 0))
            .setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0)).setVersion(1);

    UserRoleDto dto = mapper.toDto(role);
    UserRoleDto expected = new UserRoleDto().setId(1L).setName("NAME")
        .setCreatedAt(LocalDateTime.of(1999, 1, 1, 0, 0))
        .setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0)).setVersion(1);

    assertEquals(expected, dto);
  }

  @Test
  public void testToDto_ReturnsNull_WhenPojoIsNull() {
    assertNull(mapper.toDto(null));
  }

  @Test
  public void testFromUpdateDto_ReturnsNull_WhenArgisNull() {
    assertNull(mapper.fromUpdateDto(null));
  }

  @Test
  public void testFromUpdateDto_ReturnsPojo_WhenArgIsNotNll() {
    UpdateUserRoleDto arg = new UpdateUserRoleDto().setId(1L).setName("NAME").setVersion(1);

    UserRole expected = new UserRole().setId(1L).setName("NAME").setVersion(1);

    assertEquals(expected, mapper.fromUpdateDto(arg));
  }

  @Test
  public void testFromAddDto_ReturnsNull_WhenArgisNull() {
    assertNull(mapper.fromAddDto(null));
  }

  @Test
  public void testFromAddDto_ReturnsPojo_WhenArgIsNotNll() {
    AddUserRoleDto arg = new AddUserRoleDto().setName("NAME");
    UserRole expected = new UserRole().setName("NAME");
    assertEquals(expected, mapper.fromAddDto(arg));
  }
}
