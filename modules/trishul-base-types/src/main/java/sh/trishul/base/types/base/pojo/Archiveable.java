package sh.trishul.base.types.base.pojo;

public interface Archiveable {
  String ATTR_ARCHIVED = "archived";

  Boolean isArchived();

  void setArchived(Boolean archived);

  default Boolean getArchived() {
    return isArchived();
  }
}
