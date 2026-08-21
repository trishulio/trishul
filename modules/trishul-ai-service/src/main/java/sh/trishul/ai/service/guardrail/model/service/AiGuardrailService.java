package sh.trishul.ai.service.guardrail.model.service;

import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import sh.trishul.ai.guardrail.model.AiGuardrail;
import sh.trishul.ai.guardrail.model.AiGuardrailAccessor;
import sh.trishul.ai.guardrail.model.BaseAiGuardrail;
import sh.trishul.ai.guardrail.model.UpdateAiGuardrail;
import sh.trishul.base.types.base.pojo.Identified;
import sh.trishul.crud.service.BaseService;
import sh.trishul.crud.service.CrudService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.model.base.exception.EntityNotFoundException;
import sh.trishul.model.base.pojo.DeleteResult;
import sh.trishul.repo.jpa.query.clause.where.builder.WhereClauseBuilder;
import sh.trishul.repo.jpa.repository.service.RepoService;

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

  public Page<AiGuardrail> getGuardrails(Set<Long> ids, Set<String> names, int page, int size,
      SortedSet<String> sort, boolean orderAscending) {
    final Specification<AiGuardrail> spec = WhereClauseBuilder.builder()
        .in(AiGuardrail.ATTR_ID, ids).in(AiGuardrail.ATTR_NAME, names).build();

    return this.repoService.getAll(spec, sort, orderAscending, page, size);
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
  public DeleteResult delete(Set<Long> ids) {
    return this.repoService.delete(ids);
  }

  @Override
  public DeleteResult delete(Long id) {
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

  @Override
  public Page<AiGuardrail> search(String query, String[][] fieldPaths, SortedSet<String> sort,
      boolean orderAscending, int page, int size) {
    return this.repoService.search(query, fieldPaths, sort, orderAscending, page, size);
  }
}
