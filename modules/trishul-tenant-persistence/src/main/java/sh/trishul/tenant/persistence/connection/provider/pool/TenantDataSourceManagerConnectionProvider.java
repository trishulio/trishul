package sh.trishul.tenant.persistence.connection.provider.pool;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TenantDataSourceManagerConnectionProvider implements ConnectionProvider {
  private static final Logger log
      = LoggerFactory.getLogger(TenantDataSourceManagerConnectionProvider.class);
  private static final long serialVersionUID = 1L;
  private final transient DataSource dataSource;

  public TenantDataSourceManagerConnectionProvider(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public boolean isUnwrappableAs(Class unwrapType) {
    return false;
  }

  @Override
  public <T> T unwrap(Class<T> unwrapType) {
    return null;
  }

  @Override
  public Connection getConnection() throws SQLException {
    Connection conn = dataSource.getConnection();
    log.trace("Acquired connection for schema: {}", conn.getSchema());

    return conn;

    // TODO: maybe this is a good place to set the schema correctly.
  }

  @Override
  public void closeConnection(Connection conn) throws SQLException {
    conn.close();
  }

  @Override
  public boolean supportsAggressiveRelease() {
    return false;
  }
}
