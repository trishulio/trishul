package sh.trishul.user.service.filter;

import jakarta.persistence.EntityManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import sh.trishul.auth.session.context.holder.ContextHolder;

@Aspect
public class OwnerFilterAspect {
  private final EntityManager entityManager;
  private final ContextHolder contextHolder;

  public OwnerFilterAspect(EntityManager entityManager, ContextHolder contextHolder) {
    this.entityManager = entityManager;
    this.contextHolder = contextHolder;
  }

  @Around("@within(org.springframework.stereotype.Service)"
      + " || @within(org.springframework.stereotype.Repository)"
      + " || @annotation(org.springframework.transaction.annotation.Transactional)")
  public Object applyOwnerFilter(ProceedingJoinPoint joinPoint) throws Throwable {
    OwnerFilterEnabler.enableFilter(entityManager, contextHolder);
    return joinPoint.proceed();
  }
}
