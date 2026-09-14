package sh.trishul.user.service.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import sh.trishul.auth.session.context.holder.ContextHolder;
import sh.trishul.user.model.OwnedEntity;

public class OwnerEntityListener {
  private static ContextHolder staticContextHolder;
  private final ContextHolder contextHolder;
  private final boolean useStatic;

  public OwnerEntityListener() {
    this.contextHolder = null;
    this.useStatic = true;
  }

  public OwnerEntityListener(ContextHolder contextHolder) {
    this.contextHolder = contextHolder;
    this.useStatic = false;
  }

  public static void setContextHolder(ContextHolder holder) {
    staticContextHolder = holder;
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
    ContextHolder effectiveHolder = this.useStatic ? staticContextHolder : this.contextHolder;
    if (entity instanceof OwnedEntity owned
        && (owned.getOwnerUsername() == null || owned.getOwnerUsername().isBlank())
        && effectiveHolder != null && effectiveHolder.getPrincipalContext() != null) {
      String username = effectiveHolder.getPrincipalContext().getUsername();
      if (username != null && !username.isBlank()) {
        owned.setOwnerUsername(username);
      }
    }
  }
}
