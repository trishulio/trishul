package io.trishul.model.mapper;

import io.trishul.model.base.dto.DeleteResultDto;
import io.trishul.model.base.pojo.DeleteResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeleteResultMapper {
  DeleteResultMapper INSTANCE = Mappers.getMapper(DeleteResultMapper.class);

  DeleteResultDto toDto(DeleteResult deleteResult);

  DeleteResult fromDto(DeleteResultDto deleteResultDto);
}
