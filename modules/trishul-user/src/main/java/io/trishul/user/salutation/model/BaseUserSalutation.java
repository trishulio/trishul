package io.trishul.user.salutation.model;

public interface BaseUserSalutation<T extends BaseUserSalutation<T>> {
  final String ATTR_TITLE = "title";

  String getTitle();

  T setTitle(String title);
}
