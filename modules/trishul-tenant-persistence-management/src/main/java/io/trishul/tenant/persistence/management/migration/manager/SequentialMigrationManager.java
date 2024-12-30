package io.trishul.tenant.persistence.management.migration.manager;

import io.trishul.model.util.task.SequentialTaskSet;
import io.trishul.model.util.task.TaskSet;
import io.trishul.tenant.entity.TenantData;
import io.trishul.tenant.persistence.management.migration.register.MigrationRegister;
import io.trishul.tenant.persistence.management.migration.register.TenantRegister;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Note: Can be replaced with a parallel task-set that uses blocking Async executor;
public class SequentialMigrationManager implements MigrationManager {
  private static final Logger log = LoggerFactory.getLogger(SequentialMigrationManager.class);

  private final TenantRegister tenantReg;
  private final MigrationRegister migrationReg;

  public SequentialMigrationManager(TenantRegister register, MigrationRegister mgr) {
    this.tenantReg = register;
    this.migrationReg = mgr;
  }

  @Override
  public void migrate(TenantData tenant) {
    tenantReg.put(tenant);

    log.info("Applying migration to tenant: {}", tenant.getId());
    migrationReg.migrate(tenant);
  }

  @Override
  public void migrateAll(List<? extends TenantData> tenants) {
    TaskSet tasks = new SequentialTaskSet();

    tenants.forEach(id -> tasks.submit(() -> {
      migrate(id);
    }));

    log.info("{} tenants migrated successfully", tasks.getResults().size());
    if (tasks.getErrors().size() > 0) {
      log.error("Failed to migrate {} tenants", tasks.getErrors().size());
    }

    int i = 0;
    for (Exception e : tasks.getErrors()) {
      log.error("{}: Failed to migrate tenant because: {}", i++, e);
    }
  }
}
