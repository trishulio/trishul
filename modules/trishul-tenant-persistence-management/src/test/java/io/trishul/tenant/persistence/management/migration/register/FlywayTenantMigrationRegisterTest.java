package io.trishul.tenant.persistence.management.migration.register;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import io.trishul.data.datasource.configuration.model.MigrationConfiguration;
import io.trishul.data.datasource.configuration.provider.DataSourceConfigurationProvider;
import io.trishul.tenant.entity.Tenant;
import io.trishul.tenant.persistence.datasource.manager.TenantDataSourceManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class FlywayTenantMigrationRegisterTest {
  private TenantDataSourceManager mDsMgr;
  private DataSource mDs;
  private FluentConfiguration mFwConfig;
  private DataSourceConfigurationProvider<UUID> mConfigProvider;
  private DataSourceConfiguration mConfig;

  private FlywayTenantMigrationRegister register;

  @BeforeEach
  void init() throws SQLException, IOException {
    mConfig = mock(DataSourceConfiguration.class);
    doReturn(MigrationConfiguration.from("MIGRATION_PATH")).when(mConfig)
        .getMigrationConfigurations();
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
  void testMigrate_RunsFlywayOnTenantWithTenantsDataSource() {
    Flyway mFw = mockFlyway(mFwConfig, "SCHEMA", "MIGRATION_PATH", mDs);

    register.migrate(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

    verify(mFw).migrate();
  }

  @Test
  void testDefaultConstructor_ConstructsSuccessfully() {
    FlywayTenantMigrationRegister reg = new FlywayTenantMigrationRegister(mDsMgr, mConfigProvider);
    assertNotNull(reg);
  }

  @Test
  void testMigrate_RunsFlywayOnTenantWithTenantsDataSource_UsingPublicConstructor() {
    try (MockedStatic<Flyway> flywayMock = mockStatic(Flyway.class)) {
      flywayMock.when(Flyway::configure).thenReturn(mFwConfig);
      FlywayTenantMigrationRegister publicReg
          = new FlywayTenantMigrationRegister(mDsMgr, mConfigProvider);

      Flyway mFw = mockFlyway(mFwConfig, "SCHEMA", "MIGRATION_PATH", mDs);
      publicReg.migrate(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
      verify(mFw).migrate();
    }
  }

  @Test
  void testMigrate_ConfiguresHistoryTable_WhenSpecified() {
    doReturn(MigrationConfiguration.from("HISTORY_TABLE:MIGRATION_PATH")).when(mConfig)
        .getMigrationConfigurations();

    Flyway mFw = mockFlyway(mFwConfig, "SCHEMA", "MIGRATION_PATH", mDs);

    register.migrate(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));

    verify(mFwConfig).table("HISTORY_TABLE");
    verify(mFw).migrate();
  }

  @Test
  void testMigrate_ThrowsRuntimeException_WhenSQLExceptionIsThrown() throws Exception {
    mockFlyway(mFwConfig, "SCHEMA", "MIGRATION_PATH", mDs);

    doThrow(new SQLException("DB Error")).when(mDsMgr)
        .getDataSource(UUID.fromString("00000000-0000-0000-0000-000000000001"));

    assertThrows(RuntimeException.class, () -> {
      register.migrate(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
    });
  }

  @Test
  void testMigrate_ThrowsRuntimeException_WhenIOExceptionIsThrown() throws Exception {
    mockFlyway(mFwConfig, "SCHEMA", "MIGRATION_PATH", mDs);

    doThrow(new IOException("IO Error")).when(mDsMgr)
        .getDataSource(UUID.fromString("00000000-0000-0000-0000-000000000001"));

    assertThrows(RuntimeException.class, () -> {
      register.migrate(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
    });
  }



  @Test
  void testMigrate_ThrowsRuntimeException_WhenFlywayExceptionIsThrown() {
    Flyway mFw = mock(Flyway.class);
    doReturn(mFw).when(mFwConfig).load();
    doReturn(mFwConfig).when(mFwConfig).locations(anyString());
    doReturn(mFwConfig).when(mFwConfig).schemas(anyString());
    doReturn(mFwConfig).when(mFwConfig).dataSource(any(DataSource.class));
    doReturn(mFwConfig).when(mFwConfig).baselineOnMigrate(anyBoolean());
    doReturn(mFwConfig).when(mFwConfig).baselineVersion(anyString());
    doReturn(mFwConfig).when(mFwConfig).table(anyString());
    doReturn(new Location[] {new Location("LOCATION")}).when(mFwConfig).getLocations();
    doReturn("TABLE").when(mFwConfig).getTable();

    doThrow(new FlywayException("Flyway error")).when(mFw).migrate();

    assertThrows(RuntimeException.class, () -> {
      register.migrate(new Tenant(UUID.fromString("00000000-0000-0000-0000-000000000001")));
    });
  }

  private Flyway mockFlyway(FluentConfiguration config, String schemas, String location,
      DataSource ds) {
    Flyway mFw = mock(Flyway.class);
    doReturn(mFw).when(config).load();
    doReturn(config).when(config).locations(anyString());
    doReturn(config).when(config).schemas(anyString());
    doReturn(config).when(config).dataSource(any(DataSource.class));
    doReturn(config).when(config).baselineOnMigrate(anyBoolean());
    doReturn(config).when(config).baselineVersion(anyString());
    doReturn(config).when(config).table(anyString());
    doReturn(new Location[] {new Location("LOCATION")}).when(config).getLocations();
    doReturn("TABLE").when(config).getTable();

    return mFw;
  }
}
