package sh.trishul.ai.service.agent.model.service;

import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import sh.trishul.ai.agent.model.AiAgentConfig;
import sh.trishul.ai.agent.model.AiAgentConfigAccessor;
import sh.trishul.ai.agent.model.BaseAiAgentConfig;
import sh.trishul.ai.agent.model.UpdateAiAgentConfig;
import sh.trishul.base.types.base.pojo.Identified;
import sh.trishul.crud.service.BaseService;
import sh.trishul.crud.service.CrudService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.model.base.exception.EntityNotFoundException;
import sh.trishul.model.base.pojo.DeleteResult;
import sh.trishul.repo.jpa.query.clause.where.builder.WhereClauseBuilder;
import sh.trishul.repo.jpa.repository.service.RepoService;

@Transactional
public class AiAgentConfigService extends BaseService implements
    CrudService<Long, AiAgentConfig, BaseAiAgentConfig<?>, UpdateAiAgentConfig<?>, AiAgentConfigAccessor<?>> {

  private final EntityMergerService<Long, AiAgentConfig, BaseAiAgentConfig<?>, UpdateAiAgentConfig<?>> entityMergerService;
  private final RepoService<Long, AiAgentConfig, AiAgentConfigAccessor<?>> repoService;

  public AiAgentConfigService(
      EntityMergerService<Long, AiAgentConfig, BaseAiAgentConfig<?>, UpdateAiAgentConfig<?>> entityMergerService,
      RepoService<Long, AiAgentConfig, AiAgentConfigAccessor<?>> repoService) {
    this.entityMergerService = entityMergerService;
    this.repoService = repoService;
  }

  public Page<AiAgentConfig> getAgentConfigs(Set<Long> ids, Set<String> names, Boolean isActive,
      int page, int size, SortedSet<String> sort, boolean orderAscending) {
    final Specification<AiAgentConfig> spec
        = WhereClauseBuilder.builder().in(AiAgentConfig.ATTR_ID, ids)
            .in(AiAgentConfig.ATTR_NAME, names).is(AiAgentConfig.ATTR_IS_ACTIVE, isActive).build();

    return this.repoService.getAll(spec, sort, orderAscending, page, size);
  }

  @Override
  public AiAgentConfig get(Long id) {
    return this.repoService.get(id);
  }

  @Override
  public List<AiAgentConfig> getByIds(Collection<? extends Identified<Long>> idProviders) {
    return this.repoService.getByIds(idProviders);
  }

  @Override
  public List<AiAgentConfig> getByAccessorIds(
      Collection<? extends AiAgentConfigAccessor<?>> accessors) {
    return this.repoService.getByAccessorIds(accessors, AiAgentConfigAccessor::getAgentConfig);
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
    return this.repoService.delete(id);
  }

  @Override
  public List<AiAgentConfig> add(List<? extends BaseAiAgentConfig<?>> additions) {
    if (additions == null)
      return null;
    List<AiAgentConfig> entities = this.entityMergerService.getAddEntities(additions);
    return this.repoService.saveAll(entities);
  }

  @Override
  public List<AiAgentConfig> put(List<? extends UpdateAiAgentConfig<?>> updates) {
    if (updates == null)
      return null;
    List<AiAgentConfig> existing = this.repoService.getByIds(updates);
    List<AiAgentConfig> updated = this.entityMergerService.getPutEntities(existing, updates);
    return this.repoService.saveAll(updated);
  }

  @Override
  public List<AiAgentConfig> patch(List<? extends UpdateAiAgentConfig<?>> patches) {
    if (patches == null)
      return null;
    List<AiAgentConfig> existing = this.repoService.getByIds(patches);
    if (existing.size() != patches.size())
      throw new EntityNotFoundException("Entity not found");
    List<AiAgentConfig> updated = this.entityMergerService.getPatchEntities(existing, patches);
    return this.repoService.saveAll(updated);
  }
}
