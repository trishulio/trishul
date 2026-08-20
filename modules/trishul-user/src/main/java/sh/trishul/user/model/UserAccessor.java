package sh.trishul.user.model;

public interface UserAccessor<T extends UserAccessor<T>> {
  String ATTR_USER = "user";

  User getUser();

  T setUser(User user);
}
