package io.trishul.tenant.persistence.connection.provider.pool;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

public class TenantDataSourceManagerConnectionProvider implements ConnectionProvider {
    private static final long serialVersionUID = 1L;
    private final DataSource dataSource;

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
        return dataSource.getConnection();
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
