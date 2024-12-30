package io.trishul.tenant.persistence.datasource.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import io.trishul.data.datasource.manager.DataSourceManager;
import io.trishul.tenant.persistence.datasource.configuration.provider.TenantDataSourceConfigurationProvider;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantDataSourceManagerWrapperTest {
  private TenantDataSourceManagerWrapper tenantDsMgr;

  private DataSourceManager mDsMgr;
  private TenantDataSourceConfigurationProvider mConfigProvider;

  @BeforeEach
  public void init() {
    mDsMgr = mock(DataSourceManager.class);
    mConfigProvider = mock(TenantDataSourceConfigurationProvider.class);

    tenantDsMgr = new TenantDataSourceManagerWrapper(mDsMgr, mConfigProvider);
  }

  @Test
  public void testGetDataSource_ReturnsAdminDataSource_WhenTenantIdIsNull()
      throws SQLException, IOException {
    DataSource mDs = mock(DataSource.class);
    doReturn(mDs).when(mDsMgr).getAdminDataSource();

    assertEquals(mDs, tenantDsMgr.getDataSource(null));
  }

  @Test
  public void testGetDataSource_ReturnsDataSourceFromTenantDsConfig_WhenTenantIdIsNotNull()
      throws SQLException, IOException {
    DataSourceConfiguration mConfig = mock(DataSourceConfiguration.class);
    doReturn(mConfig).when(mConfigProvider)
        .getConfiguration(UUID.fromString("00000000-0000-0000-0000-000000000001"));

    DataSource mDs = mock(DataSource.class);
    doReturn(mDs).when(mDsMgr).getDataSource(mConfig);

    assertEquals(mDs,
        tenantDsMgr.getDataSource(UUID.fromString("00000000-0000-0000-0000-000000000001")));
  }
}
