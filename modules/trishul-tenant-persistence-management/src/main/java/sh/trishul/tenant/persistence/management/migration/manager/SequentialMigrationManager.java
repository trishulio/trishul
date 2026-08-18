package sh.trishul.tenant.persistence.management.migration.manager;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sh.trishul.model.util.task.SequentialTaskSet;
import sh.trishul.model.util.task.TaskSet;
import sh.trishul.tenant.entity.TenantData;
import sh.trishul.tenant.persistence.management.migration.register.MigrationRegister;
import sh.trishul.tenant.persistence.management.migration.register.TenantRegister;

// Note: Can be replaced with a parallel task-set that uses blocking Async executor;
public class SequentialMigrationManager implements MigrationManager {
  private static final Logger log = LoggerFactory.getLogger(SequentialMigrationManager.class);

  private final TenantRegister tenantReg;
  private final MigrationRegister migrationRegister;

  public SequentialMigrationManager(TenantRegister register, MigrationRegister mgr) {
    this.tenantReg = register;
    this.migrationRegister = mgr;
  }

  @Override
  public void migrate(TenantData tenant) {
    tenantReg.put(tenant);

    log.info("Applying migration to tenant: {}", tenant.getId());
    migrationRegister.migrate(tenant);
  }

  @Override
  public void migrateAll(List<? extends TenantData> tenants) {
    TaskSet tasks = new SequentialTaskSet();

    tenants.forEach(id -> tasks.submit(() -> {
      migrate(id);
    }));

    log.info("{} tenants migrated successfully", tasks.getResults().size());
    if (!tasks.getErrors().isEmpty()) {
      log.error("Failed to migrate {} tenants", tasks.getErrors().size());
    }

    int i = 0;
    for (Exception e : tasks.getErrors()) {
      log.error("{}: Failed to migrate tenant because: {}", i++, e);
    }
  }
}
