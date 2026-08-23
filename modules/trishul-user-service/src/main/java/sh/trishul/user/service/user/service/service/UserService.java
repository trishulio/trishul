package sh.trishul.user.service.user.service.service;

import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import sh.trishul.base.types.base.pojo.Identified;
import sh.trishul.crud.service.BaseService;
import sh.trishul.crud.service.CrudService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.iaas.user.model.IaasUser;
import sh.trishul.iaas.user.model.IaasUserTenantMembership;
import sh.trishul.iaas.user.service.TenantIaasUserService;
import sh.trishul.model.base.exception.EntityNotFoundException;
import sh.trishul.model.base.pojo.DeleteResult;
import sh.trishul.repo.jpa.query.clause.where.builder.WhereClauseBuilder;
import sh.trishul.repo.jpa.repository.service.RepoService;
import sh.trishul.user.model.BaseUser;
import sh.trishul.user.model.UpdateUser;
import sh.trishul.user.model.User;
import sh.trishul.user.model.UserAccessor;
import sh.trishul.user.role.model.UserRole;
import sh.trishul.user.salutation.model.UserSalutation;
import sh.trishul.user.service.user.service.repository.UserRepository;
import sh.trishul.user.status.UserStatus;

@Transactional
public class UserService extends BaseService
    implements CrudService<Long, User, BaseUser<?>, UpdateUser<?>, UserAccessor<?>> {
  private static final Logger log = LoggerFactory.getLogger(UserService.class);

  private final EntityMergerService<Long, User, BaseUser<?>, UpdateUser<?>> entityMergerService;
  private final RepoService<Long, User, UserAccessor<?>> repoService;
  private final UserRepository userRepo;
  private final TenantIaasUserService iaasService;

  public UserService(
      EntityMergerService<Long, User, BaseUser<?>, UpdateUser<?>> entityMergerService,
      RepoService<Long, User, UserAccessor<?>> repoService, UserRepository userRepo,
      TenantIaasUserService iaasService) {
    this.entityMergerService = entityMergerService;
    this.repoService = repoService;
    this.iaasService = iaasService;
    this.userRepo = userRepo;
  }

  public Page<User> getUsers(Set<Long> ids, Set<Long> excludeIds, Set<String> userNames,
      Set<String> displayNames, Set<String> emails, Set<String> phoneNumbers, Set<Long> statusIds,
      Set<Long> salutationIds, Set<String> roles, int page, int size, SortedSet<String> sort,
      boolean orderAscending) {
    final Specification<User> spec
        = WhereClauseBuilder.builder().in(User.ATTR_ID, ids).not().in(User.ATTR_ID, excludeIds)
            .ilike(User.ATTR_USER_NAME, userNames).ilike(User.ATTR_DISPLAY_NAME, displayNames)
            .ilike(User.ATTR_EMAIL, emails).ilike(User.ATTR_PHONE_NUMBER, phoneNumbers)
            .in(new String[] {User.ATTR_STATUS, UserStatus.ATTR_ID}, statusIds)
            .in(new String[] {User.ATTR_SALUTATION, UserSalutation.ATTR_ID}, salutationIds)
            .in(new String[] {User.ATTR_ROLES, UserRole.ATTR_ID}, roles).build();

    return this.repoService.getAll(spec, sort, orderAscending, page, size);
  }

  private static final String[][] SEARCH_FIELDS = {{"firstName"}, {"lastName"}, {"email"}};

  @Override
  public Page<User> search(String query, SortedSet<String> sort, boolean orderAscending, int page,
      int size) {
    return this.repoService.search(query, SEARCH_FIELDS, sort, orderAscending, page, size);
  }

  @Override
  public User get(Long id) {
    return this.repoService.get(id);
  }

  @Override
  public List<User> getByIds(Collection<? extends Identified<Long>> idProviders) {
    return this.repoService.getByIds(idProviders);
  }

  @Override
  public List<User> getByAccessorIds(Collection<? extends UserAccessor<?>> accessors) {
    return this.repoService.getByAccessorIds(accessors, UserAccessor::getUser);
  }

  @Override
  public boolean exists(Set<Long> ids) {
    return this.repoService.exists(ids);
  }

  @Override
  public boolean exist(Long id) {
    return this.repoService.exists(id);
  }

  @Override
  public DeleteResult delete(Set<Long> ids) {
    List<User> users = this.userRepo.findAllById(ids);
    DeleteResult deleteCount = this.repoService.delete(ids);
    long iaasUserDeleteResult = this.iaasService.delete(users);
    log.info("Deleted users: {}", iaasUserDeleteResult);

    return deleteCount;
  }

  @Override
  public DeleteResult delete(Long id) {
    return this.delete(Set.of(id));
  }

  @Override
  public List<User> add(final List<? extends BaseUser<?>> additions) {
    if (additions == null) {
      return null;
    }

    final List<User> entities = this.entityMergerService.getAddEntities(additions);

    List<IaasUserTenantMembership> updatedIaasUserMemberships = this.iaasService.put(entities);

    // Create a map of email to IaasUser for efficient lookup
    Map<String, IaasUser> iaasUserMap = updatedIaasUserMemberships.stream()
        .map(IaasUserTenantMembership::getUser).filter(Objects::nonNull)
        .collect(Collectors.toMap(IaasUser::getId, iaasUser -> iaasUser));

    // Update users with IaasUsername
    entities.forEach(user -> {
      IaasUser iaasUser = iaasUserMap.get(user.getEmail());
      if (iaasUser != null) {
        user.setIaasUsername(iaasUser.getUserName());
      }
    });

    List<User> users = this.repoService.saveAll(entities);

    log.info("Added users: {}", users.size());

    return users;
  }

  @Override
  public List<User> put(List<? extends UpdateUser<?>> updates) {
    if (updates == null) {
      return null;
    }

    final List<User> existing = this.repoService.getByIds(updates);
    final List<User> updated = this.entityMergerService.getPutEntities(existing, updates);

    List<IaasUserTenantMembership> updatedIaasUserMemberships = this.iaasService.put(updated);

    Map<String, IaasUser> iaasUserMap = updatedIaasUserMemberships.stream()
        .map(IaasUserTenantMembership::getUser).filter(Objects::nonNull)
        .collect(Collectors.toMap(IaasUser::getId, iaasUser -> iaasUser));

    updated.forEach(user -> {
      IaasUser iaasUser = iaasUserMap.get(user.getEmail());
      if (iaasUser != null) {
        user.setIaasUsername(iaasUser.getUserName());
      }
    });

    List<User> users = this.repoService.saveAll(updated);

    return users;
  }

  @Override
  public List<User> patch(List<? extends UpdateUser<?>> patches) {
    if (patches == null) {
      return null;
    }

    final List<User> existing = this.repoService.getByIds(patches);

    if (existing.size() != patches.size()) {
      final Set<Long> existingIds
          = existing.stream().map(Identified::getId).collect(Collectors.toSet());
      final Set<Long> nonExistingIds = patches.stream().map(Identified::getId)
          .filter(patchId -> !existingIds.contains(patchId)).collect(Collectors.toSet());

      throw new EntityNotFoundException(
          String.format("Cannot find users with Ids: %s", nonExistingIds));
    }

    final List<User> updated = this.entityMergerService.getPatchEntities(existing, patches);

    return this.repoService.saveAll(updated);
  }
}
