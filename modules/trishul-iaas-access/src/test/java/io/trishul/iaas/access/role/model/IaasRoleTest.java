package io.trishul.iaas.access.role.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    role.setId("ID");
    assertEquals("ID", role.getId());
  }

  @Test
  void testGetSetName() {
    role.setName("NAME");
    assertEquals("NAME", role.getName());
  }

  @Test
  void testGetSetDescription() {
    role.setDescription("DESCRIPTION");
    assertEquals("DESCRIPTION", role.getDescription());
  }

  @Test
  void testGetSetIaasId() {
    role.setIaasId("IAAS_ID");
    assertEquals("IAAS_ID", role.getIaasId());
  }

  @Test
  void testGetSetIaasResourceName() {
    role.setIaasResourceName("IAAS_RES_NAME");
    assertEquals("IAAS_RES_NAME", role.getIaasResourceName());
  }

  @Test
  void testGetSetAssumePolicyDocument() {
    role.setAssumePolicyDocument("DOCUMENT");
    assertEquals("DOCUMENT", role.getAssumePolicyDocument());
  }

  @Test
  void testGetSetLastUsed() {
    role.setLastUsed(LocalDateTime.of(2001, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), role.getLastUsed());
  }

  @Test
  void testGetSetCreatedAt() {
    role.setCreatedAt(LocalDateTime.of(2001, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), role.getCreatedAt());
  }

  @Test
  void testGetSetLastUpdated() {
    role.setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), role.getLastUpdated());
  }
}
