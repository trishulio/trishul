package io.trishul.tenant.persistence.management.migration.register;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import io.trishul.tenant.entity.Tenant;

public class UnifiedTenantRegisterTest {
    private TenantRegister register;

    private TenantUserRegister mUserReg;
    private TenantSchemaRegister mSchemaReg;

    private InOrder order;

    @BeforeEach
    public void init() {
        mUserReg = mock(TenantUserRegister.class);
        mSchemaReg = mock(TenantSchemaRegister.class);

        register = new UnifiedTenantRegister(mUserReg, mSchemaReg);

        order = inOrder(mUserReg, mSchemaReg);
    }

    @Test
    public void testAdd_CallsAddOnAllRegisters() {
        register.add(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        order.verify(mUserReg, times(1)).add(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
        order.verify(mSchemaReg, times(1)).add(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
    }

    @Test
    public void testPut_CallsPutOnAllRegisters() {
        register.put(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        order.verify(mUserReg, times(1)).put(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
        order.verify(mSchemaReg, times(1)).put(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
    }

    @Test
    public void testRemove_CallsRemoveOnSchemaAndUserRegisters() {
        register.remove(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        order.verify(mSchemaReg, times(1)).remove(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
        order.verify(mUserReg, times(1)).remove(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
    }

    @Test
    public void testExists_ReturnsTrue_WhenAllRegisterExistsReturnTrue() {
        doReturn(true).when(mUserReg).exists(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
        doReturn(true).when(mSchemaReg).exists(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        boolean b = register.exists(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
        assertTrue(b);
    }

    @Test
    public void testExists_ReturnsFalse_WhenUserRegisterExistsReturnFalse() {
        doReturn(false).when(mUserReg).exists(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
        doReturn(true).when(mSchemaReg).exists(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        boolean b = register.exists(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
        assertFalse(b);
    }

    @Test
    public void testExists_ReturnsFalse_WhenSchemaRegisterExistsReturnFalse() {
        doReturn(true).when(mUserReg).exists(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
        doReturn(false).when(mSchemaReg).exists(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

        boolean b = register.exists(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
        assertFalse(b);
    }
}
