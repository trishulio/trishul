package sh.trishul.object.store.model;

public interface BaseIaasObjectStore<T extends BaseIaasObjectStore<T>> {
  String ATTR_NAME = "name";

  String getName();

  T setName(String name);
}
