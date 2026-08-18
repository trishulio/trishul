package sh.trishul.crud.service;

import java.util.List;
import sh.trishul.base.types.base.pojo.UpdatableEntity;

public interface EntityMergerService<ID, E, BE, UE extends UpdatableEntity<ID, ?>> {
  List<E> getAddEntities(List<? extends BE> additions);

  List<E> getPutEntities(List<E> existingItems, List<? extends UE> updates);

  List<E> getPatchEntities(List<E> existingItems, List<? extends UE> patches);
}
