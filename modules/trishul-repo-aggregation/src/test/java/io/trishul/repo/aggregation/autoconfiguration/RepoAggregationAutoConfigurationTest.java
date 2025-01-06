package io.trishul.repo.aggregation.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import io.trishul.repo.aggregation.repo.AggregationRepository;
import io.trishul.repo.jpa.query.resolver.QueryResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RepoAggregationAutoConfigurationTest {
  private RepoAggregationAutoConfiguration config;

  @BeforeEach
  public void init() {
    config = new RepoAggregationAutoConfiguration();
  }

  @Test
  public void testAggrRepo_ReturnsInstanceOfAggregationRepository() {
    QueryResolver mResolver = mock(QueryResolver.class);

    AggregationRepository aggrRepo = config.aggregationRepository(mResolver);

    assertSame(AggregationRepository.class, aggrRepo.getClass());
  }
}
