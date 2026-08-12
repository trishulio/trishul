package sh.trishul.tenant.persistence.management.migration.manager;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.slf4j.LoggerFactory;
import sh.trishul.tenant.entity.Tenant;
import sh.trishul.tenant.entity.TenantData;
import sh.trishul.tenant.persistence.management.migration.register.MigrationRegister;
import sh.trishul.tenant.persistence.management.migration.register.TenantRegister;

class SequentialMigrationManagerTest {
  private MigrationManager mgr;

  private TenantRegister mTenantReg;
  private MigrationRegister mMigrationReg;

  @BeforeEach
  void init() {
    mTenantReg = mock(TenantRegister.class);
    mMigrationReg = mock(MigrationRegister.class);

    mgr = new SequentialMigrationManager(mTenantReg, mMigrationReg);
  }

  @Test
  void testMigrate_PutsTenantInTenantRegisterAndMigrationRegister() {
    mgr.migrate(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

    InOrder order = inOrder(mTenantReg, mMigrationReg);
    order.verify(mTenantReg)
        .put(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
    order.verify(mMigrationReg)
        .migrate(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
  }

  @Test
  void testMigrateAll_CallsMigrateOnAllTenants() {
    mgr = spy(mgr);

    List<TenantData> tenants
        = List.of(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")),
            new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002")));

    mgr.migrateAll(tenants);

    InOrder order = inOrder(mgr);

    order.verify(mgr).migrate(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
    order.verify(mgr).migrate(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002")));
  }

  @Test
  void testMigrateAll_LogsErrors_WhenMigrationThrowsException() {
    Tenant tenant1 = new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    Tenant tenant2 = new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000002"));

    doThrow(new RuntimeException("Test Exception")).when(mMigrationReg).migrate(tenant1);

    mgr.migrateAll(List.of(tenant1, tenant2));

    InOrder order = inOrder(mTenantReg, mMigrationReg);
    order.verify(mTenantReg).put(tenant1);
    order.verify(mMigrationReg).migrate(tenant1);
    order.verify(mTenantReg).put(tenant2);
    order.verify(mMigrationReg).migrate(tenant2);
  }

  @Test
  void testMigrateAll_LogsErrors_WhenErrorsExist() {
    Tenant tenant1 = new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    doThrow(new RuntimeException("Test Exception")).when(mMigrationReg).migrate(tenant1);

    Logger logger = (Logger) LoggerFactory.getLogger(SequentialMigrationManager.class);
    ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    listAppender.start();
    logger.addAppender(listAppender);

    try {
      mgr.migrateAll(List.of(tenant1));

      List<ILoggingEvent> logs = listAppender.list;
      boolean foundError = logs.stream().anyMatch(event -> event.getLevel() == Level.ERROR
          && event.getFormattedMessage().contains("Failed to migrate 1 tenants"));
      assertTrue(foundError);
    } finally {
      logger.detachAppender(listAppender);
    }
  }
}
