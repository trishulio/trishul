package sh.trishul.repo.filter;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class ArchiveFilterWebMvcConfigurer implements WebMvcConfigurer {
  private final ArchiveFilterHandlerInterceptor interceptor;

  public ArchiveFilterWebMvcConfigurer(ArchiveFilterHandlerInterceptor interceptor) {
    this.interceptor = interceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(interceptor);
  }
}
