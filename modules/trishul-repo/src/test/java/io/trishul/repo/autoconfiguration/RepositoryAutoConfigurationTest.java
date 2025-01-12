package io.trishul.repo.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import io.trishul.repo.jpa.query.resolver.QueryResolver;
import jakarta.persistence.EntityManager;

public class RepositoryAutoConfigurationTest {
  private RepositoryAutoConfiguration repoConf;

  @BeforeEach
  public void init() {
    repoConf = new RepositoryAutoConfiguration();
  }

  @Test
  public void testQueryResolver_ReturnsInstanceOfQueryResolver() {
    EntityManager mEm = mock(EntityManager.class);
    QueryResolver queryResolver = repoConf.queryResolver(mEm);

    assertSame(QueryResolver.class, queryResolver.getClass());
  }
}
