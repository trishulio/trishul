package sh.trishul.repo.aggregation.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.repo.aggregation.repo.AggregationRepository;
import sh.trishul.repo.aggregation.service.AggregationService;
import sh.trishul.repo.jpa.query.resolver.QueryResolver;

@Configuration
public class RepoAggregationAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(AggregationRepository.class)
  public AggregationRepository aggregationRepository(QueryResolver queryResolver) {
    return new AggregationRepository(queryResolver);
  }

  @Bean
  @ConditionalOnMissingBean(AggregationService.class)
  public AggregationService aggregationService(AggregationRepository aggregationRepository) {
    return new AggregationService(aggregationRepository);
  }
}
