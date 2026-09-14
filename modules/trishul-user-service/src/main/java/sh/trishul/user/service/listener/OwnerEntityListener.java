package sh.trishul.user.service.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import sh.trishul.auth.session.context.holder.ContextHolder;
import sh.trishul.user.model.OwnedEntity;

public class OwnerEntityListener {
  private static ContextHolder staticContextHolder;
  private final ContextHolder contextHolder;

  public OwnerEntityListener() {
    this(staticContextHolder);
  }

  public OwnerEntityListener(ContextHolder contextHolder) {
    this.contextHolder = contextHolder;
  }

  public static void setContextHolder(ContextHolder contextHolder) {
    staticContextHolder = contextHolder;
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
    ContextHolder holder = this.contextHolder != null ? this.contextHolder : staticContextHolder;
    if (entity instanceof OwnedEntity owned
        && (owned.getOwnerUsername() == null || owned.getOwnerUsername().isBlank())
        && holder != null && holder.getPrincipalContext() != null) {
      String username = holder.getPrincipalContext().getUsername();
      if (username != null && !username.isBlank()) {
        owned.setOwnerUsername(username);
      }
    }
  }
}
