package io.trishul.repo.autoconfiguration;

import io.trishul.repo.jpa.query.resolver.QueryResolver;
import io.trishul.repo.jpa.repository.service.TransactionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {
  @Bean
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
