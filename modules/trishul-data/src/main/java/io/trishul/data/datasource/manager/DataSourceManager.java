package io.trishul.data.datasource.manager;

import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import java.io.IOException;
import java.sql.SQLException;
import javax.sql.DataSource;

public interface DataSourceManager {
    DataSource getAdminDataSource();

    DataSource getDataSource(DataSourceConfiguration dataSourceConfig)
            throws SQLException, IOException;
}
