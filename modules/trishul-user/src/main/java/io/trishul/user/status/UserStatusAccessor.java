package io.trishul.user.status;

public interface UserStatusAccessor {
    final String ATTR_STATUS = "status";

    UserStatus getStatus();

    void setStatus(UserStatus status);
}
