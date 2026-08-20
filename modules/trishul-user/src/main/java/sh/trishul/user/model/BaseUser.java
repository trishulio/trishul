package sh.trishul.user.model;

import java.net.URI;
import java.util.List;
import sh.trishul.user.role.model.UserRole;
import sh.trishul.user.salutation.model.UserSalutationAccessor;
import sh.trishul.user.status.UserStatusAccessor;

public interface BaseUser<T extends BaseUser<T>>
    extends UserStatusAccessor<T>, UserSalutationAccessor<T> {
  String ATTR_DISPLAY_NAME = "displayName";
  String ATTR_FIRST_NAME = "firstName";
  String ATTR_LAST_NAME = "lastName";
  String ATTR_EMAIL = "email";
  String ATTR_IMAGE_SRC = "imageSrc";
  String ATTR_PHONE_NUMBER = "phoneNumber";
  String ATTR_USER_NAME = "userName";
  String ATTR_IAAS_USERNAME = "iaasUsername";
  String ATTR_ROLES = "roles";

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

  String getIaasUsername();

  T setIaasUsername(String iaasUsername);

  List<UserRole> getRoles();

  T setRoles(List<UserRole> roles);
}
