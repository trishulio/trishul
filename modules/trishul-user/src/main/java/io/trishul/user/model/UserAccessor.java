package io.trishul.user.model;

public interface UserAccessor {
    final String ATTR_USER = "user";

    User getUser();

    void setUser(User user);
}
