package io.trishul.tenant.service.autoconfiguration;

import java.util.List;
import java.util.TreeSet;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import io.trishul.tenant.entity.AdminTenant;
import io.trishul.tenant.entity.Tenant;
import io.trishul.tenant.persistence.management.migration.manager.MigrationManager;
import io.trishul.tenant.service.service.TenantService;
import jakarta.annotation.PostConstruct;

@Component
@Profile("!test")
public class AutoTenantMigratorOnStartup {
  private final TenantService tenantService;
  private final MigrationManager migrationManager;
  private final AdminTenant adminTenant;

  public AutoTenantMigratorOnStartup(AdminTenant adminTenant, TenantService tenantService,
      MigrationManager migrationManager) {
    this.adminTenant = adminTenant;
    this.tenantService = tenantService;
    this.migrationManager = migrationManager;
  }

  @PostConstruct
  public void migrateAllTenantsOnStartup() {
    this.migrationManager.migrate(adminTenant);

    this.migrateTenants();
  }

  private void migrateTenants() {
    final int SIZE = 100;
    int page = 0;
    Page<Tenant> tenantPage;

    do {
      tenantPage = tenantService.getAll(null, null, null, true, new TreeSet<>(List.of("id")), true,
          page, SIZE);
      List<Tenant> tenants = tenantPage.getContent();
      this.migrationManager.migrateAll(tenants);
      page++;
    } while (tenantPage.hasNext());
  }
}
