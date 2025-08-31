package io.trishul.data.datasource.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.trishul.data.datasource.configuration.builder.DataSourceBuilder;
import io.trishul.data.datasource.configuration.model.DataSourceConfiguration;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CachingDataSourceManagerTest {
  private CachingDataSourceManager dataSourceManager;

  private DataSource mAdminDs;
  private DataSourceBuilder mDataSourceBuilder;
  private DataSourceConfiguration mDsConfig;
  private Connection mConnection;

  @BeforeEach
  public void init() throws SQLException {
    mAdminDs = mock(DataSource.class);
    mDataSourceBuilder = mock(DataSourceBuilder.class);
    mDsConfig = mock(DataSourceConfiguration.class);
    mConnection = mock(Connection.class);

    // Setup default mocks
    when(mAdminDs.getConnection()).thenReturn(mConnection);
    when(mDataSourceBuilder.clear()).thenReturn(mDataSourceBuilder);
    when(mDataSourceBuilder.url(any())).thenReturn(mDataSourceBuilder);
    when(mDataSourceBuilder.schema(any())).thenReturn(mDataSourceBuilder);
    when(mDataSourceBuilder.username(any())).thenReturn(mDataSourceBuilder);
    when(mDataSourceBuilder.password(any())).thenReturn(mDataSourceBuilder);
    when(mDataSourceBuilder.poolSize(any(Integer.class))).thenReturn(mDataSourceBuilder);
    when(mDataSourceBuilder.autoCommit(any(Boolean.class))).thenReturn(mDataSourceBuilder);

    dataSourceManager = new CachingDataSourceManager(mAdminDs, mDataSourceBuilder);
  }

  @Test
  public void testGetAdminDataSource_ReturnsAdminDataSource() {
    DataSource result = dataSourceManager.getAdminDataSource();

    assertSame(mAdminDs, result);
  }

  @Test
  public void testGetDataSource_ReturnsAdminDataSource_WhenConfigSchemaMatchesAdminSchema()
      throws SQLException, IOException {
    String schemaName = "admin_schema";
    when(mConnection.getSchema()).thenReturn(schemaName);
    when(mDsConfig.getSchemaName()).thenReturn(schemaName);

    DataSource result = dataSourceManager.getDataSource(mDsConfig);

    assertSame(mAdminDs, result);
    verify(mDataSourceBuilder, never()).build();
  }

  @Test
  public void testGetDataSource_BuildsNewDataSource_WhenConfigSchemaDiffersFromAdminSchema()
      throws SQLException, IOException, URISyntaxException {
    String adminSchemaName = "admin_schema";
    String tenantSchemaName = "tenant_schema";
    URI url = new URI("jdbc:postgresql://localhost:5432/testdb");
    String username = "testuser";
    String password = "testpass";
    int poolSize = 10;
    boolean autoCommit = false;

    DataSource mBuiltDs = mock(DataSource.class);

    when(mConnection.getSchema()).thenReturn(adminSchemaName);
    when(mDsConfig.getSchemaName()).thenReturn(tenantSchemaName);
    when(mDsConfig.getUrl()).thenReturn(url);
    when(mDsConfig.getUserName()).thenReturn(username);
    when(mDsConfig.getPassword()).thenReturn(password);
    when(mDsConfig.getPoolSize()).thenReturn(poolSize);
    when(mDsConfig.isAutoCommit()).thenReturn(autoCommit);
    when(mDataSourceBuilder.build()).thenReturn(mBuiltDs);

    DataSource result = dataSourceManager.getDataSource(mDsConfig);

    assertSame(mBuiltDs, result);
    verify(mDataSourceBuilder).clear();
    verify(mDataSourceBuilder).url(url.toString());
    verify(mDataSourceBuilder).schema(tenantSchemaName);
    verify(mDataSourceBuilder).username(username);
    verify(mDataSourceBuilder).password(password);
    verify(mDataSourceBuilder).poolSize(poolSize);
    verify(mDataSourceBuilder).autoCommit(autoCommit);
    verify(mDataSourceBuilder).build();
  }

  @Test
  public void testGetDataSource_BuildsNewDataSource_WhenAdminSchemaIsNull()
      throws SQLException, IOException, URISyntaxException {
    String tenantSchemaName = "tenant_schema";
    URI url = new URI("jdbc:postgresql://localhost:5432/testdb");
    String username = "testuser";
    String password = "testpass";
    int poolSize = 10;
    boolean autoCommit = false;

    DataSource mBuiltDs = mock(DataSource.class);

    when(mConnection.getSchema()).thenReturn(null);
    when(mDsConfig.getSchemaName()).thenReturn(tenantSchemaName);
    when(mDsConfig.getUrl()).thenReturn(url);
    when(mDsConfig.getUserName()).thenReturn(username);
    when(mDsConfig.getPassword()).thenReturn(password);
    when(mDsConfig.getPoolSize()).thenReturn(poolSize);
    when(mDsConfig.isAutoCommit()).thenReturn(autoCommit);
    when(mDataSourceBuilder.build()).thenReturn(mBuiltDs);

    DataSource result = dataSourceManager.getDataSource(mDsConfig);

    assertSame(mBuiltDs, result);
    verify(mDataSourceBuilder).build();
  }

  @Test
  public void testGetDataSource_CachesDataSource_WhenSameConfigUsedMultipleTimes()
      throws SQLException, IOException, URISyntaxException {
    String adminSchemaName = "admin_schema";
    String tenantSchemaName = "tenant_schema";
    URI url = new URI("jdbc:postgresql://localhost:5432/testdb");
    String username = "testuser";
    String password = "testpass";
    int poolSize = 10;
    boolean autoCommit = false;

    DataSource mBuiltDs = mock(DataSource.class);

    when(mConnection.getSchema()).thenReturn(adminSchemaName);
    when(mDsConfig.getSchemaName()).thenReturn(tenantSchemaName);
    when(mDsConfig.getUrl()).thenReturn(url);
    when(mDsConfig.getUserName()).thenReturn(username);
    when(mDsConfig.getPassword()).thenReturn(password);
    when(mDsConfig.getPoolSize()).thenReturn(poolSize);
    when(mDsConfig.isAutoCommit()).thenReturn(autoCommit);
    when(mDataSourceBuilder.build()).thenReturn(mBuiltDs);

    // First call
    DataSource result1 = dataSourceManager.getDataSource(mDsConfig);
    // Second call with same config
    DataSource result2 = dataSourceManager.getDataSource(mDsConfig);

    assertSame(mBuiltDs, result1);
    assertSame(mBuiltDs, result2);
    assertSame(result1, result2);
    // Builder should only be called once due to caching
    verify(mDataSourceBuilder, times(1)).build();
  }

  @Test
  public void testGetDataSource_ThrowsSQLException_WhenAdminConnectionThrowsSQLException()
      throws SQLException {
    SQLException expectedException = new SQLException("Connection failed");
    when(mAdminDs.getConnection()).thenThrow(expectedException);

    SQLException thrownException
        = assertThrows(SQLException.class, () -> dataSourceManager.getDataSource(mDsConfig));

    assertEquals(expectedException, thrownException);
  }

  @Test
  public void testGetDataSource_ThrowsSQLException_WhenDataSourceBuilderThrowsRuntimeException()
      throws SQLException, IOException, URISyntaxException {
    String adminSchemaName = "admin_schema";
    String tenantSchemaName = "tenant_schema";
    URI url = new URI("jdbc:postgresql://localhost:5432/testdb");
    RuntimeException builderException = new RuntimeException("Build failed");

    when(mConnection.getSchema()).thenReturn(adminSchemaName);
    when(mDsConfig.getSchemaName()).thenReturn(tenantSchemaName);
    when(mDsConfig.getUrl()).thenReturn(url);
    when(mDsConfig.getUserName()).thenReturn("testuser");
    when(mDsConfig.getPassword()).thenReturn("testpass");
    when(mDsConfig.getPoolSize()).thenReturn(10);
    when(mDsConfig.isAutoCommit()).thenReturn(false);
    when(mDataSourceBuilder.build()).thenThrow(builderException);

    RuntimeException thrownException
        = assertThrows(RuntimeException.class, () -> dataSourceManager.getDataSource(mDsConfig));

    assertEquals(builderException, thrownException.getCause());
  }

  @Test
  public void testGetDataSource_ThrowsRuntimeException_WhenDataSourceBuilderThrowsIllegalArgumentException()
      throws SQLException, IOException, URISyntaxException {
    String adminSchemaName = "admin_schema";
    String tenantSchemaName = "tenant_schema";
    URI url = new URI("jdbc:postgresql://localhost:5432/testdb");
    IllegalArgumentException builderException = new IllegalArgumentException("Invalid argument");

    when(mConnection.getSchema()).thenReturn(adminSchemaName);
    when(mDsConfig.getSchemaName()).thenReturn(tenantSchemaName);
    when(mDsConfig.getUrl()).thenReturn(url);
    when(mDsConfig.getUserName()).thenReturn("testuser");
    when(mDsConfig.getPassword()).thenReturn("testpass");
    when(mDsConfig.getPoolSize()).thenReturn(10);
    when(mDsConfig.isAutoCommit()).thenReturn(false);
    when(mDataSourceBuilder.build()).thenThrow(builderException);

    RuntimeException thrownException
        = assertThrows(RuntimeException.class, () -> dataSourceManager.getDataSource(mDsConfig));

    assertEquals(builderException, thrownException.getCause());
  }

  @Test
  public void testGetDataSource_ThrowsRuntimeException_WhenDataSourceBuilderThrowsOtherException()
      throws SQLException, IOException, URISyntaxException {
    String adminSchemaName = "admin_schema";
    String tenantSchemaName = "tenant_schema";
    URI url = new URI("jdbc:postgresql://localhost:5432/testdb");
    RuntimeException expectedException = new RuntimeException("Runtime error");

    when(mConnection.getSchema()).thenReturn(adminSchemaName);
    when(mDsConfig.getSchemaName()).thenReturn(tenantSchemaName);
    when(mDsConfig.getUrl()).thenReturn(url);
    when(mDsConfig.getUserName()).thenReturn("testuser");
    when(mDsConfig.getPassword()).thenReturn("testpass");
    when(mDsConfig.getPoolSize()).thenReturn(10);
    when(mDsConfig.isAutoCommit()).thenReturn(false);
    when(mDataSourceBuilder.build()).thenThrow(expectedException);

    RuntimeException thrownException
        = assertThrows(RuntimeException.class, () -> dataSourceManager.getDataSource(mDsConfig));

    assertEquals(expectedException, thrownException.getCause());
  }

  @Test
  public void testGetDataSource_CreatesDifferentDataSources_ForDifferentConfigurations()
      throws SQLException, IOException, URISyntaxException {
    String adminSchemaName = "admin_schema";

    // First configuration
    DataSourceConfiguration mDsConfig1 = mock(DataSourceConfiguration.class);
    String tenantSchemaName1 = "tenant_schema_1";
    URI url1 = new URI("jdbc:postgresql://localhost:5432/testdb1");
    DataSource mBuiltDs1 = mock(DataSource.class);

    when(mDsConfig1.getSchemaName()).thenReturn(tenantSchemaName1);
    when(mDsConfig1.getUrl()).thenReturn(url1);
    when(mDsConfig1.getUserName()).thenReturn("testuser1");
    when(mDsConfig1.getPassword()).thenReturn("testpass1");
    when(mDsConfig1.getPoolSize()).thenReturn(10);
    when(mDsConfig1.isAutoCommit()).thenReturn(false);

    // Second configuration
    DataSourceConfiguration mDsConfig2 = mock(DataSourceConfiguration.class);
    String tenantSchemaName2 = "tenant_schema_2";
    URI url2 = new URI("jdbc:postgresql://localhost:5432/testdb2");
    DataSource mBuiltDs2 = mock(DataSource.class);

    when(mDsConfig2.getSchemaName()).thenReturn(tenantSchemaName2);
    when(mDsConfig2.getUrl()).thenReturn(url2);
    when(mDsConfig2.getUserName()).thenReturn("testuser2");
    when(mDsConfig2.getPassword()).thenReturn("testpass2");
    when(mDsConfig2.getPoolSize()).thenReturn(20);
    when(mDsConfig2.isAutoCommit()).thenReturn(true);

    when(mConnection.getSchema()).thenReturn(adminSchemaName);
    when(mDataSourceBuilder.build()).thenReturn(mBuiltDs1).thenReturn(mBuiltDs2);

    DataSource result1 = dataSourceManager.getDataSource(mDsConfig1);
    DataSource result2 = dataSourceManager.getDataSource(mDsConfig2);

    assertSame(mBuiltDs1, result1);
    assertSame(mBuiltDs2, result2);
    assertNotSame(result1, result2);
    verify(mDataSourceBuilder, times(2)).build();
  }

  @Test
  public void testGetDataSource_BuildsNewDataSource_WhenConfigSchemaIsNullButAdminSchemaIsNot()
      throws SQLException, IOException, URISyntaxException {
    String adminSchemaName = "admin_schema";
    URI url = new URI("jdbc:postgresql://localhost:5432/testdb");
    String username = "testuser";
    String password = "testpass";
    int poolSize = 10;
    boolean autoCommit = false;

    DataSource mBuiltDs = mock(DataSource.class);

    when(mConnection.getSchema()).thenReturn(adminSchemaName);
    when(mDsConfig.getSchemaName()).thenReturn(null);
    when(mDsConfig.getUrl()).thenReturn(url);
    when(mDsConfig.getUserName()).thenReturn(username);
    when(mDsConfig.getPassword()).thenReturn(password);
    when(mDsConfig.getPoolSize()).thenReturn(poolSize);
    when(mDsConfig.isAutoCommit()).thenReturn(autoCommit);
    when(mDataSourceBuilder.build()).thenReturn(mBuiltDs);

    DataSource result = dataSourceManager.getDataSource(mDsConfig);

    assertSame(mBuiltDs, result);
    verify(mDataSourceBuilder).build();
  }

  @Test
  public void testGetDataSource_BuildsNewDataSource_WhenBothSchemasAreNull()
      throws SQLException, IOException, URISyntaxException {
    URI url = new URI("jdbc:postgresql://localhost:5432/testdb");
    String username = "testuser";
    String password = "testpass";
    int poolSize = 10;
    boolean autoCommit = false;

    DataSource mBuiltDs = mock(DataSource.class);

    when(mConnection.getSchema()).thenReturn(null);
    when(mDsConfig.getSchemaName()).thenReturn(null);
    when(mDsConfig.getUrl()).thenReturn(url);
    when(mDsConfig.getUserName()).thenReturn(username);
    when(mDsConfig.getPassword()).thenReturn(password);
    when(mDsConfig.getPoolSize()).thenReturn(poolSize);
    when(mDsConfig.isAutoCommit()).thenReturn(autoCommit);
    when(mDataSourceBuilder.build()).thenReturn(mBuiltDs);

    DataSource result = dataSourceManager.getDataSource(mDsConfig);

    assertSame(mBuiltDs, result);
    verify(mDataSourceBuilder).build();
  }
}
