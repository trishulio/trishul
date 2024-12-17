package io.trishul.test.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public abstract class DbMockUtil {
    public static PreparedStatement mockPs(Connection conn, String sql, boolean isResultSet) throws SQLException {
        PreparedStatement stmt = mockPs(conn, sql);
        doReturn(isResultSet).when(stmt).execute();
        return stmt;
    }

    public static PreparedStatement mockPs(Connection conn, String sql, int updateCount) throws SQLException {
        PreparedStatement stmt = mockPs(conn, sql);
        doReturn(updateCount).when(stmt).executeUpdate();

        return stmt;
    }

    public static PreparedStatement mockPs(Connection conn, String sql, Object[][] data) throws SQLException {
        PreparedStatement stmt = mockPs(conn, sql);

        AtomicInteger cursor = new AtomicInteger(0);
        ResultSet rs = mock(ResultSet.class);

        doAnswer(inv -> data.length > cursor.getAndIncrement()).when(rs).next();
        doAnswer(inv -> data[cursor.get()][inv.getArgument(0, Integer.class)]).when(rs).getObject(anyInt());
        doAnswer(inv -> {
            verify(stmt, times(0)).close();
            return null;
        }).when(rs).close();

        doReturn(rs).when(stmt).executeQuery();

        return stmt;
    }

    public static PreparedStatement mockPs(Connection conn, String sql) throws SQLException {
        PreparedStatement stmt = mock(PreparedStatement.class);
        doReturn(stmt).when(conn).prepareStatement(sql);

        doAnswer(inv -> {
            verify(conn, times(0)).close();
            return null;
        }).when(stmt).close();

        return stmt;
    }

    public static void createAndSetMockConnection(DataSource ds, String username, String schema, String url, boolean autoCommit) throws SQLException {
        Map<String, Object> data = new HashMap<>();

        Connection fixedUsernameConn = mockConnection(username, schema, url, autoCommit);
        Connection dynamicUsername = mockConnection(data, username, schema, url, autoCommit);

        doReturn(fixedUsernameConn).when(ds).getConnection();

        doAnswer(inv -> {
            data.put("username", inv.getArgument(0, String.class));
            return dynamicUsername;
        }).when(ds).getConnection(anyString(), anyString());
    }

    public static Connection mockConnection(String username, String schema, String url, boolean autoCommit) throws SQLException {
        Map<String, Object> data = new HashMap<>();
        return mockConnection(data, username, schema, url, autoCommit);
    }

    public static Connection mockConnection(Map<String, Object> data, String username, String schema, String url, boolean autoCommit) throws SQLException {
        data.put("username", username);
        data.put("url", url);
        data.put("autoCommit", autoCommit);
        data.put("schema", schema);

        DatabaseMetaData md = mock(DatabaseMetaData.class);
        doAnswer(inv -> data.get("url")).when(md).getURL();
        doAnswer(inv -> data.get("username")).when(md).getUserName();

        Connection conn = mock(Connection.class);
        doReturn(md).when(conn).getMetaData();
        doAnswer(inv -> data.get("schema")).when(conn).getSchema();
        doAnswer(inv -> data.get("autoCommit")).when(conn).getAutoCommit();

        doAnswer(inv -> data.put("autoCommit", inv.getArgument(0, Boolean.class))).when(conn).setAutoCommit(anyBoolean());
        doAnswer(inv -> data.put("schema", inv.getArgument(0, String.class))).when(conn).setSchema(anyString());

        return conn;
    }
}
