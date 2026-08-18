package io.trishul.iaas.idp.tenant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import io.trishul.iaas.access.role.model.IaasRole;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IaasIdpTenantTest {
  private IaasIdpTenant idpTenant;

  @BeforeEach
  void init() {
    idpTenant = new IaasIdpTenant();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(idpTenant.getId());
    assertNull(idpTenant.getName());
    assertNull(idpTenant.getDescription());
    assertNull(idpTenant.getIaasRole());
    assertNull(idpTenant.getLastUpdated());
    assertNull(idpTenant.getCreatedAt());
    assertNull(idpTenant.getVersion());
  }

  @Test
  void testAllArgConstructor() {
    idpTenant = new IaasIdpTenant("ID", new IaasRole("ROLE"), "DESCRIPTION",
        LocalDateTime.of(2002, 1, 1, 0, 0), LocalDateTime.of(2003, 1, 1, 0, 0));

    assertEquals("ID", idpTenant.getId());
    assertEquals("ID", idpTenant.getName());
    assertEquals(new IaasRole("ROLE"), idpTenant.getIaasRole());
    assertEquals("DESCRIPTION", idpTenant.getDescription());
    assertEquals(LocalDateTime.of(2002, 1, 1, 0, 0), idpTenant.getCreatedAt());
    assertEquals(LocalDateTime.of(2003, 1, 1, 0, 0), idpTenant.getLastUpdated());
  }

  @Test
  void testGetSetId() {
    idpTenant.setId("ID");
    assertEquals("ID", idpTenant.getId());
  }

  @Test
  void testGetSetName() {
    idpTenant.setName("NAME");
    assertEquals("NAME", idpTenant.getName());
  }

  @Test
  void testGetSetDescription() {
    idpTenant.setDescription("DESCRIPTION");
    assertEquals("DESCRIPTION", idpTenant.getDescription());
  }

  @Test
  void testGetSetIaasRole() {
    idpTenant.setIaasRole(new IaasRole("ROLE"));
    assertEquals(new IaasRole("ROLE"), idpTenant.getIaasRole());
  }

  @Test
  void testGetSetCreatedAt() {
    idpTenant.setCreatedAt(LocalDateTime.of(2001, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), idpTenant.getCreatedAt());
  }

  @Test
  void testGetSetLastUpdated() {
    idpTenant.setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), idpTenant.getLastUpdated());
  }

  @Test
  void testAccessId() throws Exception {
    IaasIdpTenant accessor = new IaasIdpTenant();
    assertSame(accessor, accessor.setId("testString"));
    assertEquals("testString", accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    IaasIdpTenant accessor = new IaasIdpTenant();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessDescription() throws Exception {
    IaasIdpTenant accessor = new IaasIdpTenant();
    assertSame(accessor, accessor.setDescription("testString"));
    assertEquals("testString", accessor.getDescription());
  }

  @Test
  void testAccessIaasRole() throws Exception {
    IaasIdpTenant accessor = new IaasIdpTenant();
    IaasRole value = new IaasRole();
    assertSame(accessor, accessor.setIaasRole(value));
    assertEquals(value, accessor.getIaasRole());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    IaasIdpTenant accessor = new IaasIdpTenant();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    IaasIdpTenant accessor = new IaasIdpTenant();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

  @Test
  void testAccessVersion() throws Exception {
    IaasIdpTenant accessor = new IaasIdpTenant();
    assertSame(accessor, accessor.setVersion(123));
    assertNull(accessor.getVersion());
  }

}
