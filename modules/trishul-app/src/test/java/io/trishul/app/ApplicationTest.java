package io.trishul.app;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = "spring.config.name=app-application")
class ApplicationTest {
  private static final Logger log = LoggerFactory.getLogger(ApplicationTest.class);

  @Test
  void contextLoads() {}

  @Test
  public void testAutoCommitIsSetToFalse(@Autowired DataSource ds) throws SQLException {
    try (Connection conn = ds.getConnection()) {
      log.debug("Database Product Name: {}", conn.getMetaData().getDatabaseProductName());
      log.debug("Database Username: {}", conn.getMetaData().getUserName());
      assertFalse(conn.getAutoCommit());
    }
  }
}
