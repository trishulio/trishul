package io.trishul.ai.service.agent.model.service;

import io.trishul.ai.agent.model.AiAgentConfig;
import io.trishul.ai.agent.model.AiAgentConfigAccessor;
import io.trishul.ai.agent.model.BaseAiAgentConfig;
import io.trishul.ai.agent.model.UpdateAiAgentConfig;
import io.trishul.crud.service.BaseService;
import io.trishul.crud.service.CrudService;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import io.trishul.base.types.base.pojo.Identified;
import io.trishul.model.base.exception.EntityNotFoundException;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.repo.jpa.repository.service.RepoService;
import jakarta.transaction.Transactional;

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
  public long delete(Set<Long> ids) {
    return this.repoService.delete(ids);
  }

  @Override
  public long delete(Long id) {
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
