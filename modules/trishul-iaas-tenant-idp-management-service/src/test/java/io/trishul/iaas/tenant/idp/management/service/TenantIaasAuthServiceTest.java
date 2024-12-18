package io.trishul.iaas.tenant.idp.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.access.service.role.service.IaasRoleService;
import io.trishul.iaas.idp.tenant.model.IaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.TenantIaasAuthDeleteResult;
import io.trishul.iaas.idp.tenant.model.TenantIaasAuthResourceMapper;
import io.trishul.iaas.idp.tenant.model.TenantIaasAuthResources;
import io.trishul.iaas.tenant.resource.TenantIaasResourceBuilder;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantIaasAuthServiceTest {
    private TenantIaasAuthService service;

    private TenantIaasAuthResourceMapper mMapper;
    private IaasRoleService mRoleService;
    private TenantIaasResourceBuilder mResourceBuilder;

    @BeforeEach
    public void init() {
        mMapper = TenantIaasAuthResourceMapper.INSTANCE;
        mRoleService = mock(IaasRoleService.class);
        mResourceBuilder = mock(TenantIaasResourceBuilder.class);

        service = new TenantIaasAuthService(mMapper, mRoleService, mResourceBuilder);
    }

    @Test
    public void testGet_ReturnsAuthResourceWithComponents() {
        doAnswer(inv -> inv.getArgument(0, String.class) + "_ROLE_NAME")
                .when(mResourceBuilder)
                .getRoleId(anyString());
        interface RoleSet extends Set<IaasRole> {}
        doAnswer(
                        inv ->
                                inv.getArgument(0, RoleSet.class).stream()
                                        .map(id -> new IaasRole(id.toString()))
                                        .toList())
                .when(mRoleService)
                .getAll(anySet());

        List<TenantIaasAuthResources> resources = this.service.get(Set.of("T1", "T2"));

        List<TenantIaasAuthResources> expected =
                List.of(
                        new TenantIaasAuthResources(new IaasRole("T1_ROLE_NAME")),
                        new TenantIaasAuthResources(new IaasRole("T2_ROLE_NAME")));

        assertEquals(expected, resources);
    }

    @Test
    public void testAdd_ReturnsResourcesFromAddedRoles() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mRoleService).add(anyList());
        doAnswer(inv -> new IaasRole(inv.getArgument(0, IaasIdpTenant.class).getId()))
                .when(mResourceBuilder)
                .buildRole(any(IaasIdpTenant.class));

        List<TenantIaasAuthResources> resources =
                this.service.add(List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2")));

        List<TenantIaasAuthResources> expected =
                List.of(
                        new TenantIaasAuthResources(new IaasRole("T1")),
                        new TenantIaasAuthResources(new IaasRole("T2")));

        assertEquals(expected, resources);
    }

    @Test
    public void testPut_ReturnsResourcesFromPutRoles() {
        doAnswer(inv -> inv.getArgument(0, List.class)).when(mRoleService).put(anyList());
        doAnswer(inv -> new IaasRole(inv.getArgument(0, IaasIdpTenant.class).getId()))
                .when(mResourceBuilder)
                .buildRole(any(IaasIdpTenant.class));

        List<TenantIaasAuthResources> resources =
                this.service.put(List.of(new IaasIdpTenant("T1"), new IaasIdpTenant("T2")));

        List<TenantIaasAuthResources> expected =
                List.of(
                        new TenantIaasAuthResources(new IaasRole("T1")),
                        new TenantIaasAuthResources(new IaasRole("T2")));

        assertEquals(expected, resources);
    }

    @Test
    public void testDelete_ReturnsDeleteResultWithCounts() {
        doAnswer(inv -> inv.getArgument(0, String.class))
                .when(mResourceBuilder)
                .getRoleId(anyString());
        doAnswer(inv -> (long) inv.getArgument(0, Set.class).size())
                .when(mRoleService)
                .delete(anySet());

        TenantIaasAuthDeleteResult result = this.service.delete(Set.of("T1", "T2"));

        assertEquals(new TenantIaasAuthDeleteResult(2L), result);
    }
}
