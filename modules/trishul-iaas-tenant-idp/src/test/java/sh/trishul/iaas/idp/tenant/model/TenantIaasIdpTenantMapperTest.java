package sh.trishul.iaas.idp.tenant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.tenant.entity.Tenant;

class TenantIaasIdpTenantMapperTest {
  private TenantIaasIdpTenantMapper mapper;

  @BeforeEach
  void init() {
    mapper = TenantIaasIdpTenantMapper.INSTANCE;
  }

  @Test
  void testFromTenants_ReturnsNull_WhenArgIsNull() {
    assertNull(mapper.fromTenants(null));
  }

  @Test
  void testFromTenants_ReturnsList_WhenArgIsNotNull() {
    List<Tenant> tenants
        = List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001"), "TENANT_1",
            URI.create("http://localhost/"), true, LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0)));

    List<? extends BaseIaasIdpTenant<?>> idpTenants = mapper.fromTenants(tenants);

    List<IaasIdpTenant> expected
        = List.of(new IaasIdpTenant("00000000-0000-0000-0000-000000000001"));
    assertEquals(expected, idpTenants);
  }

  @Test
  void testFromTenants_ReturnsListWithNoId_WhenArgIsNotNull() {
    List<Tenant> tenants = List.of(new Tenant());

    List<? extends BaseIaasIdpTenant<?>> idpTenants = mapper.fromTenants(tenants);

    List<IaasIdpTenant> expected = List.of(new IaasIdpTenant());
    assertEquals(expected, idpTenants);
  }

  @Test
  void testToIaasTenantIds_ReturnsNull_WhenArgIsNull() {
    assertNull(mapper.toIaasTenantIds(null));
  }

  @Test
  void testToIaasTenantIds_ReturnsSet_WhenArgIsNotNull() {
    Set<UUID> ids = Set.of(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    assertEquals(Set.of("00000000-0000-0000-0000-000000000001"), mapper.toIaasTenantIds(ids));
  }

  @Test
  void testFromTenant_ReturnsNull_WhenArgIsNull() {
    assertNull(mapper.fromTenant(null));
  }

  @Test
  void testFromTenant_ReturnsIdpTenant_WhenArgIsNotNull() {
    Tenant tenant = new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001"), "TENANT_1",
        URI.create("http://localhost/"), true, LocalDateTime.of(2000, 1, 1, 0, 0),
        LocalDateTime.of(2000, 1, 1, 0, 0));

    BaseIaasIdpTenant<?> idpTenant = mapper.fromTenant(tenant);

    IaasIdpTenant expected = new IaasIdpTenant("00000000-0000-0000-0000-000000000001");
    assertEquals(expected, idpTenant);
  }

  @Test
  void testFromTenant_ReturnsIdpTenantWithNoId_WhenTenantIdIsNull() {
    Tenant tenant = new Tenant();

    BaseIaasIdpTenant<?> idpTenant = mapper.fromTenant(tenant);

    IaasIdpTenant expected = new IaasIdpTenant();
    assertEquals(expected, idpTenant);
  }
}
