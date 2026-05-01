package io.trishul.object.store.file.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IaasObjectStoreFileDtoTest {
  private IaasObjectStoreFileDto dto;

  @BeforeEach
  void init() {
    dto = new IaasObjectStoreFileDto();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(dto.getFileKey());
    assertNull(dto.getFileUrl());
    assertNull(dto.getExpiration());
  }

  @Test
  void testAllArgConstructor() throws MalformedURLException {
    dto = new IaasObjectStoreFileDto(URI.create("file.txt"), LocalDateTime.of(2000, 1, 1, 0, 0),
        URI.create("http://localhost/").toURL());

    assertEquals(URI.create("file.txt"), dto.getFileKey());
    assertEquals(URI.create("http://localhost/").toURL(), dto.getFileUrl());
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getExpiration());
  }

  @Test
  void testAccessFileKey() {
    dto.setFileKey(URI.create("file.txt"));
    assertEquals(URI.create("file.txt"), dto.getFileKey());
  }

  @Test
  void testAccessUrl() throws MalformedURLException {
    dto.setFileUrl(URI.create("http://localhost/").toURL());
    assertEquals(URI.create("http://localhost/").toURL(), dto.getFileUrl());
  }

  @Test
  void testAccessExpiration() {
    dto.setExpiration(LocalDateTime.of(2000, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getExpiration());
  }
}
