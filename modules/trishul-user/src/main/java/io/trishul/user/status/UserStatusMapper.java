package io.trishul.user.status;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserStatusMapper {
  UserStatusMapper INSTANCE = Mappers.getMapper(UserStatusMapper.class);

  @Mapping(target = BaseUserStatus.ATTR_NAME, ignore = true)
  @Mapping(target = Audited.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = Audited.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = Versioned.ATTR_VERSION, ignore = true)
  UserStatus fromDto(Long id);

  UserStatusDto toDto(UserStatus status);
}
