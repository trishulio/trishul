package sh.trishul.auth.session.context.holder;

import java.util.UUID;
import sh.trishul.auth.session.context.PrincipalContext;

public interface ContextHolder {
  PrincipalContext getPrincipalContext();

  UUID getSessionTenantId();

  String getRequestId();

  void clear();
}
