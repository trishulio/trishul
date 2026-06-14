package io.trishul.ai.service.speech.model.service;

import io.trishul.ai.speech.model.AiSpeechConfig;
import io.trishul.ai.speech.model.AiSpeechConfigAccessor;
import io.trishul.ai.speech.model.BaseAiSpeechConfig;
import io.trishul.ai.speech.model.UpdateAiSpeechConfig;
import io.trishul.base.types.base.pojo.Identified;
import io.trishul.crud.service.BaseService;
import io.trishul.crud.service.CrudService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.model.base.exception.EntityNotFoundException;
import io.trishul.repo.jpa.query.clause.where.builder.WhereClauseBuilder;
import io.trishul.repo.jpa.repository.service.RepoService;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

@Transactional
public class AiSpeechConfigService extends BaseService implements
    CrudService<Long, AiSpeechConfig, BaseAiSpeechConfig<?>, UpdateAiSpeechConfig<?>, AiSpeechConfigAccessor<?>> {

  private final EntityMergerService<Long, AiSpeechConfig, BaseAiSpeechConfig<?>, UpdateAiSpeechConfig<?>> entityMergerService;
  private final RepoService<Long, AiSpeechConfig, AiSpeechConfigAccessor<?>> repoService;

  public AiSpeechConfigService(
      EntityMergerService<Long, AiSpeechConfig, BaseAiSpeechConfig<?>, UpdateAiSpeechConfig<?>> entityMergerService,
      RepoService<Long, AiSpeechConfig, AiSpeechConfigAccessor<?>> repoService) {
    this.entityMergerService = entityMergerService;
    this.repoService = repoService;
  }

  public Page<AiSpeechConfig> getSpeechConfigs(Set<Long> ids, Set<String> names,
      Set<String> providers, Boolean isDefault, int page, int size, SortedSet<String> sort,
      boolean orderAscending) {
    final Specification<AiSpeechConfig> spec
        = WhereClauseBuilder.builder().in(AiSpeechConfig.ATTR_ID, ids)
            .in(AiSpeechConfig.ATTR_NAME, names).in(AiSpeechConfig.ATTR_PROVIDER, providers)
            .is(AiSpeechConfig.ATTR_IS_DEFAULT, isDefault).build();

    return this.repoService.getAll(spec, sort, orderAscending, page, size);
  }

  @Override
  public AiSpeechConfig get(Long id) {
    return this.repoService.get(id);
  }

  @Override
  public List<AiSpeechConfig> getByIds(Collection<? extends Identified<Long>> idProviders) {
    return this.repoService.getByIds(idProviders);
  }

  @Override
  public List<AiSpeechConfig> getByAccessorIds(
      Collection<? extends AiSpeechConfigAccessor<?>> accessors) {
    return this.repoService.getByAccessorIds(accessors, AiSpeechConfigAccessor::getSpeechConfig);
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
  public List<AiSpeechConfig> add(List<? extends BaseAiSpeechConfig<?>> additions) {
    if (additions == null)
      return null;
    List<AiSpeechConfig> entities = this.entityMergerService.getAddEntities(additions);
    return this.repoService.saveAll(entities);
  }

  @Override
  public List<AiSpeechConfig> put(List<? extends UpdateAiSpeechConfig<?>> updates) {
    if (updates == null)
      return null;
    List<AiSpeechConfig> existing = this.repoService.getByIds(updates);
    List<AiSpeechConfig> updated = this.entityMergerService.getPutEntities(existing, updates);
    return this.repoService.saveAll(updated);
  }

  @Override
  public List<AiSpeechConfig> patch(List<? extends UpdateAiSpeechConfig<?>> patches) {
    if (patches == null)
      return null;
    List<AiSpeechConfig> existing = this.repoService.getByIds(patches);
    if (existing.size() != patches.size())
      throw new EntityNotFoundException("Entity not found");
    List<AiSpeechConfig> updated = this.entityMergerService.getPatchEntities(existing, patches);
    return this.repoService.saveAll(updated);
  }
}
