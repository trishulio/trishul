package io.trishul.crud.service;

import java.util.List;

import io.trishul.model.base.pojo.CrudEntity;
import io.trishul.model.base.pojo.UpdatableEntity;

public interface UpdateService<ID, E extends CrudEntity<ID>, BE, UE extends UpdatableEntity<ID>> {
    List<E> getAddEntities(List<BE> additions);

    List<E> getPutEntities(List<E> existingItems, List<UE> updates);

    List<E> getPatchEntities(List<E> existingItems, List<UE> patches);
}