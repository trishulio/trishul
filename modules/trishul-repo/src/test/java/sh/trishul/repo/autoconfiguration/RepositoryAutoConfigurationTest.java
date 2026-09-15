package sh.trishul.repo.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.repo.filter.ArchiveFilterAspect;
import sh.trishul.repo.filter.ArchiveFilterHandlerInterceptor;
import sh.trishul.repo.filter.ArchiveFilterWebMvcConfigurer;
import sh.trishul.repo.jpa.query.resolver.QueryResolver;
import sh.trishul.repo.jpa.repository.service.TransactionService;

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

  @Test
  void testArchiveFilterHandlerInterceptor_ReturnsNonNull() {
    EntityManager mEm = mock(EntityManager.class);
    ArchiveFilterHandlerInterceptor interceptor = repoConf.archiveFilterHandlerInterceptor(mEm);

    assertNotNull(interceptor);
    assertSame(ArchiveFilterHandlerInterceptor.class, interceptor.getClass());
  }

  @Test
  void testArchiveFilterAspect_ReturnsNonNull() {
    EntityManager mEm = mock(EntityManager.class);
    ArchiveFilterAspect aspect = repoConf.archiveFilterAspect(mEm);

    assertNotNull(aspect);
    assertSame(ArchiveFilterAspect.class, aspect.getClass());
  }

  @Test
  void testArchiveFilterWebMvcConfigurer_ReturnsNonNull() {
    ArchiveFilterHandlerInterceptor mockInterceptor = mock(ArchiveFilterHandlerInterceptor.class);
    ArchiveFilterWebMvcConfigurer configurer
        = repoConf.archiveFilterWebMvcConfigurer(mockInterceptor);

    assertNotNull(configurer);
    assertSame(ArchiveFilterWebMvcConfigurer.class, configurer.getClass());
  }
}
