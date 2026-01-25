package io.trishul.user.status;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserStatusDtoTest {
  private UserStatusDto dto;

  @BeforeEach
  void init() {
    dto = new UserStatusDto();
  }

  @Test
  void testAllArgConstructor_SetsAllFields() {
    dto = new UserStatusDto(1L, "STATUS", LocalDateTime.of(1999, 1, 1, 0, 0),
        LocalDateTime.of(2000, 1, 1, 0, 0), 1);

    assertEquals(1L, dto.getId());
    assertEquals("STATUS", dto.getName());
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
    dto.setName("NAME");
    assertEquals("NAME", dto.getName());
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
    dto.setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), dto.getLastUpdated());
  }

  @Test
  void testAccessVersion() {
    assertNull(dto.getVersion());
    dto.setVersion(1);
    assertEquals(1, dto.getVersion());
  }
}
