package sh.trishul.user.model;

public interface OwnedEntity {
  String ATTR_OWNER_USERNAME = "ownerUsername";

  String getOwnerUsername();

  void setOwnerUsername(String ownerUsername);
}
