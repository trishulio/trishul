package io.trishul.crud.service;

import io.trishul.base.types.base.pojo.UpdatableEntity;
import java.util.List;

public interface EntityMergerService<ID, E, BE, UE extends UpdatableEntity<ID, ?>> {
  List<E> getAddEntities(List<? extends BE> additions);

  List<E> getPutEntities(List<E> existingItems, List<? extends UE> updates);

  List<E> getPatchEntities(List<E> existingItems, List<? extends UE> patches);
}
