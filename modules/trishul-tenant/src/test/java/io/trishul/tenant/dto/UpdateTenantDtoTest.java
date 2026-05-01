package io.trishul.tenant.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UpdateTenantDtoTest {
  private UpdateTenantDto tenantDto;

  @BeforeEach
  void init() {
    tenantDto = new UpdateTenantDto();
  }

  @Test
  void testAllArgConstructor() throws MalformedURLException {
    tenantDto = new UpdateTenantDto(UUID.fromString("00000000-0000-0000-0000-000000000000"), "NAME",
        URI.create("http://localhost/").toURL());

    assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), tenantDto.getId());
    assertEquals("NAME", tenantDto.getName());
    assertEquals(URI.create("http://localhost/").toURL(), tenantDto.getUrl());
  }

  @Test
  void testGetSetId() {
    UUID id = UUID.fromString("89efec46-fd0b-4fec-bcde-7f4bcef4f8e9");
    tenantDto.setId(id);
    assertSame(id, tenantDto.getId());
  }

  @Test
  void testGetSetName() {
    String name = "testName";
    tenantDto.setName(name);
    assertSame(name, tenantDto.getName());
  }

  @Test
  void testGetSetUrl() throws MalformedURLException {
    URL url = URI.create("https://localhost/").toURL();
    tenantDto.setUrl(url);
    assertSame(url, tenantDto.getUrl());
  }
}
