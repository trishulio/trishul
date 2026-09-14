package sh.trishul.user.service.filter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

class OwnerFilterWebMvcConfigurerTest {

  @Test
  void testAddInterceptors_RegistersInterceptor() {
    OwnerFilterHandlerInterceptor mockInterceptor = mock(OwnerFilterHandlerInterceptor.class);
    InterceptorRegistry mockRegistry = mock(InterceptorRegistry.class);

    OwnerFilterWebMvcConfigurer configurer = new OwnerFilterWebMvcConfigurer(mockInterceptor);
    configurer.addInterceptors(mockRegistry);

    verify(mockRegistry).addInterceptor(mockInterceptor);
  }
}
