package io.trishul.iaas.idp.tenant.model;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.iaas.access.role.model.IaasRole;

public class IaasIdpTenantTest {
    private IaasIdpTenant idpTenant;

    @BeforeEach
    public void init() {
        idpTenant = new IaasIdpTenant();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(idpTenant.getId());
        assertNull(idpTenant.getName());
        assertNull(idpTenant.getDescription());
        assertNull(idpTenant.getIaasRole());
        assertNull(idpTenant.getLastUpdated());
        assertNull(idpTenant.getCreatedAt());
        assertNull(idpTenant.getVersion());
    }

    @Test
    public void testAllArgConstructor() {
        idpTenant = new IaasIdpTenant("ID", new IaasRole("ROLE"), "DESCRIPTION", LocalDateTime.of(2002, 1, 1, 0, 0), LocalDateTime.of(2003, 1, 1, 0, 0));

        assertEquals("ID", idpTenant.getId());
        assertEquals("ID", idpTenant.getName());
        assertEquals(new IaasRole("ROLE"), idpTenant.getIaasRole());
        assertEquals("DESCRIPTION", idpTenant.getDescription());
        assertEquals(LocalDateTime.of(2002, 1, 1, 0, 0), idpTenant.getCreatedAt());
        assertEquals(LocalDateTime.of(2003, 1, 1, 0, 0), idpTenant.getLastUpdated());
    }

    @Test
    public void testGetSetId() {
        idpTenant.setId("ID");
        assertEquals("ID", idpTenant.getId());
    }

    @Test
    public void testGetSetName() {
        idpTenant.setName("NAME");
        assertEquals("NAME", idpTenant.getName());
    }

    @Test
    public void testGetSetDescription() {
        idpTenant.setDescription("DESCRIPTION");
        assertEquals("DESCRIPTION", idpTenant.getDescription());
    }

    @Test
    public void testGetSetIaasRole() {
        idpTenant.setIaasRole(new IaasRole("ROLE"));
        assertEquals(new IaasRole("ROLE"), idpTenant.getIaasRole());
    }

    @Test
    public void testGetSetCreatedAt() {
        idpTenant.setCreatedAt(LocalDateTime.of(2001, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), idpTenant.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        idpTenant.setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), idpTenant.getLastUpdated());
    }
}
