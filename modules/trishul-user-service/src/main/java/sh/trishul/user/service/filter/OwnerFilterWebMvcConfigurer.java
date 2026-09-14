package sh.trishul.user.service.filter;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class OwnerFilterWebMvcConfigurer implements WebMvcConfigurer {
  private final OwnerFilterHandlerInterceptor interceptor;

  public OwnerFilterWebMvcConfigurer(OwnerFilterHandlerInterceptor interceptor) {
    this.interceptor = interceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(interceptor);
  }
}
