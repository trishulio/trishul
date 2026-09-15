package sh.trishul.repo.autoconfiguration;

import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.trishul.repo.filter.ArchiveFilterAspect;
import sh.trishul.repo.filter.ArchiveFilterHandlerInterceptor;
import sh.trishul.repo.filter.ArchiveFilterWebMvcConfigurer;
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

  @Bean
  @ConditionalOnMissingBean(ArchiveFilterHandlerInterceptor.class)
  public ArchiveFilterHandlerInterceptor archiveFilterHandlerInterceptor(
      EntityManager entityManager) {
    return new ArchiveFilterHandlerInterceptor(entityManager);
  }

  @Bean
  @ConditionalOnMissingBean(ArchiveFilterAspect.class)
  public ArchiveFilterAspect archiveFilterAspect(EntityManager entityManager) {
    return new ArchiveFilterAspect(entityManager);
  }

  @Bean
  @ConditionalOnMissingBean(ArchiveFilterWebMvcConfigurer.class)
  public ArchiveFilterWebMvcConfigurer archiveFilterWebMvcConfigurer(
      ArchiveFilterHandlerInterceptor interceptor) {
    return new ArchiveFilterWebMvcConfigurer(interceptor);
  }
}
