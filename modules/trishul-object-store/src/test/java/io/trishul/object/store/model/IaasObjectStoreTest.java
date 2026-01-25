package io.trishul.object.store.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IaasObjectStoreTest {
  private IaasObjectStore objectStore;

  @BeforeEach
  void init() {
    objectStore = new IaasObjectStore();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(objectStore.getId());
    assertNull(objectStore.getName());
    assertNull(objectStore.getLastUpdated());
    assertNull(objectStore.getCreatedAt());
    assertNull(objectStore.getVersion());
  }

  @Test
  void testAllArgConstructor() {
    objectStore = new IaasObjectStore("ID", LocalDateTime.of(2002, 1, 1, 0, 0),
        LocalDateTime.of(2003, 1, 1, 0, 0));

    assertEquals("ID", objectStore.getId());
    assertEquals("ID", objectStore.getName());
    assertEquals(LocalDateTime.of(2002, 1, 1, 0, 0), objectStore.getCreatedAt());
    assertEquals(LocalDateTime.of(2003, 1, 1, 0, 0), objectStore.getLastUpdated());
  }

  @Test
  void testGetSetId() {
    objectStore.setId("ID");
    assertEquals("ID", objectStore.getId());
  }

  @Test
  void testGetSetName() {
    objectStore.setName("NAME");
    assertEquals("NAME", objectStore.getName());
  }

  @Test
  void testGetSetCreatedAt() {
    objectStore.setCreatedAt(LocalDateTime.of(2001, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), objectStore.getCreatedAt());
  }

  @Test
  void testGetSetLastUpdated() {
    objectStore.setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), objectStore.getLastUpdated());
  }
}
