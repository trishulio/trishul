package sh.trishul.repo.aggregation.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.repo.aggregation.repo.AggregationRepository;
import sh.trishul.repo.aggregation.service.AggregationService;
import sh.trishul.repo.jpa.query.resolver.QueryResolver;

class RepoAggregationAutoConfigurationTest {
  private RepoAggregationAutoConfiguration config;

  @BeforeEach
  void init() {
    config = new RepoAggregationAutoConfiguration();
  }

  @Test
  void testAggrRepo_ReturnsInstanceOfAggregationRepository() {
    QueryResolver mResolver = mock(QueryResolver.class);

    AggregationRepository aggrRepo = config.aggregationRepository(mResolver);

    assertSame(AggregationRepository.class, aggrRepo.getClass());
  }

  @Test
  void testAggrService_ReturnsInstanceOfAggregationService() {
    AggregationRepository mRepo = mock(AggregationRepository.class);
    AggregationService service = config.aggregationService(mRepo);
    assertSame(AggregationService.class, service.getClass());
  }
}
