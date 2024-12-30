package io.trishul.object.store.file.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateIaasObjectStoreFileDtoTest {
  private UpdateIaasObjectStoreFileDto dto;

  @BeforeEach
  public void init() {
    dto = new UpdateIaasObjectStoreFileDto();
  }

  @Test
  public void testNoArgConstructor() {
    assertNull(dto.getFileKey());
    assertNull(dto.getMinValidUntil());
  }

  @Test
  public void testAllArgConstructor() throws MalformedURLException {
    dto = new UpdateIaasObjectStoreFileDto(URI.create("file.txt"),
        LocalDateTime.of(2000, 1, 1, 0, 0));

    assertEquals(URI.create("file.txt"), dto.getFileKey());
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getMinValidUntil());
  }

  @Test
  public void testAccessFileKey() {
    dto.setFileKey(URI.create("file.txt"));
    assertEquals(URI.create("file.txt"), dto.getFileKey());
  }

  @Test
  public void testAccessMinValidUntil() {
    dto.setMinValidUntil(LocalDateTime.of(2000, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getMinValidUntil());
  }
}
