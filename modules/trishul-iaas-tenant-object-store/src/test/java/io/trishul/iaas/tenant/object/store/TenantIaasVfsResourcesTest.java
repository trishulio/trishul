package io.trishul.iaas.tenant.object.store;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantIaasVfsResourcesTest {
    private TenantIaasVfsResources resources;

    @BeforeEach
    public void init() {
        resources = new TenantIaasVfsResources();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(resources.getObjectStore());
        assertNull(resources.getPolicy());
    }

    @Test
    public void testAllArgConstructor() {
        resources = new TenantIaasVfsResources(new IaasObjectStore("OBJECT_STORE"), new IaasPolicy("POLICY"));

        assertEquals(new IaasObjectStore("OBJECT_STORE"), resources.getObjectStore());
        assertEquals(new IaasPolicy("POLICY"), resources.getPolicy());
    }

    @Test
    public void testGetSetObjectStore() {
        resources.setObjectStore(new IaasObjectStore("OBJECT_STORE"));

        assertEquals(new IaasObjectStore("OBJECT_STORE"), resources.getObjectStore());
    }

    @Test
    public void testGetSetPolicy() {
        resources.setPolicy(new IaasPolicy("POLICY"));

        assertEquals(new IaasPolicy("POLICY"), resources.getPolicy());
    }
}
