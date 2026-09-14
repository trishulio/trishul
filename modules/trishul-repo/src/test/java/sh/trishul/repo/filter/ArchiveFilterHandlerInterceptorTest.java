package sh.trishul.repo.filter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArchiveFilterHandlerInterceptorTest {
  private EntityManager mockEm;
  private ArchiveFilterHandlerInterceptor interceptor;

  @BeforeEach
  void setUp() {
    mockEm = mock(EntityManager.class);
    interceptor = new ArchiveFilterHandlerInterceptor(mockEm);
  }

  @Test
  void testPreHandle_EnablesFilterAndReturnsTrue() {
    Session mockSession = mock(Session.class);
    Filter mockFilter = mock(Filter.class);

    when(mockEm.unwrap(Session.class)).thenReturn(mockSession);
    when(mockSession.enableFilter(ArchiveFilter.NAME)).thenReturn(mockFilter);

    boolean result = interceptor.preHandle(mock(HttpServletRequest.class),
        mock(HttpServletResponse.class), new Object());

    assertTrue(result);
    verify(mockSession).enableFilter(ArchiveFilter.NAME);
    verify(mockFilter).setParameter(ArchiveFilter.PARAM_IS_ARCHIVED, false);
    verify(mockFilter).validate();
  }

  @Test
  void testPreHandle_WhenEnablerThrows_ReturnsTrue() {
    when(mockEm.unwrap(Session.class)).thenThrow(new IllegalStateException("no session"));

    boolean result = interceptor.preHandle(mock(HttpServletRequest.class),
        mock(HttpServletResponse.class), new Object());

    assertTrue(result);
  }
}
