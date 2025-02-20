package io.trishul.tenant.persistence.datasource.manager;

import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import io.trishul.data.datasource.configuration.provider.DataSourceConfigurationProvider;
import io.trishul.data.datasource.manager.DataSourceManager;
import io.trishul.tenant.persistence.datasource.configuration.provider.TenantDataSourceConfigurationProvider;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import javax.sql.DataSource;

public class TenantDataSourceManagerWrapper implements TenantDataSourceManager {
  private final DataSourceManager dsMgr;
  private final DataSourceConfigurationProvider<UUID> dsConfigMgr;

  public TenantDataSourceManagerWrapper(DataSourceManager dsMgr,
      TenantDataSourceConfigurationProvider dsConfigMgr) {
    this.dsMgr = dsMgr;
    this.dsConfigMgr = dsConfigMgr;
  }

  @Override
  public DataSource getDataSource(UUID tenantId) throws SQLException, IOException {
    DataSource ds = this.dsMgr.getAdminDataSource();

    if (tenantId != null) {
      DataSourceConfiguration config = this.dsConfigMgr.getConfiguration(tenantId);

      ds = this.dsMgr.getDataSource(config);
    }

    return ds;
  }
}
