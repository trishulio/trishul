package io.trishul.object.store.file.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddIaasObjectStoreFileDtoTest {
  private AddIaasObjectStoreFileDto dto;

  @BeforeEach
  void init() {
    dto = new AddIaasObjectStoreFileDto();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(dto.getMinValidUntil());
  }

  @Test
  void testAllArgConstructor() throws MalformedURLException {
    dto = new AddIaasObjectStoreFileDto(LocalDateTime.of(2000, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getMinValidUntil());
  }

  @Test
  void testAccessMinValidUntil() {
    dto.setMinValidUntil(LocalDateTime.of(2000, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getMinValidUntil());
  }
}
