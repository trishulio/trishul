package io.trishul.user.service.user.service.autoconfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.base.types.base.pojo.OwnedByAccessor;
import io.trishul.base.types.base.pojo.Refresher;
import io.trishul.crud.service.LockService;
import io.trishul.iaas.user.service.TenantIaasUserService;
import io.trishul.model.base.pojo.refresher.accessor.AccessorRefresher;
import io.trishul.object.store.file.service.decorator.TemporaryImageSrcDecorator;
import io.trishul.user.model.AssignedToAccessor;
import io.trishul.user.model.User;
import io.trishul.user.model.UserAccessor;
import io.trishul.user.role.binding.model.UserRoleBinding;
import io.trishul.user.role.binding.model.UserRoleBindingAccessor;
import io.trishul.user.role.model.UserRole;
import io.trishul.user.role.model.UserRoleAccessor;
import io.trishul.user.salutation.model.UserSalutation;
import io.trishul.user.salutation.model.UserSalutationAccessor;
import io.trishul.user.service.user.service.controller.UserDtoDecorator;
import io.trishul.user.service.user.service.repository.UserRepository;
import io.trishul.user.service.user.service.repository.role.repository.UserRoleRepository;
import io.trishul.user.service.user.service.role.service.UserRoleService;
import io.trishul.user.service.user.service.salutation.repository.UserSalutationRepository;
import io.trishul.user.service.user.service.salutation.service.UserSalutationService;
import io.trishul.user.service.user.service.service.AccountService;
import io.trishul.user.service.user.service.service.UserService;
import io.trishul.user.service.user.service.status.repository.UserStatusRepository;
import io.trishul.user.status.UserStatus;
import io.trishul.user.status.UserStatusAccessor;

class UserServiceAutoConfigurationTest {

  private UserServiceAutoConfiguration config;

  @BeforeEach
  void setUp() {
    config = new UserServiceAutoConfiguration();
  }

  @Test
  @SuppressWarnings("unchecked")
  void testUserService_ReturnsNonNull() {
    LockService mockLockService = mock(LockService.class);
    UserRepository mockUserRepository = mock(UserRepository.class);
    Refresher<User, UserAccessor<?>> mockUserRefresher = mock(Refresher.class);
    TenantIaasUserService mockTenantIaasUserService = mock(TenantIaasUserService.class);

    UserService result = config.userService(
        mockLockService, mockUserRepository, mockUserRefresher, mockTenantIaasUserService);

    assertNotNull(result);
  }

  @Test
  void testAccountService_ReturnsNonNull() {
    UserRepository mockUserRepository = mock(UserRepository.class);
    ContextHolder mockContextHolder = mock(ContextHolder.class);

    AccountService result = config.accountService(mockUserRepository, mockContextHolder);

    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testUserRoleService_ReturnsNonNull() {
    LockService mockLockService = mock(LockService.class);
    UserRoleRepository mockUserRoleRepository = mock(UserRoleRepository.class);
    Refresher<UserRole, UserRoleAccessor<?>> mockUserRoleRefresher = mock(Refresher.class);

    UserRoleService result = config.userRoleService(
        mockLockService, mockUserRoleRepository, mockUserRoleRefresher);

    assertNotNull(result);
  }

  @Test
  void testUserSalutationService_ReturnsNonNull() {
    UserSalutationRepository mockUserSalutationRepository = mock(UserSalutationRepository.class);

    UserSalutationService result = config.userSalutationService(mockUserSalutationRepository);

    assertNotNull(result);
  }

  @Test
  void testUserDecorator_ReturnsNonNull() {
    TemporaryImageSrcDecorator mockImgDecorator = mock(TemporaryImageSrcDecorator.class);

    UserDtoDecorator result = config.userDecorator(mockImgDecorator);

    assertNotNull(result);
  }

  @Test
  void testUserAccessorRefresher_ReturnsNonNull() {
    UserRepository mockRepo = mock(UserRepository.class);

    AccessorRefresher<Long, UserAccessor<?>, User> result = config.userAccessorRefresher(mockRepo);

    assertNotNull(result);
  }

  @Test
  void testUserRoleAccessorRefresher_ReturnsNonNull() {
    UserRoleRepository mockRepo = mock(UserRoleRepository.class);

    AccessorRefresher<Long, UserRoleAccessor<?>, UserRole> result = config.userRoleAccessorRefresher(mockRepo);

    assertNotNull(result);
  }

  @Test
  void testUserSalutationAccessorRefresher_ReturnsNonNull() {
    UserSalutationRepository mockRepo = mock(UserSalutationRepository.class);

    AccessorRefresher<Long, UserSalutationAccessor<?>, UserSalutation> result = config.userSalutationAccessorRefresher(mockRepo);

    assertNotNull(result);
  }

  @Test
  void testUserStatusAccessorRefresher_ReturnsNonNull() {
    UserStatusRepository mockRepo = mock(UserStatusRepository.class);

    AccessorRefresher<Long, UserStatusAccessor<?>, UserStatus> result = config.userStatusAccessorRefresher(mockRepo);

    assertNotNull(result);
  }

  @Test
  void testAssignedToAccessorRefresher_ReturnsNonNull() {
    UserRepository mockRepo = mock(UserRepository.class);

    AccessorRefresher<Long, AssignedToAccessor<?>, User> result = config.assignedToAccessorRefresher(mockRepo);

    assertNotNull(result);
  }

  @Test
  void testOwnedByAccessorRefresher_ReturnsNonNull() {
    UserRepository mockRepo = mock(UserRepository.class);

    AccessorRefresher<Long, OwnedByAccessor<User>, User> result = config.ownedByAccessorRefresher(mockRepo);

    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testUserRefresher_ReturnsNonNull() {
    AccessorRefresher<Long, UserAccessor<?>, User> mockUserAccessorRefresher = mock(AccessorRefresher.class);
    AccessorRefresher<Long, AssignedToAccessor<?>, User> mockAssignedToAccessorRefresher = mock(AccessorRefresher.class);
    AccessorRefresher<Long, OwnedByAccessor<User>, User> mockOwnedByAccessorRefresher = mock(AccessorRefresher.class);
    Refresher<UserStatus, UserStatusAccessor<?>> mockStatusRefresher = mock(Refresher.class);
    Refresher<UserSalutation, UserSalutationAccessor<?>> mockSalutationRefresher = mock(Refresher.class);
    Refresher<UserRoleBinding, UserRoleBindingAccessor<?>> mockRoleBindingRefresher = mock(Refresher.class);

    Refresher<User, UserAccessor<?>> result = config.userRefresher(
        mockUserAccessorRefresher,
        mockAssignedToAccessorRefresher,
        mockOwnedByAccessorRefresher,
        mockStatusRefresher,
        mockSalutationRefresher,
        mockRoleBindingRefresher);

    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testUserRoleBindingRefresher_ReturnsNonNull() {
    Refresher<UserRole, UserRoleAccessor<?>> mockUserRoleRefresher = mock(Refresher.class);

    Refresher<UserRoleBinding, UserRoleBindingAccessor<?>> result = config.userRoleBindingRefresher(mockUserRoleRefresher);

    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testUserRoleRefresher_ReturnsNonNull() {
    AccessorRefresher<Long, UserRoleAccessor<?>, UserRole> mockUserRoleAccessorRefresher = mock(AccessorRefresher.class);

    Refresher<UserRole, UserRoleAccessor<?>> result = config.userRoleRefresher(mockUserRoleAccessorRefresher);

    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testUserSalutationRefresher_ReturnsNonNull() {
    AccessorRefresher<Long, UserSalutationAccessor<?>, UserSalutation> mockUserSalutationAccessorRefresher = mock(AccessorRefresher.class);

    Refresher<UserSalutation, UserSalutationAccessor<?>> result = config.userSalutationRefresher(mockUserSalutationAccessorRefresher);

    assertNotNull(result);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testUserStatusRefresher_ReturnsNonNull() {
    AccessorRefresher<Long, UserStatusAccessor<?>, UserStatus> mockUserStatusAccessorRefresher = mock(AccessorRefresher.class);

    Refresher<UserStatus, UserStatusAccessor<?>> result = config.userStatusRefresher(mockUserStatusAccessorRefresher);

    assertNotNull(result);
  }
}
