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
  void testGetSetLastUpdated() {
    attachment.setLastUpdated(LocalDateTime.of(2000, 1, 1, 1, 1));

    assertEquals(LocalDateTime.of(2000, 1, 1, 1, 1), attachment.getLastUpdated());
  }

  @Test
  void testGetVersion() {
    assertNull(attachment.getVersion());
  }
}
