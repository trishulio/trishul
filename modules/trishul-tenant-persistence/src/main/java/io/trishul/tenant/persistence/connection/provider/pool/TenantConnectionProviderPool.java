package io.trishul.tenant.persistence.connection.provider.pool;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import io.trishul.tenant.persistence.datasource.manager.TenantDataSourceManager;

public class TenantConnectionProviderPool extends AbstractMultiTenantConnectionProvider {
    private static final Logger log = LoggerFactory.getLogger(TenantConnectionProviderPool.class);
    private static final long serialVersionUID = 1L;

    private final LoadingCache<String, ConnectionProvider> cache;
    private final ConnectionProvider adminConnProvider;

    public TenantConnectionProviderPool(TenantDataSourceManager dsMgr, DataSource adminDs) {
        this.adminConnProvider = new TenantDataSourceManagerConnectionProvider(adminDs);

        this.cache = CacheBuilder.newBuilder().build(new CacheLoader<String, ConnectionProvider>() {
            @Override
            public ConnectionProvider load(String sTenantId) throws Exception {
                UUID tenantId = UUID.fromString(sTenantId);
                DataSource ds = dsMgr.getDataSource(tenantId);

                return new TenantDataSourceManagerConnectionProvider(ds);
            }
        });
    }

    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        return this.adminConnProvider;
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(String tenantId) {
        try {
            return this.cache.get(tenantId);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLException || cause instanceof IOException) {
                throw new RuntimeException("Failed to fetch datasource from DataSourceManager", cause);
            } else {
                log.error("Unknown error occurred while fetching DataSource");
                throw new RuntimeException(cause);
            }
        }
    }
}
