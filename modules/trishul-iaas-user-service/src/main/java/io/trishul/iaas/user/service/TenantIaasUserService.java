package io.trishul.iaas.user.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.trishul.auth.session.context.holder.ContextHolder;
import io.trishul.iaas.idp.tenant.model.TenantIaasIdpTenantMapper;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.iaas.user.model.BaseIaasUser;
import io.trishul.iaas.user.model.BaseIaasUserTenantMembership;
import io.trishul.iaas.user.model.IaasUser;
import io.trishul.iaas.user.model.IaasUserTenantMembership;
import io.trishul.iaas.user.model.IaasUserTenantMembershipId;
import io.trishul.iaas.user.model.TenantIaasUserMapper;
import io.trishul.iaas.user.model.UpdateIaasUser;
import io.trishul.iaas.user.model.UpdateIaasUserTenantMembership;
import io.trishul.user.model.User;

public class TenantIaasUserService {
    private static final Logger log = LoggerFactory.getLogger(TenantIaasUserService.class);

    private final IaasRepository<String, IaasUser, BaseIaasUser, UpdateIaasUser> userService;
    private final IaasRepository<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership, UpdateIaasUserTenantMembership> membershipService;

    private final TenantIaasUserMapper userMapper;

    private final ContextHolder ctxHolder;

    public TenantIaasUserService(
        IaasRepository<String, IaasUser, BaseIaasUser, UpdateIaasUser> userService,
        IaasRepository<IaasUserTenantMembershipId, IaasUserTenantMembership, BaseIaasUserTenantMembership, UpdateIaasUserTenantMembership> membershipService,
        TenantIaasUserMapper userMapper,
        TenantIaasIdpTenantMapper tenantMapper,
        ContextHolder ctxHolder
    ) {
        this.userService = userService;
        this.membershipService = membershipService;
        this.userMapper = userMapper;
        this.tenantMapper = tenantMapper;
        this.ctxHolder = ctxHolder;
    }

    public List<IaasUserTenantMembership> put(List<User> users) {
        // TODO: temp: groupId should be tenantId -- fix this
        String tenantId = this.ctxHolder.getPrincipalContext().getGroupId().toString();

        List<UpdateIaasUser> updates = userMapper.fromUsers(users);

        List<IaasUser> iaasUsers = this.userService.put(updates);
        List<IaasUserTenantMembership> memberships = iaasUsers.stream().map(iaasUser -> new IaasUserTenantMembership(iaasUser, tenantId)).toList();

        return this.membershipService.put(memberships);
    }

    public long delete(List<User> users) {
        // TODO: temp: groupId should be tenantId -- fix this
        String tenantId = this.ctxHolder.getPrincipalContext().getGroupId().toString();

        List<IaasUser> iaasUsers = userMapper.fromUsers(users);
        Set<String> userIds = iaasUsers.stream().map(IaasUser::getId).collect(Collectors.toSet());
        Set<IaasUserTenantMembershipId> membershipIds = userIds.stream().map(userId -> new IaasUserTenantMembershipId(userId, tenantId)).collect(Collectors.toSet());

        long membershipCount = this.membershipService.delete(membershipIds);

        long userCount = this.userService.delete(userIds);

        log.info("Deleted user memberships: {}; users: {}", membershipCount, userCount);

        return userCount;
    }
}
