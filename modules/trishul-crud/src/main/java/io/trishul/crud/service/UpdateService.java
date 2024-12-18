package io.trishul.crud.service;

import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.base.types.base.pojo.UpdatableEntity;
import java.util.List;

public interface UpdateService<ID, E extends CrudEntity<ID>, BE, UE extends UpdatableEntity<ID>> {
    List<E> getAddEntities(List<BE> additions);

    List<E> getPutEntities(List<E> existingItems, List<UE> updates);

    List<E> getPatchEntities(List<E> existingItems, List<UE> patches);
}
