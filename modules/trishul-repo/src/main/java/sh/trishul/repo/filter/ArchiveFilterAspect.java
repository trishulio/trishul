package sh.trishul.repo.filter;

import jakarta.persistence.EntityManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ArchiveFilterAspect {
  private final EntityManager entityManager;

  public ArchiveFilterAspect(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Around("@within(org.springframework.stereotype.Service)"
      + " || @within(org.springframework.stereotype.Repository)"
      + " || @annotation(org.springframework.transaction.annotation.Transactional)")
  public Object applyArchiveFilter(ProceedingJoinPoint joinPoint) throws Throwable {
    ArchiveFilterEnabler.enableFilter(entityManager);
    return joinPoint.proceed();
  }
}
