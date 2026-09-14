package sh.trishul.user.service.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import sh.trishul.auth.session.context.holder.ContextHolder;
import sh.trishul.user.model.OwnedEntity;

public class OwnerEntityListener {
  private final ContextHolder contextHolder;

  public OwnerEntityListener(ContextHolder contextHolder) {
    this.contextHolder = contextHolder;
  }

  @PrePersist
  public void prePersist(Object entity) {
    populateOwnerUsername(entity);
  }

  @PreUpdate
  public void preUpdate(Object entity) {
    populateOwnerUsername(entity);
  }

  private void populateOwnerUsername(Object entity) {
    if (entity instanceof OwnedEntity owned) {
      if (owned.getOwnerUsername() == null || owned.getOwnerUsername().isBlank()) {
        if (contextHolder != null && contextHolder.getPrincipalContext() != null) {
          String username = contextHolder.getPrincipalContext().getUsername();
          if (username != null && !username.isBlank()) {
            owned.setOwnerUsername(username);
          }
        }
      }
    }
  }
}
