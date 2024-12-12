package io.company.brewcraft.service.mapper;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddTenantDto;
import io.company.brewcraft.dto.TenantDto;
import io.company.brewcraft.dto.UpdateTenantDto;
import io.company.brewcraft.model.Tenant;

public class TenantMapperTest {
    private TenantMapper mapper;

    @BeforeEach
    public void init() {
        mapper = TenantMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() throws MalformedURLException {
        Tenant tenant = new Tenant(
            UUID.fromString("00000000-0000-0000-0000-000000000001"),
            "TENANT_1",
            new URL("http://localhost/"),
            true,
            LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0)
        );

        TenantDto dto = mapper.toDto(tenant);

        TenantDto expected = new TenantDto(
            UUID.fromString("00000000-0000-0000-0000-000000000001"),
            "TENANT_1",
            new URL("http://localhost/"),
            true,
            LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0)
        );

        assertEquals(expected, dto);
    }

    @Test
    public void testFromUpdateDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromUpdateDto(null));
    }

    @Test
    public void testFromUpdateDto_ReturnsPojo_WhenDtoIsNotNull() throws MalformedURLException {
        UpdateTenantDto dto = new UpdateTenantDto(
            UUID.fromString("00000000-0000-0000-0000-000000000001"),
            "TENANT_1",
            new URL("http://localhost/")
        );

        Tenant tenant = mapper.fromUpdateDto(dto);

        Tenant expected = new Tenant(
            UUID.fromString("00000000-0000-0000-0000-000000000001"),
            "TENANT_1",
            new URL("http://localhost/"),
            false,
            null,
            null
        );
        assertEquals(expected, tenant);
    }

    @Test
    public void testFromAddDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromAddDto(null));
    }

    @Test
    public void testFromAddDto_ReturnsPojo_WhenDtoIsNotNull() throws MalformedURLException {
        AddTenantDto dto = new AddTenantDto(
            "TENANT_1",
            new URL("http://localhost/")
        );

        Tenant tenant = mapper.fromAddDto(dto);

        Tenant expected = new Tenant(
            null,
            "TENANT_1",
            new URL("http://localhost/"),
            false,
            null,
            null
        );
        assertEquals(expected, tenant);
    }
}
