package sh.trishul.repo.filter;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class ArchiveFilterHandlerInterceptor implements HandlerInterceptor {
  private final EntityManager entityManager;

  public ArchiveFilterHandlerInterceptor(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    ArchiveFilterEnabler.enableFilter(entityManager);
    return true;
  }
}
