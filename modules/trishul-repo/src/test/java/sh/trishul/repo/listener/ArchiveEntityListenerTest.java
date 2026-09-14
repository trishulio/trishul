package sh.trishul.repo.listener;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.base.types.base.pojo.Archiveable;

class ArchiveEntityListenerTest {
  private ArchiveEntityListener listener;

  @BeforeEach
  void setUp() {
    listener = new ArchiveEntityListener();
  }

  @Test
  void testPrePersist_WhenArchiveableWithNullArchived_SetsArchivedToFalse() {
    TestArchiveableEntity entity = new TestArchiveableEntity();
    assertNull(entity.isArchived());

    listener.prePersist(entity);

    assertFalse(entity.isArchived());
  }

  @Test
  void testPrePersist_WhenArchiveableWithTrueArchived_LeavesArchivedTrue() {
    TestArchiveableEntity entity = new TestArchiveableEntity();
    entity.setArchived(true);

    listener.prePersist(entity);

    assertTrue(entity.isArchived());
  }

  @Test
  void testPrePersist_WhenArchiveableWithFalseArchived_LeavesArchivedFalse() {
    TestArchiveableEntity entity = new TestArchiveableEntity();
    entity.setArchived(false);

    listener.prePersist(entity);

    assertFalse(entity.isArchived());
  }

  @Test
  void testPrePersist_WhenNonArchiveableEntity_DoesNothing() {
    Object entity = new Object();

    listener.prePersist(entity);
  }

  @Test
  void testPrePersist_WhenNullEntity_DoesNothing() {
    listener.prePersist(null);
  }

  @Test
  void testPreUpdate_WhenArchiveableWithNullArchived_SetsArchivedToFalse() {
    TestArchiveableEntity entity = new TestArchiveableEntity();
    assertNull(entity.isArchived());

    listener.preUpdate(entity);

    assertFalse(entity.isArchived());
  }

  @Test
  void testPreUpdate_WhenArchiveableWithTrueArchived_LeavesArchivedTrue() {
    TestArchiveableEntity entity = new TestArchiveableEntity();
    entity.setArchived(true);

    listener.preUpdate(entity);

    assertTrue(entity.isArchived());
  }

  @Test
  void testPreUpdate_WhenArchiveableWithFalseArchived_LeavesArchivedFalse() {
    TestArchiveableEntity entity = new TestArchiveableEntity();
    entity.setArchived(false);

    listener.preUpdate(entity);

    assertFalse(entity.isArchived());
  }

  @Test
  void testPreUpdate_WhenNonArchiveableEntity_DoesNothing() {
    Object entity = new Object();

    listener.preUpdate(entity);
  }

  @Test
  void testPreUpdate_WhenNullEntity_DoesNothing() {
    listener.preUpdate(null);
  }

  private static final class TestArchiveableEntity implements Archiveable {
    private Boolean archived;

    @Override
    public Boolean isArchived() {
      return archived;
    }

    @Override
    public void setArchived(Boolean archived) {
      this.archived = archived;
    }
  }
}
