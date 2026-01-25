package io.trishul.user.salutation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserSalutationTest {
  private UserSalutation salutation;

  @BeforeEach
  void init() {
    salutation = new UserSalutation();
  }

  @Test
  void testAllArgConstructor() {
    salutation = new UserSalutation(1L, "TITLE", LocalDateTime.of(1999, 1, 1, 0, 0),
        LocalDateTime.of(2000, 1, 1, 0, 0), 1);

    assertEquals(1L, salutation.getId());
    assertEquals("TITLE", salutation.getTitle());
    assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), salutation.getCreatedAt());
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), salutation.getLastUpdated());
    assertEquals(1, salutation.getVersion());
  }

  @Test
  void testAccessId() {
    assertNull(salutation.getId());
    salutation.setId(1L);
    assertEquals(1L, salutation.getId());
  }

  @Test
  void testAccessTitle() {
    assertNull(salutation.getTitle());
    salutation.setTitle("title");
    assertEquals("title", salutation.getTitle());
  }

  @Test
  void testAccessCreatedAt() {
    assertNull(salutation.getCreatedAt());
    salutation.setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), salutation.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() {
    assertNull(salutation.getLastUpdated());
    salutation.setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), salutation.getLastUpdated());
  }

  @Test
  void testAccessVersion() {
    assertNull(salutation.getVersion());
    salutation.setVersion(1);
    assertEquals(1, salutation.getVersion());
  }
}
