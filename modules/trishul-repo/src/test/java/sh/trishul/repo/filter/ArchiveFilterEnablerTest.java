package sh.trishul.repo.filter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityManager;
import java.lang.reflect.Constructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

class ArchiveFilterEnablerTest {

  @Test
  void testEnableFilter_WhenSessionPresent_EnablesFilterAndReturnsTrue() {
    EntityManager mockEm = mock(EntityManager.class);
    Session mockSession = mock(Session.class);
    Filter mockFilter = mock(Filter.class);

    when(mockEm.unwrap(Session.class)).thenReturn(mockSession);
    when(mockSession.enableFilter(ArchiveFilter.NAME)).thenReturn(mockFilter);

    boolean result = ArchiveFilterEnabler.enableFilter(mockEm);

    assertTrue(result);
    verify(mockSession).enableFilter(ArchiveFilter.NAME);
    verify(mockFilter).setParameter(ArchiveFilter.PARAM_IS_ARCHIVED, false);
    verify(mockFilter).validate();
  }

  @Test
  void testEnableFilter_WhenSessionNull_ReturnsFalse() {
    EntityManager mockEm = mock(EntityManager.class);
    when(mockEm.unwrap(Session.class)).thenReturn(null);

    boolean result = ArchiveFilterEnabler.enableFilter(mockEm);

    assertFalse(result);
  }

  @Test
  void testEnableFilter_WhenUnwrapThrows_ReturnsFalse() {
    EntityManager mockEm = mock(EntityManager.class);
    when(mockEm.unwrap(Session.class)).thenThrow(new IllegalStateException("no session"));

    boolean result = ArchiveFilterEnabler.enableFilter(mockEm);

    assertFalse(result);
  }

  @Test
  void testPrivateConstructor() throws Exception {
    Constructor<ArchiveFilterEnabler> constructor
        = ArchiveFilterEnabler.class.getDeclaredConstructor();
    constructor.setAccessible(true);
    ArchiveFilterEnabler instance = constructor.newInstance();
    assertNotNull(instance);
  }
}
