package io.trishul.user.service.user.service.autoconfiguration;

import java.util.Set;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import io.trishul.base.types.base.pojo.OwnedByAccessor;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.crud.service.CrudEntityMergerService;
import io.trishul.crud.service.CrudRepoService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.crud.service.LockService;
import io.trishul.iaas.user.service.TenantIaasUserService;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import io.trishul.object.store.file.service.decorator.TemporaryImageSrcDecorator;
import io.trishul.repo.jpa.repository.service.RepoService;
import io.trishul.user.model.AssignedToAccessor;
import io.trishul.user.model.BaseUser;
import io.trishul.user.model.UpdateUser;
import io.trishul.user.model.User;
import io.trishul.user.model.UserAccessor;
import io.trishul.user.model.UserRefresher;
import io.trishul.user.role.binding.model.UserRoleBinding;
import io.trishul.user.role.binding.model.UserRoleBindingAccessor;
import io.trishul.user.role.binding.model.UserRoleBindingRefresher;
import io.trishul.user.role.model.BaseUserRole;
import io.trishul.user.role.model.UpdateUserRole;
import io.trishul.user.role.model.UserRole;
import io.trishul.user.role.model.UserRoleAccessor;
import io.trishul.user.role.model.UserRoleRefresher;
import io.trishul.user.salutation.model.UserSalutation;
import io.trishul.user.salutation.model.UserSalutationAccessor;
import io.trishul.user.salutation.model.UserSalutationRefresher;
import io.trishul.user.service.user.service.controller.UserDtoDecorator;
import io.trishul.user.service.user.service.repository.UserRepository;
import io.trishul.user.service.user.service.repository.role.repository.UserRoleRepository;
import io.trishul.user.service.user.service.role.service.UserRoleService;
import io.trishul.user.service.user.service.salutation.repository.UserSalutationRepository;
import io.trishul.user.service.user.service.salutation.service.UserSalutationService;
import io.trishul.user.service.user.service.service.UserService;
import io.trishul.user.service.user.service.status.repository.UserStatusRepository;
import io.trishul.user.status.UserStatus;
import io.trishul.user.status.UserStatusAccessor;
import io.trishul.user.status.UserStatusRefresher;

@Configuration
public class UserServiceAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(UserService.class)
  public UserService userService(LockService lockService, UserRepository userRepository,
      Refresher<User, UserAccessor<?>> userRefresher, TenantIaasUserService tenantIaasUserService) {
    final EntityMergerService<Long, User, BaseUser<?>, UpdateUser<?>> updateService
        = new CrudEntityMergerService<>(lockService, BaseUser.class, UpdateUser.class, User.class,
            Set.of());
    final RepoService<Long, User, UserAccessor<?>> repoService
        = new CrudRepoService<>(userRepository, userRefresher);
    return new UserService(updateService, repoService, userRepository, tenantIaasUserService);
  }

  @Bean
  @ConditionalOnMissingBean(UserRoleService.class)
  public UserRoleService userRoleService(LockService lockService,
      UserRoleRepository userRoleRepository,
      Refresher<UserRole, UserRoleAccessor<?>> userRoleRefresher) {
    final EntityMergerService<Long, UserRole, BaseUserRole<?>, UpdateUserRole<?>> updateService
        = new CrudEntityMergerService<>(lockService, BaseUserRole.class, UpdateUserRole.class,
            UserRole.class, Set.of());
    final RepoService<Long, UserRole, UserRoleAccessor<?>> repoService
        = new CrudRepoService<>(userRoleRepository, userRoleRefresher);
    return new UserRoleService(updateService, repoService);
  }

  @Bean
  @ConditionalOnMissingBean(UserSalutationService.class)
  public UserSalutationService userSalutationService(
      UserSalutationRepository userSalutationRepository) {
    final UserSalutationService userSalutationService
        = new UserSalutationService(userSalutationRepository);
    return userSalutationService;
  }

  @Bean
  @ConditionalOnMissingBean(UserDtoDecorator.class)
  public UserDtoDecorator userDecorator(TemporaryImageSrcDecorator imgDecorator) {
    return new UserDtoDecorator(imgDecorator);
  }

  @Bean
  public AccessorRefresher<Long, UserAccessor<?>, User> userAccessorRefresher(UserRepository repo) {
    return new AccessorRefresher<>(User.class, accessor -> accessor.getUser(),
        (accessor, user) -> accessor.setUser(user), ids -> repo.findAllById(ids));
  }

  @Bean
  public AccessorRefresher<Long, UserRoleAccessor<?>, UserRole> userRoleAccessorRefresher(
      UserRoleRepository repo) {
    return new AccessorRefresher<>(UserRole.class, accessor -> accessor.getRole(),
        (accessor, role) -> accessor.setRole(role), ids -> repo.findAllById(ids));
  }

  @Bean
  public AccessorRefresher<Long, UserSalutationAccessor<?>, UserSalutation> userSalutationAccessorRefresher(
      UserSalutationRepository repo) {
    return new AccessorRefresher<>(UserSalutation.class, accessor -> accessor.getSalutation(),
        (accessor, salutation) -> accessor.setSalutation(salutation), ids -> repo.findAllById(ids));
  }

  @Bean
  public AccessorRefresher<Long, UserStatusAccessor<?>, UserStatus> userStatusAccessorRefresher(
      UserStatusRepository repo) {
    return new AccessorRefresher<>(UserStatus.class, accessor -> accessor.getStatus(),
        (accessor, status) -> accessor.setStatus(status), ids -> repo.findAllById(ids));
  }

  @Bean
  public AccessorRefresher<Long, AssignedToAccessor<?>, User> assignedToAccessorRefresher(
      UserRepository repo) {
    return new AccessorRefresher<>(User.class, accessor -> accessor.getAssignedTo(),
        (accessor, assignedTo) -> accessor.setAssignedTo(assignedTo), ids -> repo.findAllById(ids));
  }

  @Bean
  public AccessorRefresher<Long, OwnedByAccessor<User>, User> ownedByAccessorRefresher(
      UserRepository repo) {
    return new AccessorRefresher<>(User.class, accessor -> accessor.getOwnedBy(),
        (accessor, ownedBy) -> accessor.setOwnedBy(ownedBy), ids -> repo.findAllById(ids));
  }

  @Bean
  public UserRefresher userRefresher(
      AccessorRefresher<Long, UserAccessor<?>, User> userAccessorRefresher,
      AccessorRefresher<Long, AssignedToAccessor<?>, User> assignedToAccessorRefresher,
      AccessorRefresher<Long, OwnedByAccessor<User>, User> ownedByAccessorRefresher,
      Refresher<UserStatus, UserStatusAccessor<?>> statusRefresher,
      Refresher<UserSalutation, UserSalutationAccessor<?>> salutationRefresher,
      @Lazy Refresher<UserRoleBinding, UserRoleBindingAccessor<?>> roleBindingRefresher) {
    return new UserRefresher(userAccessorRefresher, assignedToAccessorRefresher,
        ownedByAccessorRefresher, statusRefresher, salutationRefresher, roleBindingRefresher);
  }

  @Bean
  public Refresher<UserRoleBinding, UserRoleBindingAccessor<?>> userRoleBindingRefresher(
      Refresher<UserRole, UserRoleAccessor<?>> userRoleRefresher) {
    return new UserRoleBindingRefresher(userRoleRefresher);
  }

  @Bean
  public Refresher<UserRole, UserRoleAccessor<?>> userRoleRefresher(
      AccessorRefresher<Long, UserRoleAccessor<?>, UserRole> userRoleAccessorRefresher) {
    return new UserRoleRefresher(userRoleAccessorRefresher);
  }

  @Bean
  public Refresher<UserSalutation, UserSalutationAccessor<?>> userSalutationRefresher(
      AccessorRefresher<Long, UserSalutationAccessor<?>, UserSalutation> userSalutationAccessorRefresher) {
    return new UserSalutationRefresher(userSalutationAccessorRefresher);
  }

  @Bean
  public Refresher<UserStatus, UserStatusAccessor<?>> userStatusRefresher(
      AccessorRefresher<Long, UserStatusAccessor<?>, UserStatus> userStatusAccessorRefresher) {
    return new UserStatusRefresher(userStatusAccessorRefresher);
  }
}
