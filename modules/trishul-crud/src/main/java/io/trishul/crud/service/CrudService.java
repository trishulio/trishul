package io.trishul.crud.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import io.trishul.base.types.base.pojo.Identified;

public interface CrudService<ID, E, BE, UE extends Identified<ID>, A> {
  boolean exists(Set<ID> ids);

  boolean exist(ID id);

  long delete(Set<ID> ids);

  long delete(ID id);

  E get(ID id);

  List<E> getByIds(Collection<? extends Identified<ID>> idProviders);

  List<E> getByAccessorIds(Collection<? extends A> accessors);

  List<E> add(List<? extends BE> additions);

  List<E> put(List<? extends UE> updates);

  List<E> patch(List<? extends UE> updates);
}
