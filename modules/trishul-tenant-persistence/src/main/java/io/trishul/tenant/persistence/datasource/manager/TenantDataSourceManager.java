package io.trishul.tenant.persistence.datasource.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import javax.sql.DataSource;

public interface TenantDataSourceManager {
  DataSource getDataSource(UUID tenantId) throws SQLException, IOException;
}
