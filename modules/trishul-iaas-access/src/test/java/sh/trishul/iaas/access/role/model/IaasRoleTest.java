package sh.trishul.iaas.access.role.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IaasRoleTest {
  private IaasRole role;

  @BeforeEach
  void init() {
    role = new IaasRole();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(role.getId());
    assertNull(role.getName());
    assertNull(role.getDescription());
    assertNull(role.getIaasId());
    assertNull(role.getIaasResourceName());
    assertNull(role.getAssumePolicyDocument());
    assertNull(role.getLastUpdated());
    assertNull(role.getCreatedAt());
    assertNull(role.getLastUsed());
    assertNull(role.getVersion());
  }

  @Test
  void testAllArgConstructor() {
    role = new IaasRole("ID", "DESCRIPTION", "DOCUMENT", "IAAS_RES_NAME", "IAAS_ID",
        LocalDateTime.of(2001, 1, 1, 0, 0), LocalDateTime.of(2002, 1, 1, 0, 0),
        LocalDateTime.of(2003, 1, 1, 0, 0));

    assertEquals("ID", role.getId());
    assertEquals("ID", role.getName());
    assertEquals("DESCRIPTION", role.getDescription());
    assertEquals("IAAS_ID", role.getIaasId());
    assertEquals("IAAS_RES_NAME", role.getIaasResourceName());
    assertEquals("DOCUMENT", role.getAssumePolicyDocument());
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), role.getLastUsed());
    assertEquals(LocalDateTime.of(2002, 1, 1, 0, 0), role.getCreatedAt());
    assertEquals(LocalDateTime.of(2003, 1, 1, 0, 0), role.getLastUpdated());
  }

  @Test
  void testGetSetId() {
    assertSame(role, role.setId("ID"));
    assertEquals("ID", role.getId());
  }

  @Test
  void testGetSetName() {
    assertSame(role, role.setName("NAME"));
    assertEquals("NAME", role.getName());
  }

  @Test
  void testGetSetDescription() {
    assertSame(role, role.setDescription("DESCRIPTION"));
    assertEquals("DESCRIPTION", role.getDescription());
  }

  @Test
  void testGetSetIaasId() {
    assertSame(role, role.setIaasId("IAAS_ID"));
    assertEquals("IAAS_ID", role.getIaasId());
  }

  @Test
  void testGetSetIaasResourceName() {
    assertSame(role, role.setIaasResourceName("IAAS_RES_NAME"));
    assertEquals("IAAS_RES_NAME", role.getIaasResourceName());
  }

  @Test
  void testGetSetAssumePolicyDocument() {
    assertSame(role, role.setAssumePolicyDocument("DOCUMENT"));
    assertEquals("DOCUMENT", role.getAssumePolicyDocument());
  }

  @Test
  void testGetSetLastUsed() {
    assertSame(role, role.setLastUsed(LocalDateTime.of(2001, 1, 1, 0, 0)));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), role.getLastUsed());
  }

  @Test
  void testGetSetCreatedAt() {
    assertSame(role, role.setCreatedAt(LocalDateTime.of(2001, 1, 1, 0, 0)));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), role.getCreatedAt());
  }

  @Test
  void testGetSetLastUpdated() {
    assertSame(role, role.setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0)));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), role.getLastUpdated());
  }

  @Test
  void testAccessId() throws Exception {
    IaasRole accessor = new IaasRole();
    assertSame(accessor, accessor.setId("testString"));
    assertEquals("testString", accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    IaasRole accessor = new IaasRole();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessDescription() throws Exception {
    IaasRole accessor = new IaasRole();
    assertSame(accessor, accessor.setDescription("testString"));
    assertEquals("testString", accessor.getDescription());
  }

  @Test
  void testAccessAssumePolicyDocument() throws Exception {
    IaasRole accessor = new IaasRole();
    assertSame(accessor, accessor.setAssumePolicyDocument("testString"));
    assertEquals("testString", accessor.getAssumePolicyDocument());
  }

  @Test
  void testAccessIaasResourceName() throws Exception {
    IaasRole accessor = new IaasRole();
    assertSame(accessor, accessor.setIaasResourceName("testString"));
    assertEquals("testString", accessor.getIaasResourceName());
  }

  @Test
  void testAccessIaasId() throws Exception {
    IaasRole accessor = new IaasRole();
    assertSame(accessor, accessor.setIaasId("testString"));
    assertEquals("testString", accessor.getIaasId());
  }

  @Test
  void testAccessLastUsed() throws Exception {
    IaasRole accessor = new IaasRole();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUsed(value));
    assertEquals(value, accessor.getLastUsed());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    IaasRole accessor = new IaasRole();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    IaasRole accessor = new IaasRole();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessVersion() throws Exception {
    IaasRole accessor = new IaasRole();
    assertSame(accessor, accessor.setVersion(123));
    assertNull(accessor.getVersion());
  }

}
