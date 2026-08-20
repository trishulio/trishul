package sh.trishul.test.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.sql.DataSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public abstract class DbMockUtil {
  public static PreparedStatement mockPs(Connection conn, String sql, boolean isResultSet)
      throws SQLException {
    PreparedStatement stmt = mockPs(conn, sql);
    Mockito.when(stmt.execute()).thenReturn(isResultSet);
    return stmt;
  }

  public static PreparedStatement mockPs(Connection conn, String sql, int updateCount)
      throws SQLException {
    PreparedStatement stmt = mockPs(conn, sql);
    Mockito.when(stmt.executeUpdate()).thenReturn(updateCount);

    return stmt;
  }

  public static PreparedStatement mockPs(Connection conn, String sql, Object[][] data)
      throws SQLException {
    PreparedStatement stmt = mockPs(conn, sql);

    AtomicInteger cursor = new AtomicInteger(0);
    ResultSet rs = Mockito.mock(ResultSet.class);

    if (data.length > 0) {
      Mockito.when(rs.next()).thenAnswer(inv -> data.length > cursor.getAndIncrement());
      Mockito.when(rs.getObject(ArgumentMatchers.anyInt()))
          .thenAnswer(inv -> data[cursor.get()][inv.getArgument(0, Integer.class)]);
    }
    Mockito.doAnswer(inv -> {
      Mockito.verify(stmt, Mockito.times(0)).close();
      return null;
    }).when(rs).close();

    Mockito.when(stmt.executeQuery()).thenReturn(rs);

    return stmt;
  }

  public static PreparedStatement mockPs(Connection conn, String sql) throws SQLException {
    PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
    Mockito.when(conn.prepareStatement(sql)).thenReturn(stmt);

    Mockito.doAnswer(inv -> {
      Mockito.verify(conn, Mockito.times(0)).close();
      return null;
    }).when(stmt).close();

    return stmt;
  }

  public static void createAndSetMockConnection(DataSource ds, String username, String schema,
      String url, boolean autoCommit) throws SQLException {
    Map<String, Object> data = new HashMap<>();

    Connection fixedUsernameConn = mockConnection(username, schema, url, autoCommit);
    Connection dynamicUsername = mockConnection(data, username, schema, url, autoCommit);

    Mockito.when(ds.getConnection()).thenReturn(fixedUsernameConn);

    Mockito.when(ds.getConnection(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
        .thenAnswer(inv -> {
          data.put("username", inv.getArgument(0, String.class));
          return dynamicUsername;
        });
  }

  public static Connection mockConnection(String username, String schema, String url,
      boolean autoCommit) throws SQLException {
    Map<String, Object> data = new HashMap<>();
    return mockConnection(data, username, schema, url, autoCommit);
  }

  public static Connection mockConnection(Map<String, Object> data, String username, String schema,
      String url, boolean autoCommit) throws SQLException {
    data.put("username", username);
    data.put("url", url);
    data.put("autoCommit", autoCommit);
    data.put("schema", schema);

    DatabaseMetaData md = Mockito.mock(DatabaseMetaData.class);
    Mockito.when(md.getURL()).thenAnswer(inv -> data.get("url"));
    Mockito.when(md.getUserName()).thenAnswer(inv -> data.get("username"));

    Connection conn = Mockito.mock(Connection.class);
    Mockito.when(conn.getMetaData()).thenReturn(md);
    Mockito.when(conn.getSchema()).thenAnswer(inv -> data.get("schema"));
    Mockito.when(conn.getAutoCommit()).thenAnswer(inv -> data.get("autoCommit"));

    Mockito.doAnswer(inv -> data.put("autoCommit", inv.getArgument(0, Boolean.class))).when(conn)
        .setAutoCommit(ArgumentMatchers.anyBoolean());
    Mockito.doAnswer(inv -> data.put("schema", inv.getArgument(0, String.class))).when(conn)
        .setSchema(ArgumentMatchers.anyString());

    return conn;
  }
}
