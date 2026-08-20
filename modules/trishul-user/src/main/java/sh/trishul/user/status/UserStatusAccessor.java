package sh.trishul.user.status;

public interface UserStatusAccessor<T extends UserStatusAccessor<T>> {
  String ATTR_STATUS = "status";

  UserStatus getStatus();

  T setStatus(UserStatus status);
}
