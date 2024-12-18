// todo: refactor this class to take in datasourceconfiguration parameter and return datasource, not
// extend the builder which should be a configuration class instead
package io.trishul.data.datasource.builder;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.trishul.data.datasource.configuration.builder.AbstractDataSourceBuilder;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HikariDataSourceBuilder extends AbstractDataSourceBuilder {
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(HikariDataSourceBuilder.class);

    public static final String KEY_USERNAME = "dataSource.user";
    public static final String KEY_PASSWORD = "dataSource.password";
    public static final String KEY_URL = "jdbcUrl";
    public static final String KEY_AUTO_COMMIT = "autoCommit";
    public static final String KEY_SCHEMA = "schema";
    public static final String POOL_SIZE = "maximumPoolSize";

    public HikariDataSourceBuilder() {
        super(KEY_USERNAME, KEY_PASSWORD, KEY_URL, KEY_AUTO_COMMIT, KEY_SCHEMA, POOL_SIZE);
    }

    @Override
    public DataSource build() {
        HikariConfig config = new HikariConfig(props);
        DataSource ds = new HikariDataSource(config);

        return ds;
    }
}
