package io.trishul.repo.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import io.trishul.repo.jpa.query.resolver.QueryResolver;
import io.trishul.repo.jpa.repository.service.TransactionService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RepositoryAutoConfigurationTest {
  private RepositoryAutoConfiguration repoConf;

  @BeforeEach
  void init() {
    repoConf = new RepositoryAutoConfiguration();
  }

  @Test
  void testQueryResolver_ReturnsInstanceOfQueryResolver() {
    EntityManager mEm = mock(EntityManager.class);
    QueryResolver queryResolver = repoConf.queryResolver(mEm);

    assertSame(QueryResolver.class, queryResolver.getClass());
  }

  @Test
  void testTransactionService_ReturnsInstanceOfTransactionService() {
    TransactionService transactionService = repoConf.transactionService();

    assertNotNull(transactionService);
    assertSame(TransactionService.class, transactionService.getClass());
  }
}
