package sh.trishul.repo.filter;

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

class ArchiveFilterAspectTest {
  private EntityManager mockEm;
  private ProceedingJoinPoint mockJoinPoint;
  private ArchiveFilterAspect aspect;

  @BeforeEach
  void setUp() {
    mockEm = mock(EntityManager.class);
    mockJoinPoint = mock(ProceedingJoinPoint.class);
    aspect = new ArchiveFilterAspect(mockEm);
  }

  @Test
  void testApplyArchiveFilter_EnablesFilterAndProceeds() throws Throwable {
    Session mockSession = mock(Session.class);
    Filter mockFilter = mock(Filter.class);

    when(mockEm.unwrap(Session.class)).thenReturn(mockSession);
    when(mockSession.enableFilter(ArchiveFilter.NAME)).thenReturn(mockFilter);
    when(mockJoinPoint.proceed()).thenReturn("result");

    Object result = aspect.applyArchiveFilter(mockJoinPoint);

    assertEquals("result", result);
    verify(mockSession).enableFilter(ArchiveFilter.NAME);
    verify(mockFilter).setParameter(ArchiveFilter.PARAM_IS_ARCHIVED, false);
    verify(mockFilter).validate();
    verify(mockJoinPoint).proceed();
  }

  @Test
  void testApplyArchiveFilter_WhenEnablerThrows_StillProceeds() throws Throwable {
    when(mockEm.unwrap(Session.class)).thenThrow(new IllegalStateException("no session"));
    when(mockJoinPoint.proceed()).thenReturn("result");

    Object result = aspect.applyArchiveFilter(mockJoinPoint);

    assertEquals("result", result);
    verify(mockJoinPoint).proceed();
  }
}
