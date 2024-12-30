package io.trishul.auth.session.context;

import java.util.List;
import java.util.UUID;

public interface PrincipalContext {
  UUID getGroupId();

  String getUsername();

  List<String> getRoles();
}
