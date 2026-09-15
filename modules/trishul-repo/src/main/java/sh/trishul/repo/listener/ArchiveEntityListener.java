package sh.trishul.repo.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import sh.trishul.base.types.base.pojo.Archiveable;

public class ArchiveEntityListener {

  @PrePersist
  public void prePersist(Object entity) {
    populateArchived(entity);
  }

  @PreUpdate
  public void preUpdate(Object entity) {
    populateArchived(entity);
  }

  private void populateArchived(Object entity) {
    if (entity instanceof Archiveable archiveable && archiveable.isArchived() == null) {
      archiveable.setArchived(false);
    }
  }
}
