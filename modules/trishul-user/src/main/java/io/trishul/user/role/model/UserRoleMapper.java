package io.trishul.user.role.model;

import io.trishul.model.base.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserRoleMapper
        extends BaseMapper<UserRole, UserRoleDto, AddUserRoleDto, UpdateUserRoleDto> {
    UserRoleMapper INSTANCE = Mappers.getMapper(UserRoleMapper.class);

    @Mapping(target = UserRole.ATTR_NAME, ignore = true)
    @Mapping(target = UserRole.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = UserRole.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = UserRole.ATTR_VERSION, ignore = true)
    UserRole fromDto(Long id);

    @Override
    UserRoleDto toDto(UserRole userRole);

    @Override
    @Mapping(target = UserRole.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = UserRole.ATTR_LAST_UPDATED, ignore = true)
    UserRole fromUpdateDto(UpdateUserRoleDto dto);

    @Override
    @Mapping(target = UserRole.ATTR_ID, ignore = true)
    @Mapping(target = UserRole.ATTR_CREATED_AT, ignore = true)
    @Mapping(target = UserRole.ATTR_LAST_UPDATED, ignore = true)
    @Mapping(target = UserRole.ATTR_VERSION, ignore = true)
    UserRole fromAddDto(AddUserRoleDto dto);
}
