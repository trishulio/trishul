package sh.trishul.base.types.base.pojo;

public interface Identified<T> {
  static final String ATTR_ID = "id";

  T getId();
}
