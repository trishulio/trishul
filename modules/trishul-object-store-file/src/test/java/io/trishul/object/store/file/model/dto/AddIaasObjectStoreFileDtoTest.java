package io.company.brewcraft.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddIaasObjectStoreFileDtoTest {
    private AddIaasObjectStoreFileDto dto;

    @BeforeEach
    public void init() {
        dto = new AddIaasObjectStoreFileDto();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(dto.getMinValidUntil());
    }

    @Test
    public void testAllArgConstructor() throws MalformedURLException {
        dto = new AddIaasObjectStoreFileDto(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getMinValidUntil());
    }

    @Test
    public void testAccessMinValidUntil() {
        dto.setMinValidUntil(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getMinValidUntil());
    }
}
