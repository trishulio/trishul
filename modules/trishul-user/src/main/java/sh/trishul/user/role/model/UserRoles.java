package sh.trishul.user.role.model;

import java.util.Arrays;

public enum UserRoles {
  ADMIN(1L, "ADMIN"), OPERATOR(2L, "OPERATOR");

  private final Long id;
  private final String name;

  UserRoles(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public static UserRoles fromId(Long id) {
    if (id == null) {
      return null;
    }
    return Arrays.stream(values()).filter(role -> role.getId().equals(id)).findFirst().orElse(null);
  }

  public static UserRoles fromName(String name) {
    if (name == null) {
      return null;
    }
    return Arrays.stream(values()).filter(role -> role.getName().equalsIgnoreCase(name)).findFirst()
        .orElse(null);
  }
}
