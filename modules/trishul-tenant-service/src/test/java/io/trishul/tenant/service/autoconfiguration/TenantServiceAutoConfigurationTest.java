package io.trishul.tenant.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.crud.service.LockService;
import io.trishul.iaas.tenant.service.TenantIaasService;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import io.trishul.tenant.entity.Tenant;
import io.trishul.tenant.entity.TenantAccessor;
import io.trishul.tenant.persistence.management.migration.manager.MigrationManager;
import io.trishul.tenant.persistence.management.migration.register.TenantRegister;
import io.trishul.tenant.service.repository.TenantRepository;
import io.trishul.tenant.service.service.TenantService;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TenantServiceAutoConfigurationTest {

  private TenantServiceAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new TenantServiceAutoConfiguration();
  }

  @Test
  @SuppressWarnings("unchecked")
  void testTenantService_ReturnsNonNull() {
    LockService mockLockService = mock(LockService.class);
    TenantRepository mockTenantRepository = mock(TenantRepository.class);
    MigrationManager mockMigrationManager = mock(MigrationManager.class);
    TenantRegister mockTenantRegister = mock(TenantRegister.class);
    TenantIaasService mockTenantIaasService = mock(TenantIaasService.class);
    Refresher<Tenant, TenantAccessor<?>> mockTenantRefresher = mock(Refresher.class);

    TenantService result = config.tenantService(mockLockService, mockTenantRepository,
        mockMigrationManager, mockTenantRegister, mockTenantIaasService, mockTenantRefresher);

    assertNotNull(result);
  }

  @Test
  void testTenantAccessorRefresher_ReturnsNonNull() {
    TenantRepository mockRepo = mock(TenantRepository.class);

    AccessorRefresher<UUID, TenantAccessor<?>, Tenant> result
        = config.tenantAccessorRefresher(mockRepo);

    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testTenantRefresher_ReturnsNonNull() {
    AccessorRefresher<UUID, TenantAccessor<?>, Tenant> mockAccessorRefresher
        = mock(AccessorRefresher.class);

    Refresher<Tenant, TenantAccessor<?>> result = config.tenantRefresher(mockAccessorRefresher);

    assertNotNull(result);
  }

  @Test
  void testTenantAccessorRefresher_LambdaCoverage() {
    TenantRepository mockRepo = mock(TenantRepository.class);
    AccessorRefresher<UUID, TenantAccessor<?>, Tenant> refresher
        = config.tenantAccessorRefresher(mockRepo);

    UUID id = UUID.randomUUID();
    Tenant tenant = new Tenant(id);
    when(mockRepo.findAllById(any())).thenReturn(List.of(tenant));

    TenantAccessor<?> mockAccessor = mock(TenantAccessor.class);
    when(mockAccessor.getTenant()).thenReturn(new Tenant(id));

    refresher.refreshAccessors(List.of(mockAccessor));

    verify(mockAccessor).setTenant(null);
    verify(mockAccessor).setTenant(tenant);
    verify(mockRepo).findAllById(Set.of(id));
  }
}
