package io.trishul.iaas.access.policy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IaasPolicyTest {
  private IaasPolicy policy;

  @BeforeEach
  void init() {
    policy = new IaasPolicy();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(policy.getId());
    assertNull(policy.getName());
    assertNull(policy.getDescription());
    assertNull(policy.getIaasId());
    assertNull(policy.getIaasResourceName());
    assertNull(policy.getDocument());
    assertNull(policy.getLastUpdated());
    assertNull(policy.getCreatedAt());
    assertNull(policy.getVersion());
  }

  @Test
  void testAllArgConstructor() {
    policy = new IaasPolicy("ID", "DOCUMENT", "DESCRIPTION", "IAAS_RES_NAME", "IAAS_ID",
        LocalDateTime.of(2002, 1, 1, 0, 0), LocalDateTime.of(2003, 1, 1, 0, 0));

    assertEquals("ID", policy.getId());
    assertEquals("ID", policy.getName());
    assertEquals("DESCRIPTION", policy.getDescription());
    assertEquals("IAAS_ID", policy.getIaasId());
    assertEquals("IAAS_RES_NAME", policy.getIaasResourceName());
    assertEquals("DOCUMENT", policy.getDocument());
    assertEquals(LocalDateTime.of(2002, 1, 1, 0, 0), policy.getCreatedAt());
    assertEquals(LocalDateTime.of(2003, 1, 1, 0, 0), policy.getLastUpdated());
  }

  @Test
  void testGetSetId() {
    policy.setId("ID");
    assertEquals("ID", policy.getId());
  }

  @Test
  void testGetSetName() {
    policy.setName("NAME");
    assertEquals("NAME", policy.getName());
  }

  @Test
  void testGetSetDescription() {
    policy.setDescription("DESCRIPTION");
    assertEquals("DESCRIPTION", policy.getDescription());
  }

  @Test
  void testGetSetIaasId() {
    policy.setIaasId("IAAS_ID");
    assertEquals("IAAS_ID", policy.getIaasId());
  }

  @Test
  void testGetSetIaasResourceName() {
    policy.setIaasResourceName("IAAS_RES_NAME");
    assertEquals("IAAS_RES_NAME", policy.getIaasResourceName());
  }

  @Test
  void testGetSetDocument() {
    policy.setDocument("DOCUMENT");
    assertEquals("DOCUMENT", policy.getDocument());
  }

  @Test
  void testGetSetCreatedAt() {
    policy.setCreatedAt(LocalDateTime.of(2001, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), policy.getCreatedAt());
  }

  @Test
  void testGetSetLastUpdated() {
    policy.setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), policy.getLastUpdated());
  }
}
