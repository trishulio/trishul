package io.trishul.ai.service.session.model.service;

import io.trishul.ai.session.model.AiChatSession;
import io.trishul.ai.session.model.AiChatSessionAccessor;
import io.trishul.ai.session.model.BaseAiChatSession;
import io.trishul.ai.session.model.UpdateAiChatSession;
import io.trishul.crud.service.BaseService;
import io.trishul.crud.service.CrudService;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import io.trishul.base.types.base.pojo.Identified;
import io.trishul.model.base.exception.EntityNotFoundException;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.repo.jpa.query.clause.where.builder.WhereClauseBuilder;
import io.trishul.repo.jpa.repository.service.RepoService;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;

@Transactional
public class AiChatSessionService extends BaseService implements
    CrudService<Long, AiChatSession, BaseAiChatSession<?>, UpdateAiChatSession<?>, AiChatSessionAccessor<?>> {

  private final EntityMergerService<Long, AiChatSession, BaseAiChatSession<?>, UpdateAiChatSession<?>> entityMergerService;
  private final RepoService<Long, AiChatSession, AiChatSessionAccessor<?>> repoService;

  public AiChatSessionService(
      EntityMergerService<Long, AiChatSession, BaseAiChatSession<?>, UpdateAiChatSession<?>> entityMergerService,
      RepoService<Long, AiChatSession, AiChatSessionAccessor<?>> repoService) {
    this.entityMergerService = entityMergerService;
    this.repoService = repoService;
  }

  @Override
  public AiChatSession get(Long id) {
    return this.repoService.get(id);
  }

  public AiChatSession getBySessionKey(String sessionKey) {
    final Specification<AiChatSession> spec = WhereClauseBuilder.builder()
        .is(BaseAiChatSession.ATTR_SESSION_KEY, sessionKey).build();

    final List<AiChatSession> sessions = this.repoService.getAll(spec);

    if (sessions.isEmpty()) {
      throw new EntityNotFoundException("Session not found for key: " + sessionKey);
    }

    return sessions.get(0);
  }

  @Override
  public List<AiChatSession> getByIds(Collection<? extends Identified<Long>> idProviders) {
    return this.repoService.getByIds(idProviders);
  }

  @Override
  public List<AiChatSession> getByAccessorIds(
      Collection<? extends AiChatSessionAccessor<?>> accessors) {
    return this.repoService.getByAccessorIds(accessors, AiChatSessionAccessor::getChatSession);
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
  public List<AiChatSession> add(List<? extends BaseAiChatSession<?>> additions) {
    if (additions == null)
      return null;
    List<AiChatSession> entities = this.entityMergerService.getAddEntities(additions);
    return this.repoService.saveAll(entities);
  }

  @Override
  public List<AiChatSession> put(List<? extends UpdateAiChatSession<?>> updates) {
    if (updates == null)
      return null;
    List<AiChatSession> existing = this.repoService.getByIds(updates);
    List<AiChatSession> updated = this.entityMergerService.getPutEntities(existing, updates);
    return this.repoService.saveAll(updated);
  }

  @Override
  public List<AiChatSession> patch(List<? extends UpdateAiChatSession<?>> patches) {
    if (patches == null)
      return null;
    List<AiChatSession> existing = this.repoService.getByIds(patches);
    if (existing.size() != patches.size())
      throw new EntityNotFoundException("Entity not found");
    List<AiChatSession> updated = this.entityMergerService.getPatchEntities(existing, patches);
    return this.repoService.saveAll(updated);
  }
}
