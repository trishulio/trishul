package sh.trishul.user.service.filter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.auth.session.context.PrincipalContext;
import sh.trishul.auth.session.context.holder.ContextHolder;
import sh.trishul.user.filter.OwnerFilter;

class OwnerFilterHandlerInterceptorTest {
  private EntityManager mockEm;
  private ContextHolder mockContextHolder;
  private OwnerFilterHandlerInterceptor interceptor;

  @BeforeEach
  void setUp() {
    mockEm = mock(EntityManager.class);
    mockContextHolder = mock(ContextHolder.class);
    interceptor = new OwnerFilterHandlerInterceptor(mockEm, mockContextHolder);
  }

  @Test
  void testPreHandle_WhenUsernamePresent_EnablesFilter() {
    PrincipalContext mockPrincipal = mock(PrincipalContext.class);
    Session mockSession = mock(Session.class);
    Filter mockFilter = mock(Filter.class);

    when(mockPrincipal.getUsername()).thenReturn("testuser");
    when(mockContextHolder.getPrincipalContext()).thenReturn(mockPrincipal);
    when(mockEm.unwrap(Session.class)).thenReturn(mockSession);
    when(mockSession.enableFilter(OwnerFilter.NAME)).thenReturn(mockFilter);

    boolean result = interceptor.preHandle(mock(HttpServletRequest.class),
        mock(HttpServletResponse.class), new Object());

    assertTrue(result);
    verify(mockSession).enableFilter(OwnerFilter.NAME);
    verify(mockFilter).setParameter(OwnerFilter.PARAM_OWNER_USERNAME, "testuser");
    verify(mockFilter).validate();
  }

  @Test
  void testPreHandle_WhenPrincipalContextNull_ReturnsTrueWithoutEnablingFilter() {
    when(mockContextHolder.getPrincipalContext()).thenReturn(null);

    boolean result = interceptor.preHandle(mock(HttpServletRequest.class),
        mock(HttpServletResponse.class), new Object());

    assertTrue(result);
    verifyNoInteractions(mockEm);
  }

  @Test
  void testPreHandle_WhenUsernameBlank_ReturnsTrueWithoutEnablingFilter() {
    PrincipalContext mockPrincipal = mock(PrincipalContext.class);

    when(mockPrincipal.getUsername()).thenReturn("   ");
    when(mockContextHolder.getPrincipalContext()).thenReturn(mockPrincipal);

    boolean result = interceptor.preHandle(mock(HttpServletRequest.class),
        mock(HttpServletResponse.class), new Object());

    assertTrue(result);
    verifyNoInteractions(mockEm);
  }

  @Test
  void testPreHandle_WhenUnwrapThrows_ReturnsTrueGracefully() {
    PrincipalContext mockPrincipal = mock(PrincipalContext.class);

    when(mockPrincipal.getUsername()).thenReturn("testuser");
    when(mockContextHolder.getPrincipalContext()).thenReturn(mockPrincipal);
    when(mockEm.unwrap(Session.class)).thenThrow(new IllegalStateException("no session"));

    boolean result = interceptor.preHandle(mock(HttpServletRequest.class),
        mock(HttpServletResponse.class), new Object());

    assertTrue(result);
  }
}
