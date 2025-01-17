package io.trishul.data.datasource.query.runner;

import io.trishul.base.types.lambda.CheckedConsumer;
import io.trishul.base.types.lambda.CheckedSupplier;
import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import io.trishul.data.datasource.manager.DataSourceManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceQueryRunner {
  private static final Logger log = LoggerFactory.getLogger(DataSourceQueryRunner.class);

  private final DataSourceManager dsManager;

  public DataSourceQueryRunner(DataSourceManager dsManager) {
    this.dsManager = dsManager;
  }

  public <T> T query(DataSourceConfiguration dsConfig,
      CheckedSupplier<T, Connection, Exception> supplier) {
    try {
      DataSource ds = this.dsManager.getDataSource(dsConfig);
      return executeQuery(ds, supplier);
    } catch (SQLException | IOException e) {
      throw new RuntimeException(
          String.format("Failed to fetch the datasource for tenant: %s", dsConfig), e);
    }
  }

  public <T> T query(CheckedSupplier<T, Connection, Exception> supplier) {
    return executeQuery(this.dsManager.getAdminDataSource(), supplier);
  }

  public void query(DataSourceConfiguration dsConfig,
      CheckedConsumer<Connection, Exception> runnable) {
    try {
      DataSource ds = this.dsManager.getDataSource(dsConfig);
      executeQuery(ds, runnable);
    } catch (SQLException | IOException e) {
      throw new RuntimeException(
          String.format("Failed to fetch the datasource for tenant: %s", dsConfig), e);
    }
  }

  public void query(CheckedConsumer<Connection, Exception> runnable) {
    executeQuery(this.dsManager.getAdminDataSource(), runnable);
  }

  @SuppressWarnings("UseSpecificCatch")
  private <T> T executeQuery(DataSource ds, CheckedSupplier<T, Connection, Exception> supplier) {
    Connection conn = null;
    try {
      conn = ds.getConnection();
      return supplier.get(conn);
    } catch (Exception e) {
      try {
        if (conn != null) {
          conn.rollback();
        }
      } catch (SQLException eR) {
        log.error("Failed to perform rollback because {}", eR);
      }
      throw new RuntimeException("Failed to run SQL operation", e);
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
          log.error("Error occurred while attempting to close the connection", e);
        }
      }
    }
  }

  @SuppressWarnings("UseSpecificCatch")
  private void executeQuery(DataSource ds, CheckedConsumer<Connection, Exception> runnable) {
    Connection conn = null;
    try {
      conn = ds.getConnection();
      runnable.run(conn);
    } catch (Exception e) {
      try {
        if (conn != null) {
          conn.rollback();
        }
      } catch (SQLException eR) {
        log.error("Failed to perform rollback because {}", eR);
      }
      throw new RuntimeException("Failed to run SQL operation", e);
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
          log.error("Error occurred while attempting to close the connection", e);
        }
      }
    }
  }
}
