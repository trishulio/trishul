package io.trishul.data.datasource.manager;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.trishul.data.datasource.configuration.builder.DataSourceBuilder;
import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import jakarta.annotation.Nonnull;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CachingDataSourceManager implements DataSourceManager {
    private static final Logger log = LoggerFactory.getLogger(CachingDataSourceManager.class);

    private final LoadingCache<DataSourceConfiguration, DataSource> cache;
    private final DataSource adminDs;

    public CachingDataSourceManager(DataSource adminDs, DataSourceBuilder dsBuilder) {
        this.adminDs = adminDs;
        this.cache =
                CacheBuilder.newBuilder()
                        .build(
                                new CacheLoader<DataSourceConfiguration, DataSource>() {
                                    @Override
                                    public DataSource load(
                                            @Nonnull DataSourceConfiguration dsConfig)
                                            throws Exception {
                                        log.debug(
                                                "Loading new datasource for schema: {}",
                                                dsConfig.getSchemaName());

                                        DataSource ds =
                                                dsBuilder
                                                        .clear()
                                                        .url(dsConfig.getUrl().toString())
                                                        .schema(dsConfig.getSchemaName())
                                                        .username(dsConfig.getUserName())
                                                        .password(dsConfig.getPassword())
                                                        .poolSize(dsConfig.getPoolSize())
                                                        .autoCommit(dsConfig.isAutoCommit())
                                                        .build();
                                        return ds;
                                    }
                                });
    }

    @Override
    public DataSource getDataSource(DataSourceConfiguration dsConfig)
            throws SQLException, IOException {
        DataSource ds = null;
        try {
            ds = this.cache.get(dsConfig);
        } catch (ExecutionException e) {
            // TODO: These checks are buggy.
            log.error("Error loading the datasource from the cache");
            Throwable cause = e.getCause();
            if (cause instanceof SQLException) {
                log.error("SQLException occurred while fetching DataSource");
                throw (SQLException) cause;
            } else if (cause instanceof IOException) {
                log.error("IOException occurred while fetching DataSource");
                throw (IOException) cause;
            } else {
                log.error("Unknown error occurred while fetching DataSource");
                throw new RuntimeException(cause);
            }
        }

        return ds;
    }

    @Override
    public DataSource getAdminDataSource() {
        return this.adminDs;
    }
}
