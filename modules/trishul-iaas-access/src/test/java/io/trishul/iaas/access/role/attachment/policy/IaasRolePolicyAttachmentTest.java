package io.trishul.iaas.access.role.attachment.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.access.role.model.IaasRole;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IaasRolePolicyAttachmentTest {
  private IaasRolePolicyAttachment attachment;

  @BeforeEach
  void init() {
    attachment = new IaasRolePolicyAttachment();
  }

  @Test
  void testNoArgConstructor() {
    assertNull(attachment.getId());
    assertNull(attachment.getIaasRole());
    assertNull(attachment.getIaasPolicy());
    assertNull(attachment.getVersion());
  }

  @Test
  void testIdConstructor() {
    attachment = new IaasRolePolicyAttachment(new IaasRolePolicyAttachmentId("ROLE", "POLICY"));

    assertEquals(new IaasRolePolicyAttachmentId("ROLE", "POLICY"), attachment.getId());
  }

  @Test
  void testAllArgConstructor() {
    attachment = new IaasRolePolicyAttachment(new IaasRole("ROLE"), new IaasPolicy("POLICY"));

    assertEquals(new IaasRole("ROLE"), attachment.getIaasRole());
    assertEquals(new IaasPolicy("POLICY"), attachment.getIaasPolicy());
  }

  @Test
  void testGetSetId() {
    attachment.setId(new IaasRolePolicyAttachmentId("ROLE", "POLICY"));
    assertEquals(new IaasRolePolicyAttachmentId("ROLE", "POLICY"), attachment.getId());
  }

  @Test
  void testSetId_SetsNull_WhenIdIsNull() {
    attachment.setId(null);
    assertNull(attachment.getId());
  }

  @Test
  void testGetSetIaasRole() {
    attachment.setIaasRole(new IaasRole("ROLE"));

    assertEquals(new IaasRole("ROLE"), attachment.getIaasRole());
  }

  @Test
  void testGetSetIaasPolicy() {
    attachment.setIaasPolicy(new IaasPolicy("POLICY"));

    assertEquals(new IaasPolicy("POLICY"), attachment.getIaasPolicy());
  }

  @Test
  void testAllArgConstructorWithDates() {
    LocalDateTime now = LocalDateTime.now();
    attachment
        = new IaasRolePolicyAttachment(new IaasRole("ROLE"), new IaasPolicy("POLICY"), now, now);

    assertEquals(new IaasRole("ROLE"), attachment.getIaasRole());
    assertEquals(new IaasPolicy("POLICY"), attachment.getIaasPolicy());
    assertEquals(now, attachment.getCreatedAt());
    assertEquals(now, attachment.getLastUpdated());
  }

  @Test
  void testSetId_SetsNullOnFields_WhenIdIsNullAndFieldsAreNotNull() {
    attachment.setIaasRole(new IaasRole("ROLE"));
    attachment.setIaasPolicy(new IaasPolicy("POLICY"));
    attachment.setId(null);
    assertNull(attachment.getIaasRole().getId());
    assertNull(attachment.getIaasPolicy().getId());
  }

  @Test
  void testSetId_ReusesExistingRoleAndPolicy_WhenFieldsAreNotNull() {
    IaasRole role = new IaasRole("ROLE");
    IaasPolicy policy = new IaasPolicy("POLICY");
    attachment.setIaasRole(role);
    attachment.setIaasPolicy(policy);
    attachment.setId(new IaasRolePolicyAttachmentId("NEW_ROLE", "NEW_POLICY"));
    assertEquals("NEW_ROLE", attachment.getIaasRole().getId());
    assertEquals("NEW_POLICY", attachment.getIaasPolicy().getId());
  }

  @Test
  void testSetIaasRole_Null() {
    attachment.setIaasRole(null);
    assertNull(attachment.getIaasRole());
  }

  @Test
  void testSetIaasPolicy_Null() {
    attachment.setIaasPolicy(null);
    assertNull(attachment.getIaasPolicy());
  }

  @Test
  void testGetSetCreatedAt() {
    LocalDateTime now = LocalDateTime.now();
    attachment.setCreatedAt(now);
    assertEquals(now, attachment.getCreatedAt());
  }

  @Test
  void testGetSetLastUpdated() {
    LocalDateTime now = LocalDateTime.now();
    attachment.setLastUpdated(now);
    assertEquals(now, attachment.getLastUpdated());
  }

  @Test
  void testSetVersion() {
    attachment.setVersion(1);
    assertNull(attachment.getVersion());
  }
}
