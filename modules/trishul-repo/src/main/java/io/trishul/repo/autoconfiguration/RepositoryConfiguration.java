package io.trishul.repo.autoconfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.trishul.repo.jpa.query.resolver.QueryResolver;
import io.trishul.repo.jpa.repository.aggregation.AggregationRepository;

@Configuration
public class RepositoryConfiguration {
    @Bean
    @PersistenceContext
    public QueryResolver queryResolver(EntityManager em) {
        return new QueryResolver(em);
    }

    @Bean
    public AggregationRepository aggrRepo(QueryResolver queryResolver) {
        return new AggregationRepository(queryResolver);
    }
}
