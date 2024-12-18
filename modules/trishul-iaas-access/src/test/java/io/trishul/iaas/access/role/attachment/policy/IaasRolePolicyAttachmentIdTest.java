package io.trishul.iaas.access.role.attachment.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.access.role.model.IaasRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IaasRolePolicyAttachmentIdTest {
    private IaasRolePolicyAttachmentId id;

    @BeforeEach
    public void init() {
        id = new IaasRolePolicyAttachmentId();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(id.getRoleId());
        assertNull(id.getPolicyId());
    }

    @Test
    public void testIdArgConstructor() {
        id = new IaasRolePolicyAttachmentId("ROLE", "POLICY");
        assertEquals("ROLE", id.getRoleId());
        assertEquals("POLICY", id.getPolicyId());
    }

    @Test
    public void testBuild_ReturnsNull_WhenBothRoleAndPolicyIdAreNull() {
        assertNull(IaasRolePolicyAttachmentId.build(null, null));
    }

    @Test
    public void testBuild_ReturnsId_WhenArgIsNotNull() {
        assertEquals(
                new IaasRolePolicyAttachmentId("ROLE", "POLICY"),
                IaasRolePolicyAttachmentId.build(new IaasRole("ROLE"), new IaasPolicy("POLICY")));
        assertEquals(
                new IaasRolePolicyAttachmentId("ROLE", null),
                IaasRolePolicyAttachmentId.build(new IaasRole("ROLE"), new IaasPolicy(null)));
        assertEquals(
                new IaasRolePolicyAttachmentId(null, "POLICY"),
                IaasRolePolicyAttachmentId.build(new IaasRole(null), new IaasPolicy("POLICY")));
        assertEquals(
                new IaasRolePolicyAttachmentId(null, "POLICY"),
                IaasRolePolicyAttachmentId.build(null, new IaasPolicy("POLICY")));
        assertEquals(
                new IaasRolePolicyAttachmentId("ROLE", null),
                IaasRolePolicyAttachmentId.build(new IaasRole("ROLE"), null));
    }
}
