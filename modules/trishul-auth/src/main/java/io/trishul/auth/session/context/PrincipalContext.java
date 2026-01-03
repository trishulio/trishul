package io.trishul.auth.session.context;

import java.util.List;
import java.util.UUID;

public interface PrincipalContext {
  /**
   * @return List of all tenant IDs the user belongs to
   */
  List<UUID> getTenantIds();

  String getUsername();

  List<String> getRoles();
}
