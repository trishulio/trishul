package io.trishul.iaas.user.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.iaas.user.model.BaseIaasUser;
import io.trishul.iaas.user.model.BaseIaasUserTenantMembership;
import io.trishul.iaas.user.model.IaasUser;
import io.trishul.iaas.user.model.IaasUserTenantMembership;
import io.trishul.iaas.user.model.IaasUserTenantMembershipId;
import io.trishul.iaas.user.model.TenantIaasUserMapper;
import io.trishul.iaas.user.model.UpdateIaasUser;
import io.trishul.iaas.user.model.UpdateIaasUserTenantMembership;
import io.trishul.tenant.entity.TenantIdProvider;
import io.trishul.user.model.User;

public class TenantIaasUserServiceTest {
  private TenantIaasUserService service;

  private IaasRepository<String, IaasUser, BaseIaasUser<?>, UpdateIaasUser<?>> mUserService;
  private IaasRepository<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership<?>, UpdateIaasUserTenantMembership<?>> mMembershipService;
  private TenantIdProvider mTenantIdProvider;

  @BeforeEach
  public void init() {
    mUserService = mock(IaasRepository.class);
    mMembershipService = mock(IaasRepository.class);
    mTenantIdProvider = mock(TenantIdProvider.class);

    service = new TenantIaasUserService(mUserService, mMembershipService,
        TenantIaasUserMapper.INSTANCE, mTenantIdProvider);
  }

  @Test
  public void testPut_ReturnsMembershipsAfterSavingUsersAndCreatingMemberships() {
    doAnswer(inv -> inv.getArgument(0, List.class)).when(mUserService).put(anyList());
    doAnswer(inv -> inv.getArgument(0, List.class)).when(mMembershipService).put(anyList());

    doReturn(UUID.fromString("00000000-0000-0000-0000-000000000001")).when(mTenantIdProvider)
        .getTenantId();

    List<User> users = List.of(
        new User().setId(1L).setUserName("USERNAME_1").setEmail("example-1@localhost")
            .setPhoneNumber("phone-number-1").setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0))
            .setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0)),
        new User().setId(2L).setUserName("USERNAME_2").setEmail("example-2@localhost")
            .setPhoneNumber("phone-number-2").setCreatedAt(LocalDateTime.of(2001, 1, 1, 0, 0))
            .setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0)));

    List<IaasUserTenantMembership> memberships = service.put(users);

    List<IaasUserTenantMembership> expected = List.of(
        new IaasUserTenantMembership()
            .setUser(new IaasUser().setId("USERNAME_1").setEmail("example-1@localhost")
                .setPhoneNumber("phone-number-1"))
            .setTenantId("00000000-0000-0000-0000-000000000001"),
        new IaasUserTenantMembership()
            .setUser(new IaasUser().setId("USERNAME_2").setEmail("example-2@localhost")
                .setPhoneNumber("phone-number-2"))
            .setTenantId("00000000-0000-0000-0000-000000000001"));

    assertEquals(expected, memberships);
  }

  @Test
  public void testDelete_RemovesMembershipAndDeletesUsers() {
    doReturn(55L).when(mMembershipService)
        .delete(Set.of(
            new IaasUserTenantMembershipId("example-1@localhost",
                "00000000-0000-0000-0000-000000000001"),
            new IaasUserTenantMembershipId("example-2@localhost",
                "00000000-0000-0000-0000-000000000001")));
    doReturn(55L).when(mUserService).delete(Set.of("example-1@localhost", "example-2@localhost"));

    doReturn(UUID.fromString("00000000-0000-0000-0000-000000000001")).when(mTenantIdProvider)
        .getTenantId();

    List<User> users = List.of(
        new User(1L, "USERNAME_1", null, null, null, "example-1@localhost", "phone-number-1", null,
            null, null, null, LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0), null),
        new User(2L, "USERNAME_2", null, null, null, "example-2@localhost", "phone-number-2", null,
            null, null, null, LocalDateTime.of(2001, 1, 1, 0, 0),
            LocalDateTime.of(2001, 1, 1, 0, 0), null));

    long count = service.delete(users);

    assertEquals(55L, count);
  }
}
