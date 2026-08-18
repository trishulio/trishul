package sh.trishul.data.datasource.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import sh.trishul.data.datasource.builder.HikariDataSourceBuilder;
import sh.trishul.data.datasource.configuration.model.DataSourceConfiguration;

class CachingDataSourceManagerTest {
  private CachingDataSourceManager dataSourceManager;

  private DataSource mAdminDs;
  private DataSourceConfiguration mDsConfig;
  private Connection mConnection;
  private MockedConstruction<HikariDataSourceBuilder> mockedBuilderConstruction;

  private DataSource mBuiltDs;
  private Throwable builderException;

  @BeforeEach
  void init() throws SQLException {
    mAdminDs = mock(DataSource.class);
    mDsConfig = mock(DataSourceConfiguration.class);
    mConnection = mock(Connection.class);

    mBuiltDs = mock(DataSource.class);
    builderException = null;

    // Setup default mocks
    when(mAdminDs.getConnection()).thenReturn(mConnection);

    mockedBuilderConstruction = mockConstruction(HikariDataSourceBuilder.class, (mock, context) -> {
      when(mock.clear()).thenReturn(mock);
      when(mock.url(any())).thenReturn(mock);
      when(mock.schema(any())).thenReturn(mock);
      when(mock.username(any())).thenReturn(mock);
      when(mock.password(any())).thenReturn(mock);
      when(mock.poolSize(any(Integer.class))).thenReturn(mock);
      when(mock.autoCommit(any(Boolean.class))).thenReturn(mock);
      when(mock.build()).thenAnswer(invocation -> {
        if (builderException != null) {
          throw builderException;
        }
        return mBuiltDs;
      });
    });

    dataSourceManager = new CachingDataSourceManager(mAdminDs);
  }

  @AfterEach
  void tearDown() {
    if (mockedBuilderConstruction != null) {
      mockedBuilderConstruction.close();
    }
  }

  @Test
  void testGetAdminDataSource_ReturnsAdminDataSource() {
    DataSource result = dataSourceManager.getAdminDataSource();

    assertSame(mAdminDs, result);
  }

  @Test
  void testGetDataSource_ReturnsAdminDataSource_WhenConfigSchemaMatchesAdminSchema()
      throws SQLException, IOException {
    String schemaName = "admin_schema";
    when(mConnection.getSchema()).thenReturn(schemaName);
    when(mDsConfig.getSchemaName()).thenReturn(schemaName);

    DataSource result = dataSourceManager.getDataSource(mDsConfig);

    assertSame(mAdminDs, result);
    assertEquals(0, mockedBuilderConstruction.constructed().size());
  }

  @Test
  void testGetDataSource_BuildsNewDataSource_WhenConfigSchemaDiffersFromAdminSchema()
      throws SQLException, IOException, URISyntaxException {
    String adminSchemaName = "admin_schema";
    String tenantSchemaName = "tenant_schema";
    URI url = new URI("jdbc:postgresql://localhost:5432/testdb");
    String username = "testuser";
    String password = "testpass";
    int poolSize = 10;
    boolean autoCommit = false;

    when(mConnection.getSchema()).thenReturn(adminSchemaName);
    when(mDsConfig.getSchemaName()).thenReturn(tenantSchemaName);
    when(mDsConfig.getUrl()).thenReturn(url);
    when(mDsConfig.getUserName()).thenReturn(username);
    when(mDsConfig.getPassword()).thenReturn(password);
    when(mDsConfig.getPoolSize()).thenReturn(poolSize);
    when(mDsConfig.isAutoCommit()).thenReturn(autoCommit);

    DataSource result = dataSourceManager.getDataSource(mDsConfig);

    assertSame(mBuiltDs, result);

    List<HikariDataSourceBuilder> constructed = mockedBuilderConstruction.constructed();
    assertEquals(1, constructed.size());
    HikariDataSourceBuilder mockBuilder = constructed.get(0);

    verify(mockBuilder).clear();
    verify(mockBuilder).url(url.toString());
    verify(mockBuilder).schema(tenantSchemaName);
    verify(mockBuilder).username(username);
    verify(mockBuilder).password(password);
    verify(mockBuilder).poolSize(poolSize);
    verify(mockBuilder).autoCommit(autoCommit);
    verify(mockBuilder).build();
  }

  @Test
  void testGetDataSource_BuildsNewDataSource_WhenAdminSchemaIsNull()
      throws SQLException, IOException, URISyntaxException {
    String tenantSchemaName = "tenant_schema";
    URI url = new URI("jdbc:postgresql://localhost:5432/testdb");
    String username = "testuser";
    String password = "testpass";
    int poolSize = 10;
    boolean autoCommit = false;

    when(mConnection.getSchema()).thenReturn(null);
    when(mDsConfig.getSchemaName()).thenReturn(tenantSchemaName);
    when(mDsConfig.getUrl()).thenReturn(url);
    when(mDsConfig.getUserName()).thenReturn(username);
    when(mDsConfig.getPassword()).thenReturn(password);
    when(mDsConfig.getPoolSize()).thenReturn(poolSize);
    when(mDsConfig.isAutoCommit()).thenReturn(autoCommit);

    DataSource result = dataSourceManager.getDataSource(mDsConfig);

    assertSame(mBuiltDs, result);

    List<HikariDataSourceBuilder> constructed = mockedBuilderConstruction.constructed();
    assertEquals(1, constructed.size());
    HikariDataSourceBuilder mockBuilder = constructed.get(0);
    verify(mockBuilder).build();
  }

  @Test
  void testGetDataSource_CachesDataSource_WhenSameConfigUsedMultipleTimes()
      throws SQLException, IOException, URISyntaxException {
    String adminSchemaName = "admin_schema";
    String tenantSchemaName = "tenant_schema";
    URI url = new URI("jdbc:postgresql://localhost:5432/testdb");
    String username = "testuser";
    String password = "testpass";
    int poolSize = 10;
    boolean autoCommit = false;

    when(mConnection.getSchema()).thenReturn(adminSchemaName);
    when(mDsConfig.getSchemaName()).thenReturn(tenantSchemaName);
    when(mDsConfig.getUrl()).thenReturn(url);
    when(mDsConfig.getUserName()).thenReturn(username);
    when(mDsConfig.getPassword()).thenReturn(password);
    when(mDsConfig.getPoolSize()).thenReturn(poolSize);
    when(mDsConfig.isAutoCommit()).thenReturn(autoCommit);

    // First call
    DataSource result1 = dataSourceManager.getDataSource(mDsConfig);
    // Second call with same config
    DataSource result2 = dataSourceManager.getDataSource(mDsConfig);

    assertSame(mBuiltDs, result1);
    assertSame(mBuiltDs, result2);
    assertSame(result1, result2);

    List<HikariDataSourceBuilder> constructed = mockedBuilderConstruction.constructed();
    assertEquals(1, constructed.size());
    HikariDataSourceBuilder mockBuilder = constructed.get(0);
    verify(mockBuilder, times(1)).build();
  }

  @Test
  void testGetDataSource_ThrowsSQLException_WhenAdminConnectionThrowsSQLException()
      throws SQLException {
    SQLException expectedException = new SQLException("Connection failed");
    when(mAdminDs.getConnection()).thenThrow(expectedException);

    SQLException thrownException
        = assertThrows(SQLException.class, () -> dataSourceManager.getDataSource(mDsConfig));

    assertEquals(expectedException, thrownException);
  }

  @Test
  void testGetDataSource_ThrowsSQLException_WhenDataSourceBuilderThrowsRuntimeException()
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

    this.builderException = builderException;

    RuntimeException thrownException
        = assertThrows(RuntimeException.class, () -> dataSourceManager.getDataSource(mDsConfig));

    assertSame(builderException, thrownException);
  }

  @Test
  void testGetDataSource_ThrowsRuntimeException_WhenDataSourceBuilderThrowsIllegalArgumentException()
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

    this.builderException = builderException;

    IllegalArgumentException thrownException = assertThrows(IllegalArgumentException.class,
        () -> dataSourceManager.getDataSource(mDsConfig));

    assertSame(builderException, thrownException);
  }

  @Test
  void testGetDataSource_ThrowsRuntimeException_WhenDataSourceBuilderThrowsOtherException()
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

    this.builderException = expectedException;

    RuntimeException thrownException
        = assertThrows(RuntimeException.class, () -> dataSourceManager.getDataSource(mDsConfig));

    assertSame(expectedException, thrownException);
  }

  @Test
  void testGetDataSource_ThrowsError_WhenDataSourceBuilderThrowsError()
      throws SQLException, IOException, URISyntaxException {
    String adminSchemaName = "admin_schema";
    String tenantSchemaName = "tenant_schema";
    URI url = new URI("jdbc:postgresql://localhost:5432/testdb");
    AssertionError expectedError = new AssertionError("Mock Assertion Error");

    when(mConnection.getSchema()).thenReturn(adminSchemaName);
    when(mDsConfig.getSchemaName()).thenReturn(tenantSchemaName);
    when(mDsConfig.getUrl()).thenReturn(url);
    when(mDsConfig.getUserName()).thenReturn("testuser");
    when(mDsConfig.getPassword()).thenReturn("testpass");
    when(mDsConfig.getPoolSize()).thenReturn(10);
    when(mDsConfig.isAutoCommit()).thenReturn(false);

    this.builderException = expectedError;

    AssertionError thrownError
        = assertThrows(AssertionError.class, () -> dataSourceManager.getDataSource(mDsConfig));

    assertSame(expectedError, thrownError);
  }

  @Test
  void testGetDataSource_CreatesDifferentDataSources_ForDifferentConfigurations()
      throws SQLException, IOException, URISyntaxException {
    String adminSchemaName = "admin_schema";

    // First configuration
    DataSourceConfiguration mDsConfig1 = mock(DataSourceConfiguration.class);
    String tenantSchemaName1 = "tenant_schema_1";
    URI url1 = new URI("jdbc:postgresql://localhost:5432/testdb1");

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

    when(mDsConfig2.getSchemaName()).thenReturn(tenantSchemaName2);
    when(mDsConfig2.getUrl()).thenReturn(url2);
    when(mDsConfig2.getUserName()).thenReturn("testuser2");
    when(mDsConfig2.getPassword()).thenReturn("testpass2");
    when(mDsConfig2.getPoolSize()).thenReturn(20);
    when(mDsConfig2.isAutoCommit()).thenReturn(true);

    when(mConnection.getSchema()).thenReturn(adminSchemaName);

    // Set up built DS
    DataSource mBuiltDs1 = mock(DataSource.class);
    DataSource mBuiltDs2 = mock(DataSource.class);

    // We can't easily return different mocks dynamically unless we use an answer.
    // Let's modify the build answer to return mBuiltDs1 first, then mBuiltDs2.
    this.mBuiltDs = mBuiltDs1; // Default

    // We can set it dynamically by checking invocation or counting.
    // Since we want to return mBuiltDs1 for url1 and mBuiltDs2 for url2:
    // Let's do it in the answer:
    if (mockedBuilderConstruction != null) {
      mockedBuilderConstruction.close();
    }
    mockedBuilderConstruction = mockConstruction(HikariDataSourceBuilder.class, (mock, context) -> {
      when(mock.clear()).thenReturn(mock);
      when(mock.url(any())).thenReturn(mock);
      when(mock.schema(any())).thenReturn(mock);
      when(mock.username(any())).thenReturn(mock);
      when(mock.password(any())).thenReturn(mock);
      when(mock.poolSize(any(Integer.class))).thenReturn(mock);
      when(mock.autoCommit(any(Boolean.class))).thenReturn(mock);
      when(mock.build()).thenAnswer(invocation -> {
        // If clear() was followed by url("...testdb1")
        // But since we mock everything, we can just return based on a count or state.
        // Let's use a counter or list.
        int constructedCount = mockedBuilderConstruction.constructed().size();
        if (constructedCount <= 1) {
          return mBuiltDs1;
        } else {
          return mBuiltDs2;
        }
      });
    });

    DataSource result1 = dataSourceManager.getDataSource(mDsConfig1);
    DataSource result2 = dataSourceManager.getDataSource(mDsConfig2);

    assertSame(mBuiltDs1, result1);
    assertSame(mBuiltDs2, result2);
    assertNotSame(result1, result2);

    List<HikariDataSourceBuilder> constructed = mockedBuilderConstruction.constructed();
    assertEquals(2, constructed.size());
    verify(constructed.get(0)).build();
    verify(constructed.get(1)).build();
  }

  @Test
  void testGetDataSource_BuildsNewDataSource_WhenConfigSchemaIsNullButAdminSchemaIsNot()
      throws SQLException, IOException, URISyntaxException {
    String adminSchemaName = "admin_schema";
    URI url = new URI("jdbc:postgresql://localhost:5432/testdb");
    String username = "testuser";
    String password = "testpass";
    int poolSize = 10;
    boolean autoCommit = false;

    when(mConnection.getSchema()).thenReturn(adminSchemaName);
    when(mDsConfig.getSchemaName()).thenReturn(null);
    when(mDsConfig.getUrl()).thenReturn(url);
    when(mDsConfig.getUserName()).thenReturn(username);
    when(mDsConfig.getPassword()).thenReturn(password);
    when(mDsConfig.getPoolSize()).thenReturn(poolSize);
    when(mDsConfig.isAutoCommit()).thenReturn(autoCommit);

    DataSource result = dataSourceManager.getDataSource(mDsConfig);

    assertSame(mBuiltDs, result);

    List<HikariDataSourceBuilder> constructed = mockedBuilderConstruction.constructed();
    assertEquals(1, constructed.size());
    verify(constructed.get(0)).build();
  }

  @Test
  void testGetDataSource_BuildsNewDataSource_WhenBothSchemasAreNull()
      throws SQLException, IOException, URISyntaxException {
    URI url = new URI("jdbc:postgresql://localhost:5432/testdb");
    String username = "testuser";
    String password = "testpass";
    int poolSize = 10;
    boolean autoCommit = false;

    when(mConnection.getSchema()).thenReturn(null);
    when(mDsConfig.getSchemaName()).thenReturn(null);
    when(mDsConfig.getUrl()).thenReturn(url);
    when(mDsConfig.getUserName()).thenReturn(username);
    when(mDsConfig.getPassword()).thenReturn(password);
    when(mDsConfig.getPoolSize()).thenReturn(poolSize);
    when(mDsConfig.isAutoCommit()).thenReturn(autoCommit);

    DataSource result = dataSourceManager.getDataSource(mDsConfig);
    assertSame(mBuiltDs, result);

    List<HikariDataSourceBuilder> constructed = mockedBuilderConstruction.constructed();
    assertEquals(1, constructed.size());
    verify(constructed.get(0)).build();
  }

  @Test
  void testGetDataSource_ThrowsIOException_WhenDataSourceBuilderThrowsIOException()
      throws SQLException, IOException, URISyntaxException {
    String adminSchemaName = "admin_schema";
    String tenantSchemaName = "tenant_schema";
    URI url = new URI("jdbc:postgresql://localhost:5432/testdb");
    IOException expectedException = new IOException("IO error");

    when(mConnection.getSchema()).thenReturn(adminSchemaName);
    when(mDsConfig.getSchemaName()).thenReturn(tenantSchemaName);
    when(mDsConfig.getUrl()).thenReturn(url);
    when(mDsConfig.getUserName()).thenReturn("testuser");
    when(mDsConfig.getPassword()).thenReturn("testpass");
    when(mDsConfig.getPoolSize()).thenReturn(10);
    when(mDsConfig.isAutoCommit()).thenReturn(false);

    this.builderException = expectedException;

    IOException thrownException
        = assertThrows(IOException.class, () -> dataSourceManager.getDataSource(mDsConfig));

    assertEquals(expectedException, thrownException);
  }
}
