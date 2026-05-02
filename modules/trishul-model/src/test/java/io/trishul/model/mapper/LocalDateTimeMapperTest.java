package io.trishul.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocalDateTimeMapperTest {
  private LocalDateTimeMapper mapper;

  @BeforeEach
  void init() {
    mapper = LocalDateTimeMapper.INSTANCE;
  }

  @Test
  void testFromUtilDate_ReturnsNull_WhenArgIsNull() {
    assertNull(mapper.fromUtilDate(null));
  }

  @Test
  void testFromUtilDate_ReturnsLocalDateTime_WhenDateIsNotNull() {
    LocalDateTime dt = mapper.fromUtilDate(toDate(1901, 2, 1));

    assertEquals(LocalDateTime.of(1901, 2, 1, 0, 0), dt);
  }

  @Test
  void testToUtilDate_ReturnsNull_WhenDateTimeIsNull() {
    assertNull(mapper.toUtilDate(null));
  }

  @Test
  void testToUtilDate_ReturnsDate_WhenLocalDateTimeIsNotNull() {
    Date date = mapper.toUtilDate(LocalDateTime.of(1901, 2, 1, 0, 0));

    assertEquals(toDate(1901, 2, 1), date);
  }

  private Date toDate(int year, int month, int dayOfMonth) {
    return Date.from(
        LocalDate.of(year, month, dayOfMonth).atStartOfDay(ZoneId.systemDefault()).toInstant());
  }
}
