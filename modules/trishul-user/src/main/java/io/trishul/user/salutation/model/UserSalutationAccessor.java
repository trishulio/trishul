package io.trishul.user.salutation.model;

public interface UserSalutationAccessor {
    final String ATTR_SALUTATION = "salutation";

    UserSalutation getSalutation();

    void setSalutation(UserSalutation salutation);
}
