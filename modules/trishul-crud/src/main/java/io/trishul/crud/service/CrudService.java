package io.trishul.crud.service;

import io.trishul.base.types.base.pojo.Identified;
import io.trishul.model.base.pojo.DeleteResult;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface CrudService<ID, E, BE, UE extends Identified<ID>, A> {
  boolean exists(Set<ID> ids);

  boolean exist(ID id);

  DeleteResult delete(Set<ID> ids);

  DeleteResult delete(ID id);

  E get(ID id);

  List<E> getByIds(Collection<? extends Identified<ID>> idProviders);

  List<E> getByAccessorIds(Collection<? extends A> accessors);

  List<E> add(List<? extends BE> additions);

  List<E> put(List<? extends UE> updates);

  List<E> patch(List<? extends UE> updates);
}
