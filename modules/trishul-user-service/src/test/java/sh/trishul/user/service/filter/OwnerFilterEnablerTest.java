package sh.trishul.user.service.filter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityManager;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import sh.trishul.auth.session.context.PrincipalContext;
import sh.trishul.auth.session.context.holder.ContextHolder;
import sh.trishul.user.filter.OwnerFilter;

class OwnerFilterEnablerTest {

  @Test
  void testEnableFilter_WhenUsernamePresent_EnablesFilterAndReturnsTrue() {
    EntityManager mockEm = mock(EntityManager.class);
    ContextHolder mockContextHolder = mock(ContextHolder.class);
    PrincipalContext mockPrincipal = mock(PrincipalContext.class);
    Session mockSession = mock(Session.class);
    Filter mockFilter = mock(Filter.class);

    when(mockPrincipal.getUsername()).thenReturn("testuser");
    when(mockContextHolder.getPrincipalContext()).thenReturn(mockPrincipal);
    when(mockEm.unwrap(Session.class)).thenReturn(mockSession);
    when(mockSession.enableFilter(OwnerFilter.NAME)).thenReturn(mockFilter);

    boolean result = OwnerFilterEnabler.enableFilter(mockEm, mockContextHolder);

    assertTrue(result);
    verify(mockSession).enableFilter(OwnerFilter.NAME);
    verify(mockFilter).setParameter(OwnerFilter.PARAM_OWNER_USERNAME, "testuser");
    verify(mockFilter).validate();
  }

  @Test
  void testEnableFilter_WhenPrincipalContextNull_ReturnsFalse() {
    EntityManager mockEm = mock(EntityManager.class);
    ContextHolder mockContextHolder = mock(ContextHolder.class);

    when(mockContextHolder.getPrincipalContext()).thenReturn(null);

    boolean result = OwnerFilterEnabler.enableFilter(mockEm, mockContextHolder);

    assertFalse(result);
    verifyNoInteractions(mockEm);
  }

  @Test
  void testEnableFilter_WhenUsernameNull_ReturnsFalse() {
    EntityManager mockEm = mock(EntityManager.class);
    ContextHolder mockContextHolder = mock(ContextHolder.class);
    PrincipalContext mockPrincipal = mock(PrincipalContext.class);

    when(mockPrincipal.getUsername()).thenReturn(null);
    when(mockContextHolder.getPrincipalContext()).thenReturn(mockPrincipal);

    boolean result = OwnerFilterEnabler.enableFilter(mockEm, mockContextHolder);

    assertFalse(result);
    verifyNoInteractions(mockEm);
  }

  @Test
  void testEnableFilter_WhenUsernameBlank_ReturnsFalse() {
    EntityManager mockEm = mock(EntityManager.class);
    ContextHolder mockContextHolder = mock(ContextHolder.class);
    PrincipalContext mockPrincipal = mock(PrincipalContext.class);

    when(mockPrincipal.getUsername()).thenReturn("   ");
    when(mockContextHolder.getPrincipalContext()).thenReturn(mockPrincipal);

    boolean result = OwnerFilterEnabler.enableFilter(mockEm, mockContextHolder);

    assertFalse(result);
    verifyNoInteractions(mockEm);
  }

  @Test
  void testEnableFilter_WhenSessionNull_ReturnsFalse() {
    EntityManager mockEm = mock(EntityManager.class);
    ContextHolder mockContextHolder = mock(ContextHolder.class);
    PrincipalContext mockPrincipal = mock(PrincipalContext.class);

    when(mockPrincipal.getUsername()).thenReturn("testuser");
    when(mockContextHolder.getPrincipalContext()).thenReturn(mockPrincipal);
    when(mockEm.unwrap(Session.class)).thenReturn(null);

    boolean result = OwnerFilterEnabler.enableFilter(mockEm, mockContextHolder);

    assertFalse(result);
  }

  @Test
  void testEnableFilter_WhenUnwrapThrows_ReturnsFalse() {
    EntityManager mockEm = mock(EntityManager.class);
    ContextHolder mockContextHolder = mock(ContextHolder.class);
    PrincipalContext mockPrincipal = mock(PrincipalContext.class);

    when(mockPrincipal.getUsername()).thenReturn("testuser");
    when(mockContextHolder.getPrincipalContext()).thenReturn(mockPrincipal);
    when(mockEm.unwrap(Session.class)).thenThrow(new IllegalStateException("no session"));

    boolean result = OwnerFilterEnabler.enableFilter(mockEm, mockContextHolder);

    assertFalse(result);
  }
}
