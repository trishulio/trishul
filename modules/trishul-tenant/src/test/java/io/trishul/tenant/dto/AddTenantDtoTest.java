package io.trishul.tenant.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddTenantDtoTest {
  private AddTenantDto tenantDto;

  @BeforeEach
  void init() {
    tenantDto = new AddTenantDto();
  }

  @Test
  void testAllArgConstructor() {
    tenantDto = new AddTenantDto("NAME", URI.create("http://localhost/"));

    assertEquals("NAME", tenantDto.getName());
    assertEquals(URI.create("http://localhost/"), tenantDto.getUrl());
  }

  @Test
  void testGetSetName() {
    String name = "testName";
    tenantDto.setName(name);
    assertSame(name, tenantDto.getName());
  }

  @Test
  void testGetSetUrl() {
    URI url = URI.create("https://localhost/");
    tenantDto.setUrl(url);
    assertSame(url, tenantDto.getUrl());
  }
}
