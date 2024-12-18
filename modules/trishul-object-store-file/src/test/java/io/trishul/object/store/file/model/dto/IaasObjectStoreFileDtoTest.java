package io.trishul.object.store.file.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IaasObjectStoreFileDtoTest {
    private IaasObjectStoreFileDto dto;

    @BeforeEach
    public void init() {
        dto = new IaasObjectStoreFileDto();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(dto.getFileKey());
        assertNull(dto.getFileUrl());
        assertNull(dto.getExpiration());
    }

    @Test
    public void testAllArgConstructor() throws MalformedURLException {
        dto =
                new IaasObjectStoreFileDto(
                        URI.create("file.txt"),
                        LocalDateTime.of(2000, 1, 1, 0, 0),
                        new URL("http://localhost/"));

        assertEquals(URI.create("file.txt"), dto.getFileKey());
        assertEquals(new URL("http://localhost/"), dto.getFileUrl());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getExpiration());
    }

    @Test
    public void testAccessFileKey() {
        dto.setFileKey(URI.create("file.txt"));
        assertEquals(URI.create("file.txt"), dto.getFileKey());
    }

    @Test
    public void testAccessUrl() throws MalformedURLException {
        dto.setFileUrl(new URL("http://localhost/"));
        assertEquals(new URL("http://localhost/"), dto.getFileUrl());
    }

    @Test
    public void testAccessExpiration() {
        dto.setExpiration(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getExpiration());
    }
}
