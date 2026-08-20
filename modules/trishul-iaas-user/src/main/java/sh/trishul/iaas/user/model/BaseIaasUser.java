package sh.trishul.iaas.user.model;

public interface BaseIaasUser<T extends BaseIaasUser<T>> {
  String ATTR_EMAIL = "email";
  String ATTR_PHONE_NUMBER = "phoneNumber";
  String ATTR_USER_NAME = "userName";

  String getEmail();

  T setEmail(String email);

  String getPhoneNumber();

  T setPhoneNumber(String phoneNumber);

  String getUserName();

  T setUserName(String userName);
}
