package io.trishul.base.types.base.pojo;

import java.util.Set;

public interface CrudEntity<ID, T extends CrudEntity<ID, T>> extends UpdatableEntity<ID, T> {
  void outerJoin(Object other);

  void outerJoin(Object other, Set<String> include);

  void override(Object other);

  void override(Object other, Set<String> include);
}
