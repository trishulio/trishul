package io.trishul.iaas.mapper;

public interface IaasEntityMapper<IaasEntity, Entity> {
  Entity fromIaasEntity(IaasEntity entity);
}
