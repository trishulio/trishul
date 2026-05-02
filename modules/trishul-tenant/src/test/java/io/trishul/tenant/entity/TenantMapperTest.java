package io.trishul.tenant.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.trishul.tenant.dto.AddTenantDto;
import io.trishul.tenant.dto.TenantDto;
import io.trishul.tenant.dto.UpdateTenantDto;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TenantMapperTest {
  private TenantMapper mapper;

  @BeforeEach
  void init() {
    mapper = TenantMapper.INSTANCE;
  }

  @Test
  void testToDto_ReturnsNull_WhenPojoIsNull() {
    assertNull(mapper.toDto(null));
  }

  @Test
  void testToDto_ReturnsDto_WhenPojoIsNotNull() throws MalformedURLException {
    Tenant tenant = new Tenant().setId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
        .setName("TENANT_1").setUrl(URI.create("http://localhost/").toURL()).setIsReady(true)
        .setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0))
        .setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0));

    TenantDto dto = mapper.toDto(tenant);

    TenantDto expected = new TenantDto(UUID.fromString("00000000-0000-0000-0000-000000000001"),
        "TENANT_1", URI.create("http://localhost/").toURL(), true,
        LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 1, 0, 0));

    assertEquals(expected, dto);
  }

  @Test
  void testFromUpdateDto_ReturnsNull_WhenDtoIsNull() {
    assertNull(mapper.fromUpdateDto(null));
  }

  @Test
  void testFromUpdateDto_ReturnsPojo_WhenDtoIsNotNull() throws MalformedURLException {
    UpdateTenantDto dto
        = new UpdateTenantDto(UUID.fromString("00000000-0000-0000-0000-000000000001"), "TENANT_1",
            URI.create("http://localhost/").toURL());

    Tenant tenant = mapper.fromUpdateDto(dto);

    Tenant expected = new Tenant().setId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
        .setName("TENANT_1").setUrl(URI.create("http://localhost/").toURL()).setIsReady(false);
    assertEquals(expected, tenant);
  }

  @Test
  void testFromAddDto_ReturnsNull_WhenDtoIsNull() {
    assertNull(mapper.fromAddDto(null));
  }

  @Test
  void testFromAddDto_ReturnsPojo_WhenDtoIsNotNull() throws MalformedURLException {
    AddTenantDto dto = new AddTenantDto("TENANT_1", URI.create("http://localhost/").toURL());
    Tenant tenant = mapper.fromAddDto(dto);

    Tenant expected = new Tenant().setName("TENANT_1")
        .setUrl(URI.create("http://localhost/").toURL()).setIsReady(false);
    assertEquals(expected, tenant);
  }
}
