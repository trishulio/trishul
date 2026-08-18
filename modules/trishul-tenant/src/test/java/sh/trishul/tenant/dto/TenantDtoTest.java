package sh.trishul.tenant.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TenantDtoTest {
  private TenantDto tenantDto;

  @BeforeEach
  void init() {
    tenantDto = new TenantDto();
  }

  @Test
  void testAllArgConstructor() {
    tenantDto = new TenantDto(UUID.fromString("00000000-0000-0000-0000-000000000000"), "NAME",
        URI.create("http://localhost/"), true, LocalDateTime.of(2000, 1, 1, 0, 0),
        LocalDateTime.of(2001, 1, 1, 0, 0));

    assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), tenantDto.getId());
    assertEquals("NAME", tenantDto.getName());
    assertEquals(URI.create("http://localhost/"), tenantDto.getUrl());
    assertEquals(true, tenantDto.getIsReady());
    assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), tenantDto.getCreatedAt());
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), tenantDto.getLastUpdated());
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
  void testGetSetUrl() {
    URI url = URI.create("https://localhost/");
    tenantDto.setUrl(url);
    assertSame(url, tenantDto.getUrl());
  }

  @Test
  void testGetSetIsReady() {
    tenantDto.setIsReady(false);
    assertFalse(tenantDto.getIsReady());
    tenantDto.setIsReady(true);
    assertTrue(tenantDto.getIsReady());
  }

  @Test
  void testGetSetCreatedAt() {
    LocalDateTime created = LocalDateTime.now();
    tenantDto.setCreatedAt(created);
    assertSame(created, tenantDto.getCreatedAt());
  }

  @Test
  void testGetSetLastUpdated() {
    LocalDateTime lastUpdated = LocalDateTime.now();
    tenantDto.setLastUpdated(lastUpdated);
    assertSame(lastUpdated, tenantDto.getLastUpdated());
  }

  @Test
  void testAccessId() throws Exception {
    TenantDto accessor = new TenantDto();
    UUID value = UUID.randomUUID();
    assertSame(accessor, accessor.setId(value));
    assertEquals(value, accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    TenantDto accessor = new TenantDto();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessUrl() throws Exception {
    TenantDto accessor = new TenantDto();
    assertSame(accessor, accessor.setUrl(URI.create("http://localhost")));
    assertEquals(URI.create("http://localhost"), accessor.getUrl());
  }

  @Test
  void testAccessIsReady() throws Exception {
    TenantDto accessor = new TenantDto();
    assertSame(accessor, accessor.setIsReady(true));
    assertEquals(true, accessor.getIsReady());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    TenantDto accessor = new TenantDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    TenantDto accessor = new TenantDto();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

}
