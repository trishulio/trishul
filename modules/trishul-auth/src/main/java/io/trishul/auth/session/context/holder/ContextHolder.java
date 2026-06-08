package io.trishul.auth.session.context.holder;

import io.trishul.auth.session.context.PrincipalContext;
import java.util.UUID;

public interface ContextHolder {
  PrincipalContext getPrincipalContext();

  UUID getSessionTenantId();

  void clear();
}
