package io.company.brewcraft.service.mapper;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.Tenant;

public class TenantIaasIdpTenantMapperTest {
    private TenantIaasIdpTenantMapper mapper;

    @BeforeEach
    public void init() {
        mapper = TenantIaasIdpTenantMapper.INSTANCE;
    }

    @Test
    public void testFromTenants_ReturnsNull_WhenArgIsNull() {
        assertNull(mapper.fromTenants(null));
    }

    @Test
    public void testFromTenants_ReturnsList_WhenArgIsNotNull() throws MalformedURLException {
        List<Tenant> tenants = List.of(
            new Tenant(
                UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "TENANT_1",
                new URL("http://localhost/"),
                true,
                LocalDateTime.of(2000, 1, 1, 0, 0),
                LocalDateTime.of(2000, 1, 1, 0, 0)
            )
        );

        List<IaasIdpTenant> idpTenants = mapper.fromTenants(tenants);

        List<IaasIdpTenant> expected = List.of(
            new IaasIdpTenant("00000000-0000-0000-0000-000000000001")
        );
        assertEquals(expected, idpTenants);
    }

    @Test
    public void testFromTenants_ReturnsListWithNoId_WhenArgIsNotNull() {
        List<Tenant> tenants = List.of(new Tenant());

        List<IaasIdpTenant> idpTenants = mapper.fromTenants(tenants);

        List<IaasIdpTenant> expected = List.of(new IaasIdpTenant());
        assertEquals(expected, idpTenants);
    }
}
