package io.trishul.tenant.persistence.management.migration.register;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import io.trishul.data.datasource.configuration.provider.DataSourceConfigurationProvider;
import io.trishul.tenant.entity.Tenant;
import io.trishul.tenant.persistence.datasource.manager.TenantDataSourceManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FlywayTenantMigrationRegisterTest {
  private TenantDataSourceManager mDsMgr;
  private DataSource mDs;
  private FluentConfiguration mFwConfig;
  private DataSourceConfigurationProvider<UUID> mConfigProvider;
  private DataSourceConfiguration mConfig;

  private FlywayTenantMigrationRegister register;

  @BeforeEach
  public void init() throws SQLException, IOException {
    mConfig = mock(DataSourceConfiguration.class);
    doReturn("MIGRATION_PATH").when(mConfig).getMigrationScriptPath();
    doReturn("SCHEMA").when(mConfig).getSchemaName();

    mConfigProvider = mock(DataSourceConfigurationProvider.class);
    doReturn(mConfig).when(mConfigProvider)
        .getConfiguration(UUID.fromString("00000000-0000-0000-0000-000000000001"));

    mDsMgr = mock(TenantDataSourceManager.class);
    mDs = mock(DataSource.class);
    doReturn(mDs).when(mDsMgr)
        .getDataSource(UUID.fromString("00000000-0000-0000-0000-000000000001"));

    mFwConfig = mock(FluentConfiguration.class);
    register = new FlywayTenantMigrationRegister(() -> mFwConfig, mDsMgr, mConfigProvider);
  }

  @Test
  public void testMigrate_RunsFlywayOnTenantWithTenantsDataSource() {
    Flyway mFw = mockFlyway(mFwConfig, "SCHEMA", "MIGRATION_PATH", mDs);

    register.migrate(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

    verify(mFw).migrate();
  }

  @Test
  public void testIsMigrated_ReturnsTrue_WhenAllScriptsAreApplied() {
    MigrationInfoService mInfo = mock(MigrationInfoService.class);

    MigrationInfo[] mApplied = new MigrationInfo[3];
    doReturn(mApplied).when(mInfo).applied();

    MigrationInfo[] mAll = new MigrationInfo[3];
    doReturn(mAll).when(mInfo).all();

    Flyway mFw = mockFlyway(mFwConfig, "SCHEMA", "MIGRATION_PATH", mDs);
    doReturn(mInfo).when(mFw).info();

    boolean b
        = register.isMigrated(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

    assertTrue(b);
  }

  @Test
  public void testIsMigrated_ReturnsFalse_WhenAllScriptsAreNotApplied() {
    MigrationInfoService mInfo = mock(MigrationInfoService.class);

    MigrationInfo[] mApplied = new MigrationInfo[3];
    doReturn(mApplied).when(mInfo).applied();

    MigrationInfo[] mAll = new MigrationInfo[5];
    doReturn(mAll).when(mInfo).all();

    Flyway mFw = mockFlyway(mFwConfig, "SCHEMA", "MIGRATION_PATH", mDs);
    doReturn(mInfo).when(mFw).info();

    boolean b
        = register.isMigrated(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

    assertFalse(b);
  }

  private Flyway mockFlyway(FluentConfiguration config, String schemas, String location,
      DataSource ds) {
    Flyway mFw = mock(Flyway.class);
    doReturn(mFw).when(config).load();
    doReturn(config).when(config).locations(location);
    doReturn(config).when(config).schemas(schemas);
    doReturn(config).when(config).dataSource(ds);

    return mFw;
  }
}
