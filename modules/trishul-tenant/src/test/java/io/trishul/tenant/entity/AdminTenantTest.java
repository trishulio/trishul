package io.trishul.tenant.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdminTenantTest {
    private Tenant adminTenant;

    @BeforeEach
    public void init() {
        adminTenant =
                new AdminTenant(UUID.fromString("00000000-0000-0000-0000-000000000001"), "ADMIN");
    }

    @Test
    public void testGetId() {
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000001"), adminTenant.getId());
    }

    @Test
    public void testSetId_ThrowsException() {
        assertThrows(UnsupportedOperationException.class, () -> adminTenant.setId(null));
    }

    @Test
    public void testGetSetIsReady() {
        adminTenant.setIsReady(true);
        assertTrue(adminTenant.getIsReady());
    }

    @Test
    public void testGetName() {
        assertEquals("ADMIN", adminTenant.getName());
    }

    @Test
    public void testSetName_ThrowsException() {
        assertThrows(UnsupportedOperationException.class, () -> adminTenant.setName(null));
    }

    @Test
    public void testGetUrl() {
        assertEquals(null, adminTenant.getUrl());
    }

    @Test
    public void testSetUrl_ThrowsException() {
        assertThrows(UnsupportedOperationException.class, () -> adminTenant.setUrl(null));
    }

    @Test
    public void testGetCreatedAt() {
        assertEquals(null, adminTenant.getCreatedAt());
    }

    @Test
    public void testSetCreatedAt_ThrowsException() {
        assertThrows(UnsupportedOperationException.class, () -> adminTenant.setCreatedAt(null));
    }

    @Test
    public void testGetLastUpdated() {
        assertEquals(null, adminTenant.getLastUpdated());
    }

    @Test
    public void testSetLastUpdated_ThrowsException() {
        assertThrows(UnsupportedOperationException.class, () -> adminTenant.setLastUpdated(null));
    }

    @Test
    public void testGetVersion() {
        assertEquals(null, adminTenant.getVersion());
    }
}
