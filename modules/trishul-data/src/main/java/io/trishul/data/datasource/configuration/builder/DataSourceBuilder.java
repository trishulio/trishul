package io.trishul.data.datasource.configuration.builder;

import javax.sql.DataSource;

// TODO: Rename to DataSourceConfigurationBuilder
public interface DataSourceBuilder {
    DataSource build();

    DataSourceBuilder username(String username);

    DataSourceBuilder password(String password);

    DataSourceBuilder url(String url);

    DataSourceBuilder schema(String schema);

    DataSourceBuilder poolSize(int size);

    DataSourceBuilder autoCommit(boolean autoCommit);

    String username();

    String password();

    String url();

    String schema();

    int poolSize();

    boolean autoCommit();

    DataSourceBuilder clear();
}
