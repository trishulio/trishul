package io.trishul.user.model;

import java.net.URI;
import java.util.List;
import io.trishul.user.role.model.UserRole;
import io.trishul.user.salutation.model.UserSalutationAccessor;
import io.trishul.user.status.UserStatusAccessor;

public interface BaseUser<T extends BaseUser<T>>
    extends UserStatusAccessor<T>, UserSalutationAccessor<T> {
  final String ATTR_DISPLAY_NAME = "displayName";
  final String ATTR_FIRST_NAME = "firstName";
  final String ATTR_LAST_NAME = "lastName";
  final String ATTR_EMAIL = "email";
  final String ATTR_IMAGE_SRC = "imageSrc";
  final String ATTR_PHONE_NUMBER = "phoneNumber";
  final String ATTR_USER_NAME = "userName";
  final String ATTR_ROLES = "roles";

  String getDisplayName();

  T setDisplayName(String displayName);

  String getFirstName();

  T setFirstName(String firstName);

  String getLastName();

  T setLastName(String lastName);

  String getEmail();

  T setEmail(String email);

  URI getImageSrc();

  T setImageSrc(URI imageSrc);

  String getPhoneNumber();

  T setPhoneNumber(String phoneNumber);

  String getUserName();

  T setUserName(String userName);

  List<UserRole> getRoles();

  T setRoles(List<UserRole> roles);
}
