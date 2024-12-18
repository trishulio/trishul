package io.trishul.iaas.tenant.idp.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.TenantIaasIdpDeleteResult;
import io.trishul.iaas.idp.tenant.model.TenantIaasIdpResources;
import io.trishul.iaas.idp.tenant.model.mapper.TenantIaasIdpResourcesMapper;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantIaasIdpServiceTest {
    private TenantIaasIdpService service;

    private IaasIdpTenantService mIdpTenantService;

    @BeforeEach
    public void init() {
        mIdpTenantService = mock(IaasIdpTenantService.class);

        service =
                new TenantIaasIdpService(mIdpTenantService, TenantIaasIdpResourcesMapper.INSTANCE);
    }

    @Test
    public void testGet_ReturnsAuthResourcesFromComponents() {
        doReturn(List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2")))
                .when(mIdpTenantService)
                .getAll(Set.of("T1", "T2"));

        List<TenantIaasIdpResources> resources = service.get(Set.of("T1", "T2"));

        List<TenantIaasIdpResources> expected =
                List.of(
                        new TenantIaasIdpResources(new IaasIdpTenant("T1")),
                        new TenantIaasIdpResources(new IaasIdpTenant("T2")));

        assertEquals(expected, resources);
    }

    @Test
    public void testAdd_ReturnsAddedAuthResourcesFromComponents() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mIdpTenantService).add(anyList());

        List<TenantIaasIdpResources> resources =
                service.add(List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2")));

        List<TenantIaasIdpResources> expected =
                List.of(
                        new TenantIaasIdpResources(new IaasIdpTenant("T1")),
                        new TenantIaasIdpResources(new IaasIdpTenant("T2")));

        assertEquals(expected, resources);
        verify(mIdpTenantService).add(List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2")));
    }

    @Test
    public void testPut_ReturnsPutAuthResourcesFromComponents() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mIdpTenantService).put(anyList());

        List<TenantIaasIdpResources> resources =
                service.put(List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2")));

        List<TenantIaasIdpResources> expected =
                List.of(
                        new TenantIaasIdpResources(new IaasIdpTenant("T1")),
                        new TenantIaasIdpResources(new IaasIdpTenant("T2")));

        assertEquals(expected, resources);
        verify(mIdpTenantService).put(List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2")));
    }

    @Test
    public void testDelete_ReturnsDeleteResult() {
        doAnswer(inv -> new Long(inv.getArgument(0, Set.class).size()))
                .when(mIdpTenantService)
                .delete(anySet());

        TenantIaasIdpDeleteResult res = service.delete(Set.of("T1", "T2"));

        assertEquals(new TenantIaasIdpDeleteResult(2), res);
        verify(mIdpTenantService).delete(Set.of("T1", "T2"));
    }

    @Test
    public void testExists_ReturnsTrue_WhenIdpReturnsTrue() {
        doReturn(true).when(mIdpTenantService).exist("T1");

        assertTrue(service.exist("T1"));
    }

    @Test
    public void testExists_ReturnsFalse_WhenIdpReturnsFalse() {
        doReturn(false).when(mIdpTenantService).exist("T1");

        assertFalse(service.exist("T1"));
    }
}
