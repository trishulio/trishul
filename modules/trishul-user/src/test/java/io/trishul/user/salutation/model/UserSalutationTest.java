package io.trishul.user.salutation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserSalutationTest {
  private UserSalutation salutation;

  @BeforeEach
  public void init() {
    salutation = new UserSalutation();
  }

  @Test
  public void testAllArgConstructor() {
    salutation = new UserSalutation(1L, "TITLE", LocalDateTime.of(1999, 1, 1, 0, 0),
        LocalDateTime.of(2000, 1, 1, 0, 0), 1);

    assertEquals(1L, salutation.getId());
    assertEquals("TITLE", salutation.getTitle());
    assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), salutation.getCreatedAt());
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), salutation.getLastUpdated());
    assertEquals(1, salutation.getVersion());
  }

  @Test
  public void testAccessId() {
    assertNull(salutation.getId());
    salutation.setId(1L);
    assertEquals(1L, salutation.getId());
  }

  @Test
  public void testAccessTitle() {
    assertNull(salutation.getTitle());
    salutation.setTitle("title");
    assertEquals("title", salutation.getTitle());
  }

  @Test
  public void testAccessCreatedAt() {
    assertNull(salutation.getCreatedAt());
    salutation.setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), salutation.getCreatedAt());
  }

  @Test
  public void testAccessLastUpdated() {
    assertNull(salutation.getLastUpdated());
    salutation.setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), salutation.getLastUpdated());
  }

  @Test
  public void testAccessVersion() {
    assertNull(salutation.getVersion());
    salutation.setVersion(1);
    assertEquals(1, salutation.getVersion());
  }
}
