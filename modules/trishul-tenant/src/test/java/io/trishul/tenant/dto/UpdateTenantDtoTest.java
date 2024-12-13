package io.trishul.tenant.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateTenantDtoTest {
    private UpdateTenantDto tenantDto;

    @BeforeEach
    public void init() {
        tenantDto = new UpdateTenantDto();
    }

    @Test
    public void testAllArgConstructor() throws MalformedURLException {
        tenantDto = new UpdateTenantDto(UUID.fromString("00000000-0000-0000-0000-000000000000"), "NAME", new URL("http://localhost/"));

        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), tenantDto.getId());
        assertEquals("NAME", tenantDto.getName());
        assertEquals(new URL("http://localhost/"), tenantDto.getUrl());
    }

    @Test
    public void testGetSetId() {
        UUID id = UUID.fromString("89efec46-fd0b-4fec-bcde-7f4bcef4f8e9");
        tenantDto.setId(id);
        assertSame(id, tenantDto.getId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        tenantDto.setName(name);
        assertSame(name, tenantDto.getName());
    }

    @Test
    public void testGetSetUrl() throws MalformedURLException {
        URL url = new URL("https://localhost/");
        tenantDto.setUrl(url);
        assertSame(url, tenantDto.getUrl());
    }
}
