package sh.trishul.repo.autoconfiguration;

import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.repo.jpa.query.resolver.QueryResolver;
import sh.trishul.repo.jpa.repository.service.TransactionService;

@Configuration
public class RepositoryAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(QueryResolver.class)
  public QueryResolver queryResolver(EntityManager em) {
    return new QueryResolver(em);
  }

  @Bean
  @ConditionalOnMissingBean(TransactionService.class)
  public TransactionService transactionService() {
    return new TransactionService();
  }
}
