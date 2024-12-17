package io.trishul.iaas.idp.tenant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantIaasAuthDeleteResultTest {
    private TenantIaasAuthDeleteResult result;

    @BeforeEach
    public void init() {
        result = new TenantIaasAuthDeleteResult(10);
    }

    @Test
    public void testAllArgConstructor() {
        assertEquals(10, result.getRoles());
    }

    @Test
    public void testGetSetRoles() {
        result.setRoles(1);
        assertEquals(1, result.getRoles());
    }
}
