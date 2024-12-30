package io.trishul.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LocalDateTimeMapperTest {
  private LocalDateTimeMapper mapper;

  @BeforeEach
  public void init() {
    mapper = LocalDateTimeMapper.INSTANCE;
  }

  @Test
  public void testFromUtilDate_ReturnsNull_WhenArgIsNull() {
    assertNull(mapper.fromUtilDate(null));
  }

  @Test
  public void testFromUtilDate_ReturnsLocalDateTime_WhenDateIsNotNull() {
    LocalDateTime dt = mapper.fromUtilDate(new Date(1, 1, 1));

    assertEquals(LocalDateTime.of(1901, 2, 1, 0, 0), dt);
  }

  @Test
  public void testToUtilDate_ReturnsNull_WhenDateTimeIsNull() {
    assertNull(mapper.toUtilDate(null));
  }

  @Test
  public void testToUtilDate_ReturnsDate_WhenLocalDateTimeIsNotNull() {
    Date date = mapper.toUtilDate(LocalDateTime.of(1901, 2, 1, 0, 0));

    assertEquals(new Date(1, 1, 1), date);
  }
}
