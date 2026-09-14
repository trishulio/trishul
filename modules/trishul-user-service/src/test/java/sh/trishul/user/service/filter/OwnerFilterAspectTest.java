package sh.trishul.user.service.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.auth.session.context.PrincipalContext;
import sh.trishul.auth.session.context.holder.ContextHolder;
import sh.trishul.user.filter.OwnerFilter;

class OwnerFilterAspectTest {
  private EntityManager mockEm;
  private ContextHolder mockContextHolder;
  private ProceedingJoinPoint mockJoinPoint;
  private OwnerFilterAspect aspect;

  @BeforeEach
  void setUp() {
    mockEm = mock(EntityManager.class);
    mockContextHolder = mock(ContextHolder.class);
    mockJoinPoint = mock(ProceedingJoinPoint.class);
    aspect = new OwnerFilterAspect(mockEm, mockContextHolder);
  }

  @Test
  void testApplyOwnerFilter_WhenUsernamePresent_EnablesFilter() throws Throwable {
    PrincipalContext mockPrincipal = mock(PrincipalContext.class);
    Session mockSession = mock(Session.class);
    Filter mockFilter = mock(Filter.class);

    when(mockPrincipal.getUsername()).thenReturn("testuser");
    when(mockContextHolder.getPrincipalContext()).thenReturn(mockPrincipal);
    when(mockEm.unwrap(Session.class)).thenReturn(mockSession);
    when(mockSession.enableFilter(OwnerFilter.NAME)).thenReturn(mockFilter);
    when(mockJoinPoint.proceed()).thenReturn("result");

    Object result = aspect.applyOwnerFilter(mockJoinPoint);

    assertEquals("result", result);
    verify(mockSession).enableFilter(OwnerFilter.NAME);
    verify(mockFilter).setParameter(OwnerFilter.PARAM_OWNER_USERNAME, "testuser");
    verify(mockFilter).validate();
    verify(mockJoinPoint).proceed();
  }

  @Test
  void testApplyOwnerFilter_WhenPrincipalNull_ProceedsWithoutFilter() throws Throwable {
    when(mockContextHolder.getPrincipalContext()).thenReturn(null);
    when(mockJoinPoint.proceed()).thenReturn("result");

    Object result = aspect.applyOwnerFilter(mockJoinPoint);

    assertEquals("result", result);
    verify(mockJoinPoint).proceed();
  }

  @Test
  void testApplyOwnerFilter_WhenUsernameBlank_ProceedsWithoutFilter() throws Throwable {
    PrincipalContext mockPrincipal = mock(PrincipalContext.class);

    when(mockPrincipal.getUsername()).thenReturn("   ");
    when(mockContextHolder.getPrincipalContext()).thenReturn(mockPrincipal);
    when(mockJoinPoint.proceed()).thenReturn("result");

    Object result = aspect.applyOwnerFilter(mockJoinPoint);

    assertEquals("result", result);
    verify(mockJoinPoint).proceed();
  }

  @Test
  void testApplyOwnerFilter_WhenEnablerThrows_StillProceeds() throws Throwable {
    PrincipalContext mockPrincipal = mock(PrincipalContext.class);

    when(mockPrincipal.getUsername()).thenReturn("testuser");
    when(mockContextHolder.getPrincipalContext()).thenReturn(mockPrincipal);
    when(mockEm.unwrap(Session.class)).thenThrow(new IllegalStateException("no session"));
    when(mockJoinPoint.proceed()).thenReturn("result");

    Object result = aspect.applyOwnerFilter(mockJoinPoint);

    assertEquals("result", result);
    verify(mockJoinPoint).proceed();
  }
}
