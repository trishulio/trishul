package sh.trishul.object.store.file.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.MimeTypeUtils;

class UpdateIaasObjectStoreFileDtoTest {
  private UpdateIaasObjectStoreFileDto dto;

  @BeforeEach
  void init() {
    dto = new UpdateIaasObjectStoreFileDto();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(dto.getFileKey());
    assertNull(dto.getMinValidUntil());
    assertNull(dto.getMimeType());
  }

  @Test
  void testAllArgConstructor() throws MalformedURLException {
    dto = new UpdateIaasObjectStoreFileDto(URI.create("file.txt"),
        LocalDateTime.of(2000, 1, 1, 0, 0));

    assertEquals(URI.create("file.txt"), dto.getFileKey());
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getMinValidUntil());
    assertNull(dto.getMimeType());
  }

  @Test
  void testMimeTypeConstructor() throws MalformedURLException {
    dto = new UpdateIaasObjectStoreFileDto(URI.create("file.txt"),
        LocalDateTime.of(2000, 1, 1, 0, 0), MimeTypeUtils.IMAGE_PNG);

    assertEquals(URI.create("file.txt"), dto.getFileKey());
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getMinValidUntil());
    assertEquals(MimeTypeUtils.IMAGE_PNG, dto.getMimeType());
  }

  @Test
  void testAccessFileKey() {
    dto.setFileKey(URI.create("file.txt"));
    assertEquals(URI.create("file.txt"), dto.getFileKey());
  }

  @Test
  void testAccessMinValidUntil() {
    dto.setMinValidUntil(LocalDateTime.of(2000, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getMinValidUntil());
  }

  @Test
  void testAccessMimeType() {
    dto.setMimeType(MimeTypeUtils.IMAGE_PNG);
    assertEquals(MimeTypeUtils.IMAGE_PNG, dto.getMimeType());
  }
}
