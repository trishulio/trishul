package io.trishul.tenant.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddTenantDtoTest {
    private AddTenantDto tenantDto;

    @BeforeEach
    public void init() {
        tenantDto = new AddTenantDto();
    }

    @Test
    public void testAllArgConstructor() throws MalformedURLException {
        tenantDto = new AddTenantDto("NAME", new URL("http://localhost/"));

        assertEquals("NAME", tenantDto.getName());
        assertEquals(new URL("http://localhost/"), tenantDto.getUrl());
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
