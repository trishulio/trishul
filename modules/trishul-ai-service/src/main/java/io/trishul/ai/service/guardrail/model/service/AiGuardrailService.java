package io.trishul.ai.service.guardrail.model.service;

import io.trishul.ai.guardrail.model.AiGuardrail;
import io.trishul.ai.guardrail.model.AiGuardrailAccessor;
import io.trishul.ai.guardrail.model.BaseAiGuardrail;
import io.trishul.ai.guardrail.model.UpdateAiGuardrail;
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
public class AiGuardrailService extends BaseService implements
    CrudService<Long, AiGuardrail, BaseAiGuardrail<?>, UpdateAiGuardrail<?>, AiGuardrailAccessor<?>> {

  private final EntityMergerService<Long, AiGuardrail, BaseAiGuardrail<?>, UpdateAiGuardrail<?>> entityMergerService;
  private final RepoService<Long, AiGuardrail, AiGuardrailAccessor<?>> repoService;

  public AiGuardrailService(
      EntityMergerService<Long, AiGuardrail, BaseAiGuardrail<?>, UpdateAiGuardrail<?>> entityMergerService,
      RepoService<Long, AiGuardrail, AiGuardrailAccessor<?>> repoService) {
    this.entityMergerService = entityMergerService;
    this.repoService = repoService;
  }

  @Override
  public AiGuardrail get(Long id) {
    return this.repoService.get(id);
  }

  @Override
  public List<AiGuardrail> getByIds(Collection<? extends Identified<Long>> idProviders) {
    return this.repoService.getByIds(idProviders);
  }

  @Override
  public List<AiGuardrail> getByAccessorIds(
      Collection<? extends AiGuardrailAccessor<?>> accessors) {
    return this.repoService.getByAccessorIds(accessors, AiGuardrailAccessor::getGuardrail);
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
  public List<AiGuardrail> add(List<? extends BaseAiGuardrail<?>> additions) {
    if (additions == null)
      return null;
    List<AiGuardrail> entities = this.entityMergerService.getAddEntities(additions);
    return this.repoService.saveAll(entities);
  }

  @Override
  public List<AiGuardrail> put(List<? extends UpdateAiGuardrail<?>> updates) {
    if (updates == null)
      return null;
    List<AiGuardrail> existing = this.repoService.getByIds(updates);
    List<AiGuardrail> updated = this.entityMergerService.getPutEntities(existing, updates);
    return this.repoService.saveAll(updated);
  }

  @Override
  public List<AiGuardrail> patch(List<? extends UpdateAiGuardrail<?>> patches) {
    if (patches == null)
      return null;
    List<AiGuardrail> existing = this.repoService.getByIds(patches);
    if (existing.size() != patches.size())
      throw new EntityNotFoundException("Entity not found");
    List<AiGuardrail> updated = this.entityMergerService.getPatchEntities(existing, patches);
    return this.repoService.saveAll(updated);
  }
}
