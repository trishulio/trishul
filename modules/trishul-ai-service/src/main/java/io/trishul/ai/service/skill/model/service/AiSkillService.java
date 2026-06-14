package io.trishul.ai.service.skill.model.service;

import io.trishul.ai.skill.model.AiSkill;
import io.trishul.ai.skill.model.AiSkillAccessor;
import io.trishul.ai.skill.model.BaseAiSkill;
import io.trishul.ai.skill.model.UpdateAiSkill;
import io.trishul.base.types.base.pojo.Identified;
import io.trishul.crud.service.BaseService;
import io.trishul.crud.service.CrudService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.model.base.exception.EntityNotFoundException;
import io.trishul.repo.jpa.repository.service.RepoService;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Transactional
public class AiSkillService extends BaseService
    implements CrudService<Long, AiSkill, BaseAiSkill<?>, UpdateAiSkill<?>, AiSkillAccessor<?>> {

  private final EntityMergerService<Long, AiSkill, BaseAiSkill<?>, UpdateAiSkill<?>> entityMergerService;
  private final RepoService<Long, AiSkill, AiSkillAccessor<?>> repoService;

  public AiSkillService(
      EntityMergerService<Long, AiSkill, BaseAiSkill<?>, UpdateAiSkill<?>> entityMergerService,
      RepoService<Long, AiSkill, AiSkillAccessor<?>> repoService) {
    this.entityMergerService = entityMergerService;
    this.repoService = repoService;
  }

  @Override
  public AiSkill get(Long id) {
    return this.repoService.get(id);
  }

  @Override
  public List<AiSkill> getByIds(Collection<? extends Identified<Long>> idProviders) {
    return this.repoService.getByIds(idProviders);
  }

  @Override
  public List<AiSkill> getByAccessorIds(Collection<? extends AiSkillAccessor<?>> accessors) {
    return this.repoService.getByAccessorIds(accessors, AiSkillAccessor::getSkill);
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
  public List<AiSkill> add(List<? extends BaseAiSkill<?>> additions) {
    if (additions == null)
      return null;
    List<AiSkill> entities = this.entityMergerService.getAddEntities(additions);
    return this.repoService.saveAll(entities);
  }

  @Override
  public List<AiSkill> put(List<? extends UpdateAiSkill<?>> updates) {
    if (updates == null)
      return null;
    List<AiSkill> existing = this.repoService.getByIds(updates);
    List<AiSkill> updated = this.entityMergerService.getPutEntities(existing, updates);
    return this.repoService.saveAll(updated);
  }

  @Override
  public List<AiSkill> patch(List<? extends UpdateAiSkill<?>> patches) {
    if (patches == null)
      return null;
    List<AiSkill> existing = this.repoService.getByIds(patches);
    if (existing.size() != patches.size())
      throw new EntityNotFoundException("Entity not found");
    List<AiSkill> updated = this.entityMergerService.getPatchEntities(existing, patches);
    return this.repoService.saveAll(updated);
  }
}
