package io.trishul.object.store.file.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IaasObjectStoreFileTest {
  private IaasObjectStoreFile file;

  @BeforeEach
  void init() {
    file = new IaasObjectStoreFile();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(file.getFileKey());
    assertNull(file.getExpiration());
    assertNull(file.getFileUrl());
    assertNull(file.getVersion());
  }

  @Test
  void testAllArgConstructor() throws MalformedURLException {
    file = new IaasObjectStoreFile(URI.create("file.txt"), LocalDateTime.of(2000, 1, 1, 0, 0),
        URI.create("http://localhost/").toURL());

    assertEquals(URI.create("file.txt"), file.getFileKey());
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), file.getExpiration());
    assertEquals(URI.create("http://localhost/").toURL(), file.getFileUrl());
    assertNull(file.getVersion());
  }

  @Test
  void testGetSetId() {
    file.setId(URI.create("file.txt"));
    assertEquals(URI.create("file.txt"), file.getId());
  }

  @Test
  void testGetSetFileKey() {
    file.setFileKey(URI.create("file.txt"));
    assertEquals(URI.create("file.txt"), file.getFileKey());
  }

  @Test
  void testGetSetExpiration() {
    file.setExpiration(LocalDateTime.of(2000, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), file.getExpiration());
  }

  @Test
  void testGetSetFileUrl() throws MalformedURLException {
    file.setFileUrl(URI.create("http://localhost/").toURL());
    assertEquals(URI.create("http://localhost/").toURL(), file.getFileUrl());
  }

  @Test
  void testSetMinValidUntil() {
    assertSame(file, file.setMinValidUntil(LocalDateTime.of(2000, 1, 1, 5, 0)));
    assertEquals(LocalDateTime.of(2000, 1, 1, 6, 0), file.getExpiration());

    file.setMinValidUntil(LocalDateTime.of(2000, 1, 1, 5, 15));
    assertEquals(LocalDateTime.of(2000, 1, 1, 6, 0), file.getExpiration());

    file.setMinValidUntil(LocalDateTime.of(2000, 1, 1, 5, 30));
    assertEquals(LocalDateTime.of(2000, 1, 1, 6, 00), file.getExpiration());

    file.setMinValidUntil(LocalDateTime.of(2000, 1, 1, 5, 31));
    assertEquals(LocalDateTime.of(2000, 1, 1, 7, 00), file.getExpiration());

    file.setMinValidUntil(LocalDateTime.of(2000, 1, 1, 5, 45));
    assertEquals(LocalDateTime.of(2000, 1, 1, 7, 0), file.getExpiration());

    file.setMinValidUntil(LocalDateTime.of(2000, 1, 1, 6, 0));
    assertEquals(LocalDateTime.of(2000, 1, 1, 7, 0), file.getExpiration());
  }

  @Test
  void testAccessId() throws Exception {
    IaasObjectStoreFile accessor = new IaasObjectStoreFile();
    assertSame(accessor, accessor.setId(URI.create("http://localhost")));
    assertEquals(URI.create("http://localhost"), accessor.getId());
  }

  @Test
  void testAccessFileKey() throws Exception {
    IaasObjectStoreFile accessor = new IaasObjectStoreFile();
    assertSame(accessor, accessor.setFileKey(URI.create("http://localhost")));
    assertEquals(URI.create("http://localhost"), accessor.getFileKey());
  }

  @Test
  void testAccessExpiration() throws Exception {
    IaasObjectStoreFile accessor = new IaasObjectStoreFile();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setExpiration(value));
    assertEquals(value, accessor.getExpiration());
  }

  @Test
  void testAccessFileUrl() throws Exception {
    IaasObjectStoreFile accessor = new IaasObjectStoreFile();
    assertSame(accessor, accessor.setFileUrl(URI.create("http://localhost").toURL()));
    assertEquals(URI.create("http://localhost").toURL(), accessor.getFileUrl());
  }

}
