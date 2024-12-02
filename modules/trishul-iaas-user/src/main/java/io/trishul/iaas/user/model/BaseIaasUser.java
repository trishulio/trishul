package io.trishul.iaas.user.model;

public interface BaseIaasUser {
    final String ATTR_EMAIL = "email";
    final String ATTR_PHONE_NUMBER_ = "phoneNumber";
    final String ATTR_USER_NAME = "userName";

    String getEmail();

    void setEmail(String email);

    String getPhoneNumber();

    void setPhoneNumber(String phoneNumber);

    String getUserName();

    void setUserName(String userName);
}
