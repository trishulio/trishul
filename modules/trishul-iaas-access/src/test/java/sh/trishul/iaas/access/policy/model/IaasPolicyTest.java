package sh.trishul.iaas.access.policy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

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
    assertSame(policy, policy.setId("ID"));
    assertEquals("ID", policy.getId());
  }

  @Test
  void testGetSetName() {
    assertSame(policy, policy.setName("NAME"));
    assertEquals("NAME", policy.getName());
  }

  @Test
  void testGetSetDescription() {
    assertSame(policy, policy.setDescription("DESCRIPTION"));
    assertEquals("DESCRIPTION", policy.getDescription());
  }

  @Test
  void testGetSetIaasId() {
    assertSame(policy, policy.setIaasId("IAAS_ID"));
    assertEquals("IAAS_ID", policy.getIaasId());
  }

  @Test
  void testGetSetIaasResourceName() {
    assertSame(policy, policy.setIaasResourceName("IAAS_RES_NAME"));
    assertEquals("IAAS_RES_NAME", policy.getIaasResourceName());
  }

  @Test
  void testGetSetDocument() {
    assertSame(policy, policy.setDocument("DOCUMENT"));
    assertEquals("DOCUMENT", policy.getDocument());
  }

  @Test
  void testGetSetCreatedAt() {
    assertSame(policy, policy.setCreatedAt(LocalDateTime.of(2001, 1, 1, 0, 0)));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), policy.getCreatedAt());
  }

  @Test
  void testGetSetLastUpdated() {
    assertSame(policy, policy.setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0)));
    assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), policy.getLastUpdated());
  }

  @Test
  void testAccessId() throws Exception {
    IaasPolicy accessor = new IaasPolicy();
    assertSame(accessor, accessor.setId("testString"));
    assertEquals("testString", accessor.getId());
  }

  @Test
  void testAccessName() throws Exception {
    IaasPolicy accessor = new IaasPolicy();
    assertSame(accessor, accessor.setName("testString"));
    assertEquals("testString", accessor.getName());
  }

  @Test
  void testAccessIaasId() throws Exception {
    IaasPolicy accessor = new IaasPolicy();
    assertSame(accessor, accessor.setIaasId("testString"));
    assertEquals("testString", accessor.getIaasId());
  }

  @Test
  void testAccessIaasResourceName() throws Exception {
    IaasPolicy accessor = new IaasPolicy();
    assertSame(accessor, accessor.setIaasResourceName("testString"));
    assertEquals("testString", accessor.getIaasResourceName());
  }

  @Test
  void testAccessDocument() throws Exception {
    IaasPolicy accessor = new IaasPolicy();
    assertSame(accessor, accessor.setDocument("testString"));
    assertEquals("testString", accessor.getDocument());
  }

  @Test
  void testAccessDescription() throws Exception {
    IaasPolicy accessor = new IaasPolicy();
    assertSame(accessor, accessor.setDescription("testString"));
    assertEquals("testString", accessor.getDescription());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    IaasPolicy accessor = new IaasPolicy();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    IaasPolicy accessor = new IaasPolicy();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

  @Test
  void testAccessVersion() throws Exception {
    IaasPolicy accessor = new IaasPolicy();
    assertSame(accessor, accessor.setVersion(123));
    assertNull(accessor.getVersion());
  }

}
