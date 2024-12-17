package io.trishul.object.store.model;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IaasObjectStoreTest {
    private IaasObjectStore objectStore;

    @BeforeEach
    public void init() {
        objectStore = new IaasObjectStore();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(objectStore.getId());
        assertNull(objectStore.getName());
        assertNull(objectStore.getLastUpdated());
        assertNull(objectStore.getCreatedAt());
        assertNull(objectStore.getVersion());
    }

    @Test
    public void testAllArgConstructor() {
        objectStore = new IaasObjectStore("ID", LocalDateTime.of(2002, 1, 1, 0, 0), LocalDateTime.of(2003, 1, 1, 0, 0));

        assertEquals("ID", objectStore.getId());
        assertEquals("ID", objectStore.getName());
        assertEquals(LocalDateTime.of(2002, 1, 1, 0, 0), objectStore.getCreatedAt());
        assertEquals(LocalDateTime.of(2003, 1, 1, 0, 0), objectStore.getLastUpdated());
    }

    @Test
    public void testGetSetId() {
        objectStore.setId("ID");
        assertEquals("ID", objectStore.getId());
    }

    @Test
    public void testGetSetName() {
        objectStore.setName("NAME");
        assertEquals("NAME", objectStore.getName());
    }

    @Test
    public void testGetSetCreatedAt() {
        objectStore.setCreatedAt(LocalDateTime.of(2001, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), objectStore.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        objectStore.setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), objectStore.getLastUpdated());
    }
}
