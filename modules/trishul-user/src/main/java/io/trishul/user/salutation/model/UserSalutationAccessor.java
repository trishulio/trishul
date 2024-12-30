package io.trishul.user.salutation.model;

public interface UserSalutationAccessor<T extends UserSalutationAccessor<T>> {
  final String ATTR_SALUTATION = "salutation";

  UserSalutation getSalutation();

  T setSalutation(UserSalutation salutation);
}
