package io.trishul.iaas.user.model;

public interface BaseIaasUser<T extends BaseIaasUser<T>> {
  final String ATTR_EMAIL = "email";
  final String ATTR_PHONE_NUMBER = "phoneNumber";
  final String ATTR_USER_NAME = "userName";

  String getEmail();

  T setEmail(String email);

  String getPhoneNumber();

  T setPhoneNumber(String phoneNumber);

  String getUserName();

  T setUserName(String userName);
}
