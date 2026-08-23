package sh.trishul.ai.service.chat.model.service;

import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import sh.trishul.ai.chat.model.AiChatModelConfig;
import sh.trishul.ai.chat.model.AiChatModelConfigAccessor;
import sh.trishul.ai.chat.model.BaseAiChatModelConfig;
import sh.trishul.ai.chat.model.UpdateAiChatModelConfig;
import sh.trishul.base.types.base.pojo.Identified;
import sh.trishul.crud.service.BaseService;
import sh.trishul.crud.service.CrudService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.model.base.exception.EntityNotFoundException;
import sh.trishul.model.base.pojo.DeleteResult;
import sh.trishul.repo.jpa.query.clause.where.builder.WhereClauseBuilder;
import sh.trishul.repo.jpa.repository.service.RepoService;

@Transactional
public class AiChatModelConfigService extends BaseService implements
    CrudService<Long, AiChatModelConfig, BaseAiChatModelConfig<?>, UpdateAiChatModelConfig<?>, AiChatModelConfigAccessor<?>> {

  private final EntityMergerService<Long, AiChatModelConfig, BaseAiChatModelConfig<?>, UpdateAiChatModelConfig<?>> entityMergerService;
  private final RepoService<Long, AiChatModelConfig, AiChatModelConfigAccessor<?>> repoService;

  public AiChatModelConfigService(
      EntityMergerService<Long, AiChatModelConfig, BaseAiChatModelConfig<?>, UpdateAiChatModelConfig<?>> entityMergerService,
      RepoService<Long, AiChatModelConfig, AiChatModelConfigAccessor<?>> repoService) {
    this.entityMergerService = entityMergerService;
    this.repoService = repoService;
  }

  public Page<AiChatModelConfig> getChatModelConfigs(Set<Long> ids, Set<String> names,
      Set<String> providers, Boolean isDefault, int page, int size, SortedSet<String> sort,
      boolean orderAscending) {
    final Specification<AiChatModelConfig> spec
        = WhereClauseBuilder.builder().in(AiChatModelConfig.ATTR_ID, ids)
            .in(AiChatModelConfig.ATTR_NAME, names).in(AiChatModelConfig.ATTR_PROVIDER, providers)
            .is(AiChatModelConfig.ATTR_IS_DEFAULT, isDefault).build();

    return this.repoService.getAll(spec, sort, orderAscending, page, size);
  }

  @Override
  public AiChatModelConfig get(Long id) {
    return this.repoService.get(id);
  }

  @Override
  public List<AiChatModelConfig> getByIds(Collection<? extends Identified<Long>> idProviders) {
    return this.repoService.getByIds(idProviders);
  }

  @Override
  public List<AiChatModelConfig> getByAccessorIds(
      Collection<? extends AiChatModelConfigAccessor<?>> accessors) {
    return this.repoService.getByAccessorIds(accessors,
        AiChatModelConfigAccessor::getChatModelConfig);
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
  public List<AiChatModelConfig> add(List<? extends BaseAiChatModelConfig<?>> additions) {
    if (additions == null)
      return null;
    List<AiChatModelConfig> entities = this.entityMergerService.getAddEntities(additions);
    return this.repoService.saveAll(entities);
  }

  @Override
  public List<AiChatModelConfig> put(List<? extends UpdateAiChatModelConfig<?>> updates) {
    if (updates == null)
      return null;
    List<AiChatModelConfig> existing = this.repoService.getByIds(updates);
    List<AiChatModelConfig> updated = this.entityMergerService.getPutEntities(existing, updates);
    return this.repoService.saveAll(updated);
  }

  @Override
  public List<AiChatModelConfig> patch(List<? extends UpdateAiChatModelConfig<?>> patches) {
    if (patches == null)
      return null;
    List<AiChatModelConfig> existing = this.repoService.getByIds(patches);
    if (existing.size() != patches.size())
      throw new EntityNotFoundException("Entity not found");
    List<AiChatModelConfig> updated = this.entityMergerService.getPatchEntities(existing, patches);
    return this.repoService.saveAll(updated);
  }

  private static final String[][] SEARCH_FIELDS = {{"name"}, {"provider"}};

  @Override
  public Page<AiChatModelConfig> search(String query, SortedSet<String> sort, boolean orderAscending,
      int page, int size) {
    return this.repoService.search(query, SEARCH_FIELDS, sort, orderAscending, page, size);
  }
}
