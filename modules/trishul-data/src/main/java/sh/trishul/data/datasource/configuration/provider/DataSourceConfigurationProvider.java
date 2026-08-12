package sh.trishul.data.datasource.configuration.provider;

import sh.trishul.data.datasource.configuration.model.DataSourceConfiguration;

public interface DataSourceConfigurationProvider<ID> {
  DataSourceConfiguration getConfiguration(ID id);

  DataSourceConfiguration getAdminConfiguration();
}
