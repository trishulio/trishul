package io.trishul.iaas.access.role.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IaasRoleTest {
  private IaasRole role;

  @BeforeEach
  public void init() {
    role = new IaasRole();
  }

  @Test
  public void testNoArgConstructor() {
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
  public void testAllArgConstructor() {
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
  public void testGetSetId() {
    role.setId("ID");
    assertEquals("ID", role.getId());
  }

  @Test
  public void testGetSetName() {
    role.setName("NAME");
    assertEquals("NAME", role.getName());
  }

  @Test
  public void testGetSetDescription() {
    role.setDescription("DESCRIPTION");
    assertEquals("DESCRIPTION", role.getDescription());
  }

  @Test
  public void testGetSetIaasId() {
    role.setIaasId("IAAS_ID");
    assertEquals("IAAS_ID", role.getIaasId());
  }

  @Test
  public void testGetSetIaasResourceName() {
    role.setIaasResourceName("IAAS_RES_NAME");
    assertEquals("IAAS_RES_NAME", role.getIaasResourceName());
  }

  @Test
  public void testGetSetAssumePolicyDocument() {
    role.setAssumePolicyDocument("DOCUMENT");
    assertEquals("DOCUMENT", role.getAssumePolicyDocument());
  }

  @Test
  public void testGetSetLastUsed() {
    role.setLastUsed(LocalDateTime.of(2001, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), role.getLastUsed());
  }

  @Test
  public void testGetSetCreatedAt() {
    role.setCreatedAt(LocalDateTime.of(2001, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), role.getCreatedAt());
  }

  @Test
  public void testGetSetLastUpdated() {
    role.setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), role.getLastUpdated());
  }
}
