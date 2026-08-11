package io.trishul.tenant.persistence.datasource.manager;

import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import io.trishul.data.datasource.configuration.provider.DataSourceConfigurationProvider;
import io.trishul.data.datasource.manager.DataSourceManager;
import io.trishul.tenant.persistence.datasource.configuration.provider.TenantDataSourceConfigurationProvider;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TenantDataSourceManagerWrapper implements TenantDataSourceManager {
  private static final Logger log = LoggerFactory.getLogger(TenantDataSourceManagerWrapper.class);

  private final DataSourceManager dsMgr;
  private final DataSourceConfigurationProvider<UUID> dsConfigMgr;
  private final UUID adminTenantId;

  public TenantDataSourceManagerWrapper(DataSourceManager dsMgr,
      TenantDataSourceConfigurationProvider dsConfigMgr, UUID adminTenantId) {
    this.dsMgr = dsMgr;
    this.dsConfigMgr = dsConfigMgr;
    this.adminTenantId = adminTenantId;
  }

  @Override
  public DataSource getDataSource(UUID tenantId) throws SQLException, IOException {
    DataSource ds = this.dsMgr.getAdminDataSource();

    log.debug("Requesting data source for tenant: {}", tenantId);

    if (tenantId != null && !tenantId.equals(this.adminTenantId)) {
      DataSourceConfiguration config = this.dsConfigMgr.getConfiguration(tenantId);
      ds = this.dsMgr.getDataSource(config);
      log.debug("Tenant data source config found for tenant: {}", tenantId);
    }

    return ds;
  }
}
