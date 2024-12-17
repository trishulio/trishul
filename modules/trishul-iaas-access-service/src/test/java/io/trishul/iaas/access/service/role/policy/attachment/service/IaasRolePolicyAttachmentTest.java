package io.trishul.iaas.access.service.role.policy.attachment.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IaasRolePolicyAttachmentTest {
    private IaasRolePolicyAttachment attachment;

    @BeforeEach
    public void init() {
        attachment = new IaasRolePolicyAttachment();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(attachment.getId());
        assertNull(attachment.getIaasRole());
        assertNull(attachment.getIaasPolicy());
        assertNull(attachment.getVersion());
    }

    @Test
    public void testIdConstructor() {
        attachment = new IaasRolePolicyAttachment(new IaasRolePolicyAttachmentId("ROLE", "POLICY"));

        assertEquals(new IaasRolePolicyAttachmentId("ROLE", "POLICY"), attachment.getId());
    }

    @Test
    public void testAllArgConstructor() {
        attachment = new IaasRolePolicyAttachment(new IaasRole("ROLE"), new IaasPolicy("POLICY"));

        assertEquals(new IaasRole("ROLE"), attachment.getIaasRole());
        assertEquals(new IaasPolicy("POLICY"), attachment.getIaasPolicy());
    }

    @Test
    public void testGetSetId() {
        attachment.setId(new IaasRolePolicyAttachmentId("ROLE", "POLICY"));
        assertEquals(new IaasRolePolicyAttachmentId("ROLE", "POLICY"), attachment.getId());
    }

    @Test
    public void testSetId_SetsNull_WhenIdIsNull() {
        attachment.setId(null);
        assertNull(attachment.getId());
    }

    @Test
    public void testGetSetIaasRole() {
        attachment.setIaasRole(new IaasRole("ROLE"));

        assertEquals(new IaasRole("ROLE"), attachment.getIaasRole());
    }

    @Test
    public void testGetSetIaasPolicy() {
        attachment.setIaasPolicy(new IaasPolicy("POLICY"));

        assertEquals(new IaasPolicy("POLICY"), attachment.getIaasPolicy());
    }

    @Test
    public void testGetSetLastUpdated() {
        attachment.setLastUpdated(LocalDateTime.of(2000, 1, 1, 1, 1));

        assertEquals(LocalDateTime.of(2000, 1, 1, 1, 1), attachment.getLastUpdated());
    }

    @Test
    public void testGetVersion() {
        assertNull(attachment.getVersion());
    }
}
