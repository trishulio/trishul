package io.trishul.iaas.client;

public interface IaasClient<ID, Entity, BaseEntity, UpdateEntity> {
  Entity get(ID id);

  <BE extends BaseEntity> Entity add(BE entity);

  <UE extends UpdateEntity> Entity put(UE entity);

  boolean delete(ID id);

  boolean exists(ID id);
}
