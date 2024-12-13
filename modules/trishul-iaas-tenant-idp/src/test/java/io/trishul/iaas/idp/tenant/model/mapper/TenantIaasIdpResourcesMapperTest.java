package io.trishul.iaas.idp.tenant.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.TenantIaasIdpResources;

public class TenantIaasIdpResourcesMapperTest {
    private TenantIaasIdpResourcesMapper mapper;

    @BeforeEach
    public void init() {
        mapper = TenantIaasIdpResourcesMapper.INSTANCE;
    }

    @Test
    public void testFromComponents_ReturnsResources() {
        List<IaasIdpTenant> idpTenants = List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2"));

        List<TenantIaasIdpResources> resources = mapper.fromComponents(idpTenants);

        List<TenantIaasIdpResources> expected = List.of(
            new TenantIaasIdpResources(new IaasIdpTenant("T1")),
            new TenantIaasIdpResources(new IaasIdpTenant("T2"))
        );

        assertEquals(expected, resources);
    }
}
