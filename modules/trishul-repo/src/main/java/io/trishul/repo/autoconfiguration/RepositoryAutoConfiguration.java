package io.trishul.repo.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.trishul.repo.jpa.query.resolver.QueryResolver;
import io.trishul.repo.jpa.repository.service.TransactionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Configuration
public class RepositoryAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(QueryResolver.class)
  @PersistenceContext
  public QueryResolver queryResolver(EntityManager em) {
    return new QueryResolver(em);
  }

  @Bean
  @ConditionalOnMissingBean(TransactionService.class)
  public TransactionService transactionService() {
    return new TransactionService();
  }
}
