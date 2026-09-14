package sh.trishul.user.service.filter;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import sh.trishul.auth.session.context.holder.ContextHolder;

public class OwnerFilterHandlerInterceptor implements HandlerInterceptor {
  private final EntityManager entityManager;
  private final ContextHolder contextHolder;

  public OwnerFilterHandlerInterceptor(EntityManager entityManager, ContextHolder contextHolder) {
    this.entityManager = entityManager;
    this.contextHolder = contextHolder;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    OwnerFilterEnabler.enableFilter(entityManager, contextHolder);
    return true;
  }
}
