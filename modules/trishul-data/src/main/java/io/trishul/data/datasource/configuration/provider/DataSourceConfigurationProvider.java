package io.trishul.data.datasource.configuration.provider;

import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;

public interface DataSourceConfigurationProvider<ID> {
    DataSourceConfiguration getConfiguration(ID id);

    DataSourceConfiguration getAdminConfiguration();
}
