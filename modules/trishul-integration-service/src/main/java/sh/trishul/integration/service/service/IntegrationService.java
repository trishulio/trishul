package sh.trishul.integration.service.service;

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
import sh.trishul.base.types.base.pojo.Identified;
import sh.trishul.crud.service.BaseService;
import sh.trishul.crud.service.CrudService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.integration.model.BaseIntegration;
import sh.trishul.integration.model.Integration;
import sh.trishul.integration.model.IntegrationAccessor;
import sh.trishul.integration.model.IntegrationStatus;
import sh.trishul.integration.model.IntegrationType;
import sh.trishul.integration.model.UpdateIntegration;
import sh.trishul.model.base.exception.EntityNotFoundException;
import sh.trishul.model.base.pojo.DeleteResult;
import sh.trishul.repo.jpa.query.clause.where.builder.WhereClauseBuilder;
import sh.trishul.repo.jpa.repository.service.RepoService;

@Transactional
public class IntegrationService extends BaseService implements
    CrudService<Long, Integration, BaseIntegration<?>, UpdateIntegration<?>, IntegrationAccessor<?>> {
  private static final Logger log = LoggerFactory.getLogger(IntegrationService.class);

  private final EntityMergerService<Long, Integration, BaseIntegration<?>, UpdateIntegration<?>> entityMergerService;
  private final RepoService<Long, Integration, IntegrationAccessor<?>> repoService;

  public IntegrationService(
      EntityMergerService<Long, Integration, BaseIntegration<?>, UpdateIntegration<?>> entityMergerService,
      RepoService<Long, Integration, IntegrationAccessor<?>> repoService) {
    this.entityMergerService = entityMergerService;
    this.repoService = repoService;
  }

  public Page<Integration> getIntegrations(Set<Long> ids, Set<String> names,
      Set<IntegrationType> types, Set<String> providers, Set<IntegrationStatus> statuses, int page,
      int size, SortedSet<String> sort, boolean orderAscending) {
    final Specification<Integration> spec
        = WhereClauseBuilder.builder().in(Integration.ATTR_ID, ids).in(Integration.ATTR_NAME, names)
            .in(Integration.ATTR_TYPE, types).in(Integration.ATTR_PROVIDER, providers)
            .in(Integration.ATTR_STATUS, statuses).build();

    return this.repoService.getAll(spec, sort, orderAscending, page, size);
  }

  @Override
  public Integration get(Long id) {
    return this.repoService.get(id);
  }

  @Override
  public List<Integration> getByIds(Collection<? extends Identified<Long>> idProviders) {
    return this.repoService.getByIds(idProviders);
  }

  @Override
  public List<Integration> getByAccessorIds(
      Collection<? extends IntegrationAccessor<?>> accessors) {
    return this.repoService.getByAccessorIds(accessors, IntegrationAccessor::getIntegration);
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
    return this.repoService.delete(ids);
  }

  @Override
  public DeleteResult delete(Long id) {
    return this.delete(Set.of(id));
  }

  @Override
  public List<Integration> add(final List<? extends BaseIntegration<?>> additions) {
    if (additions == null) {
      return null;
    }

    final List<Integration> entities = this.entityMergerService.getAddEntities(additions);

    List<Integration> integrations = this.repoService.saveAll(entities);

    log.info("Added integrations: {}", integrations.size());

    return integrations;
  }

  @Override
  public List<Integration> put(List<? extends UpdateIntegration<?>> updates) {
    if (updates == null) {
      return null;
    }

    final List<Integration> existing = this.repoService.getByIds(updates);
    final List<Integration> updated = this.entityMergerService.getPutEntities(existing, updates);

    return this.repoService.saveAll(updated);
  }

  @Override
  public List<Integration> patch(List<? extends UpdateIntegration<?>> patches) {
    if (patches == null) {
      return null;
    }

    final List<Integration> existing = this.repoService.getByIds(patches);

    if (existing.size() != patches.size()) {
      final Set<Long> existingIds
          = existing.stream().map(Identified::getId).collect(Collectors.toSet());
      final Set<Long> nonExistingIds = patches.stream().map(Identified::getId)
          .filter(patchId -> !existingIds.contains(patchId)).collect(Collectors.toSet());

      throw new EntityNotFoundException(
          String.format("Cannot find integrations with Ids: %s", nonExistingIds));
    }

    final List<Integration> updated = this.entityMergerService.getPatchEntities(existing, patches);

    return this.repoService.saveAll(updated);
  }

  @Override
  public Page<Integration> search(String query, String[][] fieldPaths, SortedSet<String> sort,
      boolean orderAscending, int page, int size) {
    return this.repoService.search(query, fieldPaths, sort, orderAscending, page, size);
  }
}
