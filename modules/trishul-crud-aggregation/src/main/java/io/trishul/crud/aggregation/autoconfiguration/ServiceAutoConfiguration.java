package io.trishul.crud.aggregation.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.crud.aggregation.repo.AggregationRepository;
import io.trishul.crud.aggregation.service.AggregationService;
import io.trishul.repo.jpa.query.resolver.QueryResolver;

@Configuration
public class ServiceAutoConfiguration {

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
