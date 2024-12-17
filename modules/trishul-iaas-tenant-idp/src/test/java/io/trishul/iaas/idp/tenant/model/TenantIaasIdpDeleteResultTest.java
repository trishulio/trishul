package io.trishul.iaas.idp.tenant.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantIaasIdpDeleteResultTest {
    private TenantIaasIdpDeleteResult result;

    @BeforeEach
    public void init() {
        result = new TenantIaasIdpDeleteResult(10);
    }

    @Test
    public void testAllArgConstructor() {
        assertEquals(10, result.getIdpTenant());
    }

    @Test
    public void testGetSetIdpTenant() {
        result.setIdpTenant(1);
        assertEquals(1, result.getIdpTenant());
    }
}
