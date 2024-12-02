package io.trishul.data.datasource.configuration.model;

public interface DataSourceConfiguration extends GlobalDataSourceConfiguration {
    String getUserName();

    String getPassword();

    String getSchemaName();
}
