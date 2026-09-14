package sh.trishul.repo.filter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

class ArchiveFilterWebMvcConfigurerTest {

  @Test
  void testAddInterceptors_RegistersArchiveFilterHandlerInterceptor() {
    ArchiveFilterHandlerInterceptor mockInterceptor = mock(ArchiveFilterHandlerInterceptor.class);
    InterceptorRegistry mockRegistry = mock(InterceptorRegistry.class);

    ArchiveFilterWebMvcConfigurer configurer = new ArchiveFilterWebMvcConfigurer(mockInterceptor);
    configurer.addInterceptors(mockRegistry);

    verify(mockRegistry).addInterceptor(mockInterceptor);
  }
}
