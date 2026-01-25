package io.trishul.user.status;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserStatusMapperTest {
  private UserStatusMapper mapper;

  @BeforeEach
  void init() {
    mapper = UserStatusMapper.INSTANCE;
  }

  @Test
  void testFromDto_ReturnsStatus_WhenIdIsNotNull() {
    UserStatus status = mapper.fromDto(1L);

    UserStatus expected = new UserStatus(1L);

    assertEquals(expected, status);
  }

  @Test
  void testFromDto_ReturnNull_WhenIdIsNull() {
    assertNull(mapper.fromDto((Long) null));
  }

  @Test
  void testToDto_ReturnsDto_WhenPojoIsNotNull() {
    UserStatus status = new UserStatus(1L, "STATUS", LocalDateTime.of(1999, 1, 1, 0, 0),
        LocalDateTime.of(2000, 1, 1, 0, 0), 1);

    UserStatusDto dto = mapper.toDto(status);

    UserStatusDto expected = new UserStatusDto(1L, "STATUS", LocalDateTime.of(1999, 1, 1, 0, 0),
        LocalDateTime.of(2000, 1, 1, 0, 0), 1);

    assertEquals(expected, dto);
  }

  @Test
  void testToDto_ReturnsNull_WhenPojoIsNull() {
    assertNull(mapper.toDto(null));
  }
}
