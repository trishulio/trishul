package io.trishul.ai.tool.model;

import io.trishul.model.base.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AiToolMapper extends BaseMapper<AiTool, AiToolDto, AddAiToolDto, UpdateAiToolDto> {
  AiToolMapper INSTANCE = Mappers.getMapper(AiToolMapper.class);

  @Mapping(target = AiTool.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiTool.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = AiTool.ATTR_VERSION, ignore = true)
  @Mapping(target = AiTool.ATTR_NAME, ignore = true)
  @Mapping(target = AiTool.ATTR_BEAN_NAME, ignore = true)
  @Mapping(target = AiTool.ATTR_DESCRIPTION, ignore = true)
  @Mapping(target = AiTool.ATTR_IS_ENABLED, ignore = true)
  AiTool fromDto(Long id);

  @Override

  @Mapping(target = AiTool.ATTR_ID, ignore = true)
  @Mapping(target = AiTool.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiTool.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = AiTool.ATTR_VERSION, ignore = true)
  AiTool fromAddDto(AddAiToolDto addDto);

  @Override
  @Mapping(target = AiTool.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = AiTool.ATTR_CREATED_AT, ignore = true)
  AiTool fromUpdateDto(UpdateAiToolDto updateDto);

  @Override
  AiToolDto toDto(AiTool entity);
}
