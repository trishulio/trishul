package io.trishul.user.model;

import io.trishul.model.base.mapper.BaseMapper;
import io.trishul.user.role.model.UserRoleMapper;
import io.trishul.user.salutation.model.UserSalutationMapper;
import io.trishul.user.status.UserStatusMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserStatusMapper.class, UserSalutationMapper.class, UserRoleMapper.class})
public interface UserMapper extends BaseMapper<User, UserDto, AddUserDto, UpdateUserDto> {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(target = "roleBindings", ignore = true)
  @Mapping(target = User.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = User.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = User.ATTR_VERSION, ignore = true)
  @Mapping(target = User.ATTR_STATUS, ignore = true)
  @Mapping(target = User.ATTR_SALUTATION, ignore = true)
  @Mapping(target = User.ATTR_DISPLAY_NAME, ignore = true)
  @Mapping(target = User.ATTR_EMAIL, ignore = true)
  @Mapping(target = User.ATTR_USER_NAME, ignore = true)
  @Mapping(target = User.ATTR_FIRST_NAME, ignore = true)
  @Mapping(target = User.ATTR_LAST_NAME, ignore = true)
  @Mapping(target = User.ATTR_ROLES, ignore = true)
  @Mapping(target = User.ATTR_IMAGE_SRC, ignore = true)
  @Mapping(target = User.ATTR_PHONE_NUMBER, ignore = true)
  User fromDto(Long id);

  @Override
  @Mapping(target = "roleBindings", ignore = true)
  @Mapping(target = User.ATTR_ID, ignore = true)
  @Mapping(target = User.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = User.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = User.ATTR_VERSION, ignore = true)
  @Mapping(target = User.ATTR_STATUS, source = "statusId")
  @Mapping(target = User.ATTR_SALUTATION, source = "salutationId")
  @Mapping(target = User.ATTR_ROLES, source = "roleIds")
  User fromAddDto(AddUserDto addUserDto);

  @Override
  @Mapping(target = "roleBindings", ignore = true)
  @Mapping(target = User.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = User.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = User.ATTR_EMAIL, ignore = true)
  @Mapping(target = User.ATTR_STATUS, source = "statusId")
  @Mapping(target = User.ATTR_SALUTATION, source = "salutationId")
  @Mapping(target = User.ATTR_ROLES, source = "roleIds")
  User fromUpdateDto(UpdateUserDto updateUserDto);

  @Override
  UserDto toDto(User user);
}
