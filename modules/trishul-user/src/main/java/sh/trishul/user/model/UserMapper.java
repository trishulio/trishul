package sh.trishul.user.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sh.trishul.model.base.mapper.BaseMapper;
import sh.trishul.model.mapper.DeleteResultMapper;
import sh.trishul.user.role.model.UserRoleMapper;
import sh.trishul.user.salutation.model.UserSalutationMapper;
import sh.trishul.user.status.UserStatusMapper;

@Mapper(uses = {UserStatusMapper.class, UserSalutationMapper.class, UserRoleMapper.class,
    DeleteResultMapper.class})
public interface UserMapper extends BaseMapper<User, UserDto, AddUserDto, UpdateUserDto> {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(target = User.ATTR_ROLE_BINDINGS, ignore = true)
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
  @Mapping(target = User.ATTR_IAAS_USERNAME, ignore = true)
  User fromDto(Long id);

  @Override
  @Mapping(target = User.ATTR_ROLE_BINDINGS, ignore = true)
  @Mapping(target = User.ATTR_ID, ignore = true)
  @Mapping(target = User.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = User.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = User.ATTR_VERSION, ignore = true)
  @Mapping(target = User.ATTR_STATUS, source = "statusId")
  @Mapping(target = User.ATTR_SALUTATION, source = "salutationId")
  @Mapping(target = User.ATTR_ROLES, source = "roleIds")
  @Mapping(target = User.ATTR_IAAS_USERNAME, ignore = true)
  User fromAddDto(AddUserDto addUserDto);

  @Override
  @Mapping(target = User.ATTR_ROLE_BINDINGS, ignore = true)
  @Mapping(target = User.ATTR_LAST_UPDATED, ignore = true)
  @Mapping(target = User.ATTR_CREATED_AT, ignore = true)
  @Mapping(target = User.ATTR_STATUS, source = "statusId")
  @Mapping(target = User.ATTR_SALUTATION, source = "salutationId")
  @Mapping(target = User.ATTR_ROLES, source = "roleIds")
  @Mapping(target = User.ATTR_IAAS_USERNAME, ignore = true)
  User fromUpdateDto(UpdateUserDto updateUserDto);

  @Override
  @Mapping(target = UserDto.ATTR_OBJECT_STORE_FILE, ignore = true)
  UserDto toDto(User user);
}
