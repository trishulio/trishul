package io.trishul.object.store.file.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.trishul.object.store.file.model.dto.AddIaasObjectStoreFileDto;
import io.trishul.object.store.file.model.dto.IaasObjectStoreFileDto;
import io.trishul.object.store.file.model.dto.UpdateIaasObjectStoreFileDto;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IaasObjectStoreFileMapperTest {
    private IaasObjectStoreFileMapper mapper;

    @BeforeEach
    public void init() {
        mapper = IaasObjectStoreFileMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() throws MalformedURLException {
        IaasObjectStoreFile tenant =
                new IaasObjectStoreFile(
                        URI.create("file.txt"),
                        LocalDateTime.of(2000, 1, 1, 0, 0),
                        new URL("http://localhost/"));

        IaasObjectStoreFileDto dto = mapper.toDto(tenant);

        IaasObjectStoreFileDto expected =
                new IaasObjectStoreFileDto(
                        URI.create("file.txt"),
                        LocalDateTime.of(2000, 1, 1, 0, 0),
                        new URL("http://localhost/"));

        assertEquals(expected, dto);
    }

    @Test
    public void testFromUpdateDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromUpdateDto(null));
    }

    @Test
    public void testFromUpdateDto_ReturnsPojo_WhenDtoIsNotNull() throws MalformedURLException {
        UpdateIaasObjectStoreFileDto dto =
                new UpdateIaasObjectStoreFileDto(
                        URI.create("file.txt"), LocalDateTime.of(2000, 1, 1, 5, 15));

        IaasObjectStoreFile tenant = mapper.fromUpdateDto(dto);

        IaasObjectStoreFile expected =
                new IaasObjectStoreFile(
                        URI.create("file.txt"), LocalDateTime.of(2000, 1, 1, 6, 0), null);
        assertEquals(expected, tenant);
    }

    @Test
    public void testFromAddDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromAddDto(null));
    }

    @Test
    public void testFromAddDto_ReturnsPojo_WhenDtoIsNotNull() throws MalformedURLException {
        AddIaasObjectStoreFileDto dto =
                new AddIaasObjectStoreFileDto(LocalDateTime.of(2000, 1, 1, 5, 15));

        IaasObjectStoreFile tenant = mapper.fromAddDto(dto);

        IaasObjectStoreFile expected =
                new IaasObjectStoreFile(null, LocalDateTime.of(2000, 1, 1, 6, 0), null);
        assertEquals(expected, tenant);
    }
}
