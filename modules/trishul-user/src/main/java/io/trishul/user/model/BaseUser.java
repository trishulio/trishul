package io.trishul.user.model;

import java.net.URI;
import java.util.List;

import io.trishul.user.role.model.UserRole;
import io.trishul.user.salutation.model.UserSalutationAccessor;
import io.trishul.user.status.UserStatusAccessor;

public interface BaseUser extends UserStatusAccessor, UserSalutationAccessor {
    final String ATTR_DISPLAY_NAME = "displayName";
    final String ATTR_FIRST_NAME = "firstName";
    final String ATTR_LAST_NAME = "lastName";
    final String ATTR_EMAIL = "email";
    final String ATTR_IMAGE_SRC = "imageSrc";
    final String ATTR_PHONE_NUMBER = "phoneNumber";
    final String ATTR_USER_NAME = "userName";
    final String ATTR_ROLES = "roles";

    String getDisplayName();

    void setDisplayName(String displayName);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getEmail();

    void setEmail(String email);

    URI getImageSrc();

    void setImageSrc(URI imageSrc);

    String getPhoneNumber();

    void setPhoneNumber(String phoneNumber);

    String getUserName();

    void setUserName(String userName);

    List<UserRole> getRoles();

    void setRoles(List<UserRole> roles);
}
