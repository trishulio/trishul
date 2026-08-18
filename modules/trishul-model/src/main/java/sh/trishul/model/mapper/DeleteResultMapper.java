package sh.trishul.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sh.trishul.model.base.dto.DeleteResultDto;
import sh.trishul.model.base.pojo.DeleteResult;

@Mapper
public interface DeleteResultMapper {
  DeleteResultMapper INSTANCE = Mappers.getMapper(DeleteResultMapper.class);

  DeleteResultDto toDto(DeleteResult deleteResult);

  DeleteResult fromDto(DeleteResultDto deleteResultDto);
}
