package io.trishul.ai.service.memory.model.service;

import io.trishul.ai.memory.model.AiChatMemoryConfig;
import io.trishul.ai.memory.model.AiChatMemoryConfigAccessor;
import io.trishul.ai.memory.model.BaseAiChatMemoryConfig;
import io.trishul.ai.memory.model.UpdateAiChatMemoryConfig;
import io.trishul.base.types.base.pojo.Identified;
import io.trishul.crud.service.BaseService;
import io.trishul.crud.service.CrudService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.model.base.exception.EntityNotFoundException;
import io.trishul.model.base.pojo.DeleteResult;
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
public class AiChatMemoryConfigService extends BaseService implements
    CrudService<Long, AiChatMemoryConfig, BaseAiChatMemoryConfig<?>, UpdateAiChatMemoryConfig<?>, AiChatMemoryConfigAccessor<?>> {

  private final EntityMergerService<Long, AiChatMemoryConfig, BaseAiChatMemoryConfig<?>, UpdateAiChatMemoryConfig<?>> entityMergerService;
  private final RepoService<Long, AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> repoService;

  public AiChatMemoryConfigService(
      EntityMergerService<Long, AiChatMemoryConfig, BaseAiChatMemoryConfig<?>, UpdateAiChatMemoryConfig<?>> entityMergerService,
      RepoService<Long, AiChatMemoryConfig, AiChatMemoryConfigAccessor<?>> repoService) {
    this.entityMergerService = entityMergerService;
    this.repoService = repoService;
  }

  public Page<AiChatMemoryConfig> getChatMemoryConfigs(Set<Long> ids, Set<String> names, int page,
      int size, SortedSet<String> sort, boolean orderAscending) {
    final Specification<AiChatMemoryConfig> spec = WhereClauseBuilder.builder()
        .in(AiChatMemoryConfig.ATTR_ID, ids).in(AiChatMemoryConfig.ATTR_NAME, names).build();

    return this.repoService.getAll(spec, sort, orderAscending, page, size);
  }

  @Override
  public AiChatMemoryConfig get(Long id) {
    return this.repoService.get(id);
  }

  @Override
  public List<AiChatMemoryConfig> getByIds(Collection<? extends Identified<Long>> idProviders) {
    return this.repoService.getByIds(idProviders);
  }

  @Override
  public List<AiChatMemoryConfig> getByAccessorIds(
      Collection<? extends AiChatMemoryConfigAccessor<?>> accessors) {
    return this.repoService.getByAccessorIds(accessors,
        AiChatMemoryConfigAccessor::getChatMemoryConfig);
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
  public List<AiChatMemoryConfig> add(List<? extends BaseAiChatMemoryConfig<?>> additions) {
    if (additions == null)
      return null;
    List<AiChatMemoryConfig> entities = this.entityMergerService.getAddEntities(additions);
    return this.repoService.saveAll(entities);
  }

  @Override
  public List<AiChatMemoryConfig> put(List<? extends UpdateAiChatMemoryConfig<?>> updates) {
    if (updates == null)
      return null;
    List<AiChatMemoryConfig> existing = this.repoService.getByIds(updates);
    List<AiChatMemoryConfig> updated = this.entityMergerService.getPutEntities(existing, updates);
    return this.repoService.saveAll(updated);
  }

  @Override
  public List<AiChatMemoryConfig> patch(List<? extends UpdateAiChatMemoryConfig<?>> patches) {
    if (patches == null)
      return null;
    List<AiChatMemoryConfig> existing = this.repoService.getByIds(patches);
    if (existing.size() != patches.size())
      throw new EntityNotFoundException("Entity not found");
    List<AiChatMemoryConfig> updated = this.entityMergerService.getPatchEntities(existing, patches);
    return this.repoService.saveAll(updated);
  }
}
