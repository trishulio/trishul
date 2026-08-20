package sh.trishul.user.role.model;

public interface BaseUserRole<T extends BaseUserRole<T>> {
  String ATTR_NAME = "name";

  String getName();

  T setName(String name);
}
