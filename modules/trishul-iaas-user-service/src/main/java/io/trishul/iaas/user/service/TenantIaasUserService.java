package io.trishul.iaas.user.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class TenantIaasUserService {
  private static final Logger log = LoggerFactory.getLogger(TenantIaasUserService.class);

  private final IaasRepository<String, IaasUser, BaseIaasUser<?>, UpdateIaasUser<?>> userRepo;
  private final IaasRepository<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership<?>, UpdateIaasUserTenantMembership<?>> membershipRepo;

  private final TenantIaasUserMapper userMapper;
  private final TenantIdProvider tenantIdProvider;

  public TenantIaasUserService(
      IaasRepository<String, IaasUser, BaseIaasUser<?>, UpdateIaasUser<?>> userRepo,
      IaasRepository<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership<?>, UpdateIaasUserTenantMembership<?>> membershipRepo,
      TenantIaasUserMapper userMapper, TenantIdProvider tenantIdProvider) {
    this.userRepo = userRepo;
    this.membershipRepo = membershipRepo;
    this.userMapper = userMapper;
    this.tenantIdProvider = tenantIdProvider;
  }

  public List<IaasUserTenantMembership> put(List<User> users) {
    String tenantId = this.tenantIdProvider.getTenantId().toString();

    @SuppressWarnings("unchecked")
    List<? extends UpdateIaasUser<?>> updates
        = (List<? extends UpdateIaasUser<?>>) userMapper.fromUsers(users);

    List<IaasUser> iaasUsers = this.userRepo.put(updates);
    List<IaasUserTenantMembership> memberships = iaasUsers.stream()
        .map(iaasUser -> new IaasUserTenantMembership(iaasUser, tenantId)).toList();

    return this.membershipRepo.put(memberships);
  }

  public long delete(List<User> users) {
    String tenantId = this.tenantIdProvider.getTenantId().toString();

    List<IaasUser> iaasUsers = userMapper.fromUsers(users);
    Set<String> userIds = iaasUsers.stream().map(IaasUser::getId).collect(Collectors.toSet());
    Set<IaasUserTenantMembershipId> membershipIds
        = userIds.stream().map(userId -> new IaasUserTenantMembershipId(userId, tenantId))
            .collect(Collectors.toSet());

    long membershipCount = this.membershipRepo.delete(membershipIds);

    long userCount = this.userRepo.delete(userIds);

    log.info("Deleted user memberships: {}; users: {}", membershipCount, userCount);

    return userCount;
  }
}
