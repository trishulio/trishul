package io.trishul.tenant.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddTenantDtoTest {
  private AddTenantDto tenantDto;

  @BeforeEach
  void init() {
    tenantDto = new AddTenantDto();
  }

  @Test
  void testAllArgConstructor() throws MalformedURLException {
    tenantDto = new AddTenantDto("NAME", URI.create("http://localhost/").toURL());

    assertEquals("NAME", tenantDto.getName());
    assertEquals(URI.create("http://localhost/").toURL(), tenantDto.getUrl());
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
