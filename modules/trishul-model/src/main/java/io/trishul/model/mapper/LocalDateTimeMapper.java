package io.trishul.model.mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateTimeMapper {
  public static final LocalDateTimeMapper INSTANCE = new LocalDateTimeMapper();

  private final ZoneId zoneId;

  public LocalDateTimeMapper() {
    this(ZoneId.systemDefault());
  }

  protected LocalDateTimeMapper(ZoneId zoneId) {
    this.zoneId = zoneId;
  }

  public LocalDateTime fromUtilDate(Date date) {
    LocalDateTime ldt = null;

    if (date != null) {
      ldt = LocalDateTime.ofInstant(date.toInstant(), this.zoneId);
    }

    return ldt;
  }

  public Date toUtilDate(LocalDateTime dt) {
    Date date = null;

    if (dt != null) {
      date = Date.from(dt.atZone(this.zoneId).toInstant());
    }

    return date;
  }
}
