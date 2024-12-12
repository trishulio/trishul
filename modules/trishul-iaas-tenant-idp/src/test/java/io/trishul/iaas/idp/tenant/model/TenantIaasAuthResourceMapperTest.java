package io.company.brewcraft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.TenantIaasAuthResources;

public class TenantIaasAuthResourceMapperTest {
    private TenantIaasAuthResourceMapper mapper;

    @BeforeEach
    public void init() {
        mapper = TenantIaasAuthResourceMapper.INSTANCE;
    }

    @Test
    public void testFromComponents_ReturnsResourcesFromComponents() {
        List<IaasRole> roles = List.of(new IaasRole("ROLE_1"));

        List<TenantIaasAuthResources> resources =  mapper.fromComponents(roles);

        List<TenantIaasAuthResources> expected = List.of(new TenantIaasAuthResources(new IaasRole("ROLE_1")));
        assertEquals(expected, resources);
    }
}
