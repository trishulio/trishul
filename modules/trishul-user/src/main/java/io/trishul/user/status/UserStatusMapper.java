package io.trishul.user.status;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.dto.user.UserStatusDto;

@Mapper
public interface UserStatusMapper {
    UserStatusMapper INSTANCE = Mappers.getMapper(UserStatusMapper.class);

    @Mapping(target = UserStatus.ATTR_NAME, ignore = true)
    @Mapping(target = UserStatus.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = UserStatus.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = UserStatus.ATTR_VERSION, ignore = true)
    UserStatus fromDto(Long id);

    UserStatusDto toDto(UserStatus status);
}
