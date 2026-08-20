package sh.trishul.user.salutation.model;

public interface BaseUserSalutation<T extends BaseUserSalutation<T>> {
  String ATTR_TITLE = "title";

  String getTitle();

  T setTitle(String title);
}
