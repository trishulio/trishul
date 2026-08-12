package sh.trishul.model.base.mapper;

import sh.trishul.model.base.dto.BaseDto;

public interface BaseMapper<Entity, EntityDto extends BaseDto, AddDto extends BaseDto, UpdateDto extends BaseDto> {
  EntityDto toDto(Entity e);

  Entity fromAddDto(AddDto dto);

  Entity fromUpdateDto(UpdateDto dto);
}
