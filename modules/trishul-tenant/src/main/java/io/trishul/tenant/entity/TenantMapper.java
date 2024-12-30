package io.trishul.tenant.entity;

import io.trishul.model.base.mapper.BaseMapper;
import io.trishul.tenant.dto.AddTenantDto;
import io.trishul.tenant.dto.TenantDto;
import io.trishul.tenant.dto.UpdateTenantDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TenantMapper extends BaseMapper<Tenant, TenantDto, AddTenantDto, UpdateTenantDto> {
  TenantMapper INSTANCE = Mappers.getMapper(TenantMapper.class);

  @Override
  TenantDto toDto(Tenant invoice);

  @Override
  @Mapping(target = Tenant.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = Tenant.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = Tenant.ATTR_IS_READY, ignore = true)
  Tenant fromUpdateDto(UpdateTenantDto dto);

  @Override
  @Mapping(target = Tenant.ATTR_ID, ignore = true)
  @Mapping(target = Tenant.ATTR_VERSION, ignore = true)
  @Mapping(target = Tenant.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = Tenant.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = Tenant.ATTR_IS_READY, ignore = true)
  Tenant fromAddDto(AddTenantDto dto);
}
