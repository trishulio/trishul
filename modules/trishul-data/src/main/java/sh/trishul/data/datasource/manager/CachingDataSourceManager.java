package sh.trishul.data.datasource.manager;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.UncheckedExecutionException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import sh.trishul.data.datasource.builder.HikariDataSourceBuilder;
import sh.trishul.data.datasource.configuration.model.DataSourceConfiguration;

public class CachingDataSourceManager implements DataSourceManager {
  private static final Logger log = LoggerFactory.getLogger(CachingDataSourceManager.class);

  private final LoadingCache<DataSourceConfiguration, DataSource> cache;
  private final DataSource adminDs;

  public CachingDataSourceManager(DataSource adminDs) {
    this.adminDs = adminDs;

    this.cache
        = CacheBuilder.newBuilder().build(new CacheLoader<DataSourceConfiguration, DataSource>() {
          @Override
          public DataSource load(@NonNull DataSourceConfiguration dsConfig) throws Exception {
            log.debug("Loading new datasource for schema: {}", dsConfig.getSchemaName());
            log.debug("Datasource config: {}", dsConfig);

            try (Connection connection = adminDs.getConnection()) {
              String schema = connection.getSchema();
              if (schema != null && schema.equals(dsConfig.getSchemaName())) {
                return adminDs;
              }
            } catch (SQLException e) {
              log.error("Error getting schema from admin datasource", e);
              throw e;
            }

            HikariDataSourceBuilder dataSourceBuilder = new HikariDataSourceBuilder();
            return dataSourceBuilder.clear().url(dsConfig.getUrl().toString())
                .schema(dsConfig.getSchemaName()).username(dsConfig.getUserName())
                .password(dsConfig.getPassword()).poolSize(dsConfig.getPoolSize())
                .autoCommit(dsConfig.isAutoCommit()).build();
          }
        });
  }

  @Override
  public DataSource getDataSource(DataSourceConfiguration dsConfig)
      throws SQLException, IOException {
    try {
      return this.cache.get(dsConfig);
    } catch (ExecutionException | UncheckedExecutionException | ExecutionError e) {
      log.error("Error loading the datasource from the cache");
      Throwable cause = e.getCause();
      if (cause == null) {
        throw new RuntimeException(e);
      }
      switch (cause) {
        case SQLException c -> {
          log.error("SQLException occurred while fetching DataSource");
          throw c;
        }
        case IOException io -> {
          log.error("IOException occurred while fetching DataSource");
          throw io;
        }
        case RuntimeException re -> throw re;
        case Error err -> throw err;
        default -> {
          log.error("Unknown error occurred while fetching DataSource");
          throw new RuntimeException(cause);
        }
      }
    }
  }

  @Override
  public DataSource getAdminDataSource() {
    return this.adminDs;
  }
}
