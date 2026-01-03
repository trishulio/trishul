package io.trishul.auth.session.context.holder;

import java.util.UUID;
import io.trishul.auth.session.context.PrincipalContext;

public interface ContextHolder {
  PrincipalContext getPrincipalContext();

  UUID getSessionTenantId();

  void clear();
}
