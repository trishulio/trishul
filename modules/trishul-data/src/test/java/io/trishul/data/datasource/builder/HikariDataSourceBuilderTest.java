package io.trishul.data.datasource.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.zaxxer.hikari.HikariDataSource;
import io.trishul.data.datasource.configuration.builder.DataSourceBuilder;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HikariDataSourceBuilderTest {
  private DataSourceBuilder builder;

  @BeforeEach
  void init() {
    builder = new HikariDataSourceBuilder();
  }

  @Test
  void testBuild_ReturnsDataSource_CreatedWithProps() throws SQLException {
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
  void testSchema_SetsSchemaValue() {
    assertSame(builder, builder.schema("schema"));
    String value = builder.schema();
    assertEquals("schema", value);
  }

  @Test
  void testUsername_SetsUsername() {
    assertSame(builder, builder.username("username"));
    String value = builder.username();
    assertEquals("username", value);
  }

  @Test
  void testPassword_SetsPassword() {
    assertSame(builder, builder.password("password"));
    String value = builder.password();
    assertEquals("password", value);
  }

  @Test
  void testUrl_SetsUrl() {
    assertSame(builder, builder.url("url"));
    String value = builder.url();
    assertEquals("url", value);
  }

  @Test
  void testAutoCommit_SetsAutoCommit() {
    assertSame(builder, builder.autoCommit(false));
    assertFalse(builder.autoCommit());

    assertSame(builder, builder.autoCommit(true));
    assertTrue(builder.autoCommit());
  }

  @Test
  void testPoolSize_SetsPoolSize() {
    assertSame(builder, builder.poolSize(123));
    int poolSize = builder.poolSize();
    assertEquals(123, poolSize);
  }

  @Test
  void testClear_ClearsAllValues() {
    builder.username("username").password("password").schema("schema").autoCommit(true).url("url")
        .poolSize(99);
    DataSourceBuilder result = builder.clear();
    assertSame(builder, result);
    assertNull(builder.username());
    assertNull(builder.password());
    assertNull(builder.schema());
    assertNull(builder.url());
    assertFalse(builder.autoCommit());
    assertEquals(-1, builder.poolSize());
  }

  @Test
  void testBuild_IgnoresPoolSize_WhenPoolSizeIsZero() throws SQLException {
    DataSource ds = builder.url("jdbc:hsqldb:mem:unittestdb2").username("test_user")
        .password("test_pass").poolSize(0).build();
    assertTrue(ds instanceof HikariDataSource);
  }

  @Test
  void testBuild_SetsSchemaOnDataSource() throws SQLException {
    DataSource ds = builder.url("jdbc:hsqldb:mem:unittestdb3").username("test_user")
        .password("test_pass").schema("INFORMATION_SCHEMA").build();

    assertTrue(ds instanceof HikariDataSource);
    assertEquals("INFORMATION_SCHEMA", ((HikariDataSource) ds).getSchema());
  }

  @Test
  void testBuild_SetsPoolSize_WhenPoolSizeIsGreaterThanZero() {
    DataSource ds = builder.url("jdbc:hsqldb:mem:unittestdb4").username("test_user")
        .password("test_pass").poolSize(5).build();

    assertTrue(ds instanceof HikariDataSource);
    assertEquals(5, ((HikariDataSource) ds).getMaximumPoolSize());
  }
}
