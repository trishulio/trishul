package io.trishul.ai.service.tool.model.service;

import io.trishul.ai.tool.model.AiTool;
import io.trishul.ai.tool.model.AiToolAccessor;
import io.trishul.ai.tool.model.BaseAiTool;
import io.trishul.ai.tool.model.UpdateAiTool;
import io.trishul.base.types.base.pojo.Identified;
import io.trishul.crud.service.BaseService;
import io.trishul.crud.service.CrudService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.model.base.exception.EntityNotFoundException;
import io.trishul.model.base.pojo.DeleteResult;
import io.trishul.repo.jpa.repository.service.RepoService;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Transactional
public class AiToolService extends BaseService
    implements CrudService<Long, AiTool, BaseAiTool<?>, UpdateAiTool<?>, AiToolAccessor<?>> {

  private final EntityMergerService<Long, AiTool, BaseAiTool<?>, UpdateAiTool<?>> entityMergerService;
  private final RepoService<Long, AiTool, AiToolAccessor<?>> repoService;

  public AiToolService(
      EntityMergerService<Long, AiTool, BaseAiTool<?>, UpdateAiTool<?>> entityMergerService,
      RepoService<Long, AiTool, AiToolAccessor<?>> repoService) {
    this.entityMergerService = entityMergerService;
    this.repoService = repoService;
  }

  @Override
  public AiTool get(Long id) {
    return this.repoService.get(id);
  }

  @Override
  public List<AiTool> getByIds(Collection<? extends Identified<Long>> idProviders) {
    return this.repoService.getByIds(idProviders);
  }

  @Override
  public List<AiTool> getByAccessorIds(Collection<? extends AiToolAccessor<?>> accessors) {
    return this.repoService.getByAccessorIds(accessors, AiToolAccessor::getTool);
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
  public List<AiTool> add(List<? extends BaseAiTool<?>> additions) {
    if (additions == null)
      return null;
    List<AiTool> entities = this.entityMergerService.getAddEntities(additions);
    return this.repoService.saveAll(entities);
  }

  @Override
  public List<AiTool> put(List<? extends UpdateAiTool<?>> updates) {
    if (updates == null)
      return null;
    List<AiTool> existing = this.repoService.getByIds(updates);
    List<AiTool> updated = this.entityMergerService.getPutEntities(existing, updates);
    return this.repoService.saveAll(updated);
  }

  @Override
  public List<AiTool> patch(List<? extends UpdateAiTool<?>> patches) {
    if (patches == null)
      return null;
    List<AiTool> existing = this.repoService.getByIds(patches);
    if (existing.size() != patches.size())
      throw new EntityNotFoundException("Entity not found");
    List<AiTool> updated = this.entityMergerService.getPatchEntities(existing, patches);
    return this.repoService.saveAll(updated);
  }
}
