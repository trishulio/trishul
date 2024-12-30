package io.trishul.data.datasource.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.zaxxer.hikari.HikariDataSource;
import io.trishul.data.datasource.configuration.builder.DataSourceBuilder;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HikariDataSourceBuilderTest {
  private DataSourceBuilder builder;

  @BeforeEach
  public void init() {
    builder = new HikariDataSourceBuilder();
  }

  @Test
  public void testBuild_ReturnsDataSource_CreatedWithProps() throws SQLException {
    DataSource ds = builder.url("jdbc:hsqldb:mem:unittestdb").username("test_user")
        .password("test_pass").autoCommit(false).build();

    assertTrue(ds instanceof HikariDataSource);

    Connection conn = ds.getConnection();
    assertEquals(false, conn.getAutoCommit());

    DatabaseMetaData md = conn.getMetaData();
    assertEquals("test_user", md.getUserName());
    assertEquals("jdbc:hsqldb:mem:unittestdb", md.getURL());
  }

  @Test
  public void testSchema_SetsSchemaValue() {
    builder.schema("schema");
    String value = builder.schema();
    assertEquals("schema", value);
  }

  @Test
  public void testUsername_SetsUsername() {
    builder.username("username");
    String value = builder.username();
    assertEquals("username", value);
  }

  @Test
  public void testPassword_SetsPassword() {
    builder.password("password");
    String value = builder.password();
    assertEquals("password", value);
  }

  @Test
  public void testUrl_SetsUrl() {
    builder.url("url");
    String value = builder.url();
    assertEquals("url", value);
  }

  @Test
  public void testAutoCommit_SetsAutoCommit() {
    builder.autoCommit(false);
    boolean value = builder.autoCommit();
    assertFalse(value);
  }

  @Test
  public void testPoolSize_SetsPoolSize() {
    builder.poolSize(123);
    int poolSize = builder.poolSize();
    assertEquals(123, poolSize);
  }

  @Test
  public void testClear_ClearsAllValues() {
    builder.username("username").password("password").schema("schema").autoCommit(true).url("url")
        .poolSize(99).clear();
    assertNull(builder.username());
    assertNull(builder.password());
    assertNull(builder.schema());
    assertNull(builder.url());
    assertFalse(builder.autoCommit());
    assertEquals(-1, builder.poolSize());
  }
}
