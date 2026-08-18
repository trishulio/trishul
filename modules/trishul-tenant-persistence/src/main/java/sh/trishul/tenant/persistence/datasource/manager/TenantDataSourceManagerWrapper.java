package sh.trishul.tenant.persistence.datasource.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import javax.sql.DataSource;
import sh.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import sh.trishul.data.datasource.configuration.provider.DataSourceConfigurationProvider;
import sh.trishul.data.datasource.manager.DataSourceManager;
import sh.trishul.tenant.persistence.datasource.configuration.provider.TenantDataSourceConfigurationProvider;

public class TenantDataSourceManagerWrapper implements TenantDataSourceManager {
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

    if (tenantId != null && !tenantId.equals(this.adminTenantId)) {
      DataSourceConfiguration config = this.dsConfigMgr.getConfiguration(tenantId);
      ds = this.dsMgr.getDataSource(config);
    }

    return ds;
  }
}
