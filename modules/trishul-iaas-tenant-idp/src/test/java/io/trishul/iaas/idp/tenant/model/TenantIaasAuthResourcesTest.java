package io.trishul.iaas.idp.tenant.model;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantIaasAuthResourcesTest {
    private TenantIaasAuthResources resources;

    @BeforeEach
    public void init() {
        resources = new TenantIaasAuthResources();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(resources.getRole());
    }

    @Test
    public void testAllArgConstructor() {
        resources = new TenantIaasAuthResources(new IaasRole());
        assertEquals(new IaasRole(), resources.getRole());
    }

    @Test
    public void testGetSetRole() {
        resources.setRole(new IaasRole("ROLE"));
        assertEquals(new IaasRole("ROLE"), resources.getRole());
    }
}
