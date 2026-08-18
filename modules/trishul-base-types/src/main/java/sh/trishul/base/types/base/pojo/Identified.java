package sh.trishul.base.types.base.pojo;

public interface Identified<T> {
  String ATTR_ID = "id";

  T getId();
}
