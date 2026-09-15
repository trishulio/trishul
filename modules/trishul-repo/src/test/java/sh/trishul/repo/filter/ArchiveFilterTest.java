package sh.trishul.repo.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Constructor;
import org.junit.jupiter.api.Test;

class ArchiveFilterTest {

  @Test
  void testConstants() {
    assertEquals("archiveFilter", ArchiveFilter.NAME);
    assertEquals("isArchived", ArchiveFilter.PARAM_IS_ARCHIVED);
    assertEquals("archived = :isArchived", ArchiveFilter.CONDITION);
  }

  @Test
  void testPrivateConstructor() throws Exception {
    Constructor<ArchiveFilter> constructor = ArchiveFilter.class.getDeclaredConstructor();
    constructor.setAccessible(true);
    ArchiveFilter instance = constructor.newInstance();
    assertNotNull(instance);
  }
}
