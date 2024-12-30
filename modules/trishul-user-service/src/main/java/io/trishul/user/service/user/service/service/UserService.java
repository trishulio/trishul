package io.trishul.user.service.user.service.service;

import io.trishul.base.types.base.pojo.Identified;
import io.trishul.crud.service.BaseService;
import io.trishul.crud.service.CrudService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.iaas.user.service.TenantIaasUserService;
import io.trishul.model.base.exception.EntityNotFoundException;
import io.trishul.repo.jpa.query.clause.where.builder.WhereClauseBuilder;
import io.trishul.repo.jpa.repository.service.RepoService;
import io.trishul.user.model.BaseUser;
import io.trishul.user.model.UpdateUser;
import io.trishul.user.model.User;
import io.trishul.user.model.UserAccessor;
import io.trishul.user.role.model.UserRole;
import io.trishul.user.service.user.service.repository.UserRepository;
import io.trishul.user.status.UserStatus;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

@Transactional
public class UserService extends BaseService
    implements CrudService<Long, User, BaseUser<?>, UpdateUser<?>, UserAccessor<?>> {
  @SuppressWarnings("unused")
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
            .in(User.ATTR_USER_NAME, userNames).in(User.ATTR_DISPLAY_NAME, displayNames)
            .in(User.ATTR_EMAIL, emails).in(User.ATTR_PHONE_NUMBER, phoneNumbers)
            .in(new String[] {User.ATTR_STATUS, UserStatus.ATTR_ID}, statusIds)
            .in(new String[] {User.ATTR_SALUTATION, UserStatus.ATTR_ID}, salutationIds)
            .in(new String[] {User.ATTR_ROLES, UserRole.ATTR_ID}, roles).build();

    return this.repoService.getAll(spec, sort, orderAscending, page, size);
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
    return this.repoService.getByAccessorIds(accessors, accessor -> accessor.getUser());
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
  public long delete(Set<Long> ids) {
    List<User> users = this.userRepo.findAllById(ids);
    long deleteCount = this.repoService.delete(ids);
    this.iaasService.delete(users);

    return deleteCount;
  }

  @Override
  public long delete(Long id) {
    return this.delete(Set.of(id));
  }

  @Override
  public List<User> add(final List<? extends BaseUser<?>> additions) {
    if (additions == null) {
      return null;
    }

    final List<User> entities = this.entityMergerService.getAddEntities(additions);

    List<User> users = this.repoService.saveAll(entities);

    this.iaasService.put(users);

    return users;
  }

  @Override
  public List<User> put(List<? extends UpdateUser<?>> updates) {
    if (updates == null) {
      return null;
    }

    final List<User> existing = this.repoService.getByIds(updates);
    final List<User> updated = this.entityMergerService.getPutEntities(existing, updates);

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
          = existing.stream().map(user -> user.getId()).collect(Collectors.toSet());
      final Set<Long> nonExistingIds = patches.stream().map(patch -> patch.getId())
          .filter(patchId -> !existingIds.contains(patchId)).collect(Collectors.toSet());

      throw new EntityNotFoundException(
          String.format("Cannot find users with Ids: %s", nonExistingIds));
    }

    final List<User> updated = this.entityMergerService.getPatchEntities(existing, patches);

    return this.repoService.saveAll(updated);
  }
}
