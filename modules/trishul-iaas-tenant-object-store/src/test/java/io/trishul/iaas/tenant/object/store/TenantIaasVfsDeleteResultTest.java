package io.trishul.iaas.tenant.object.store;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantIaasVfsDeleteResultTest {
    private TenantIaasVfsDeleteResult result;

    @BeforeEach
    public void init() {
        result = new TenantIaasVfsDeleteResult(10, 11);
    }

    @Test
    public void testAllArgConstructor() {
        assertEquals(10, result.getObjectStore());
        assertEquals(11, result.getPolicy());
    }

    @Test
    public void testGetSetObjectStore() {
        result.setObjectStore(1);
        assertEquals(1, result.getObjectStore());
    }

    @Test
    public void testGetSetPolicy() {
        result.setPolicy(2);
        assertEquals(2, result.getPolicy());
    }
}
