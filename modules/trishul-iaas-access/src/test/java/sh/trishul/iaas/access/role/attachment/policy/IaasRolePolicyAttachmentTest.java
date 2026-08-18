package sh.trishul.iaas.access.role.attachment.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.iaas.access.policy.model.IaasPolicy;
import sh.trishul.iaas.access.role.model.IaasRole;

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
    assertSame(attachment, attachment.setId(new IaasRolePolicyAttachmentId("ROLE", "POLICY")));
    assertEquals(new IaasRolePolicyAttachmentId("ROLE", "POLICY"), attachment.getId());
  }

  @Test
  void testSetId_SetsNull_WhenIdIsNull() {
    attachment.setId(null);
    assertNull(attachment.getId());
  }

  @Test
  void testGetSetIaasRole() {
    assertSame(attachment, attachment.setIaasRole(new IaasRole("ROLE")));

    assertEquals(new IaasRole("ROLE"), attachment.getIaasRole());
  }

  @Test
  void testGetSetIaasPolicy() {
    assertSame(attachment, attachment.setIaasPolicy(new IaasPolicy("POLICY")));

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
    assertSame(attachment, attachment.setCreatedAt(now));
    assertEquals(now, attachment.getCreatedAt());
  }

  @Test
  void testGetSetLastUpdated() {
    LocalDateTime now = LocalDateTime.now();
    assertSame(attachment, attachment.setLastUpdated(now));
    assertEquals(now, attachment.getLastUpdated());
  }

  @Test
  void testSetVersion() {
    attachment.setVersion(1);
    assertNull(attachment.getVersion());
  }

  @Test
  void testAccessId() throws Exception {
    IaasRolePolicyAttachment accessor = new IaasRolePolicyAttachment();
    IaasRolePolicyAttachmentId value = new IaasRolePolicyAttachmentId("role-1", "policy-1");
    assertSame(accessor, accessor.setId(value));
    assertEquals(value, accessor.getId());
  }

  @Test
  void testAccessIaasRole() throws Exception {
    IaasRolePolicyAttachment accessor = new IaasRolePolicyAttachment();
    IaasRole value = new IaasRole();
    assertSame(accessor, accessor.setIaasRole(value));
    assertEquals(value, accessor.getIaasRole());
  }

  @Test
  void testAccessIaasPolicy() throws Exception {
    IaasRolePolicyAttachment accessor = new IaasRolePolicyAttachment();
    IaasPolicy value = new IaasPolicy();
    assertSame(accessor, accessor.setIaasPolicy(value));
    assertEquals(value, accessor.getIaasPolicy());
  }

  @Test
  void testAccessCreatedAt() throws Exception {
    IaasRolePolicyAttachment accessor = new IaasRolePolicyAttachment();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setCreatedAt(value));
    assertEquals(value, accessor.getCreatedAt());
  }

  @Test
  void testAccessLastUpdated() throws Exception {
    IaasRolePolicyAttachment accessor = new IaasRolePolicyAttachment();
    LocalDateTime value = LocalDateTime.of(2000, 1, 1, 0, 0);
    assertSame(accessor, accessor.setLastUpdated(value));
    assertEquals(value, accessor.getLastUpdated());
  }

  @Test
  void testAccessVersion() throws Exception {
    IaasRolePolicyAttachment accessor = new IaasRolePolicyAttachment();
    assertSame(accessor, accessor.setVersion(123));
    assertNull(accessor.getVersion());
  }

}
