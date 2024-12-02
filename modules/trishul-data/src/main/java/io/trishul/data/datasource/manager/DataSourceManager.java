package io.trishul.data.datasource.manager;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;

public interface DataSourceManager {
    DataSource getAdminDataSource();

    DataSource getDataSource(DataSourceConfiguration dataSourceConfig) throws SQLException, IOException;
}
