package sh.trishul.user.status;

public interface BaseUserStatus<T extends BaseUserStatus<T>> {
  String ATTR_NAME = "name";

  String getName();

  T setName(String name);
}
