package sh.trishul.integration.communication.service.service;

import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import sh.trishul.base.types.base.pojo.Identified;
import sh.trishul.communication.model.message.Message;
import sh.trishul.communication.service.message.CommunicationMessageService;
import sh.trishul.crud.service.BaseService;
import sh.trishul.crud.service.CrudService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.integration.communication.model.BaseIntegrationCommunicationConfig;
import sh.trishul.integration.communication.model.IntegrationCommunicationConfig;
import sh.trishul.integration.communication.model.IntegrationCommunicationConfigAccessor;
import sh.trishul.integration.communication.model.UpdateIntegrationCommunicationConfig;
import sh.trishul.model.base.exception.EntityNotFoundException;
import sh.trishul.model.base.pojo.DeleteResult;
import sh.trishul.repo.jpa.repository.service.RepoService;

@Transactional
public class IntegrationCommunicationService extends BaseService implements
    CrudService<Long, IntegrationCommunicationConfig, BaseIntegrationCommunicationConfig<?>, UpdateIntegrationCommunicationConfig<?>, IntegrationCommunicationConfigAccessor<?>> {
  private static final Logger log = LoggerFactory.getLogger(IntegrationCommunicationService.class);

  private final EntityMergerService<Long, IntegrationCommunicationConfig, BaseIntegrationCommunicationConfig<?>, UpdateIntegrationCommunicationConfig<?>> entityMergerService;
  private final RepoService<Long, IntegrationCommunicationConfig, IntegrationCommunicationConfigAccessor<?>> repoService;
  private final CommunicationMessageService communicationMessageService;

  public IntegrationCommunicationService(
      EntityMergerService<Long, IntegrationCommunicationConfig, BaseIntegrationCommunicationConfig<?>, UpdateIntegrationCommunicationConfig<?>> entityMergerService,
      RepoService<Long, IntegrationCommunicationConfig, IntegrationCommunicationConfigAccessor<?>> repoService,
      CommunicationMessageService communicationMessageService) {
    this.entityMergerService = entityMergerService;
    this.repoService = repoService;
    this.communicationMessageService = communicationMessageService;
  }

  @Override
  public IntegrationCommunicationConfig get(Long id) {
    return this.repoService.get(id);
  }

  @Override
  public List<IntegrationCommunicationConfig> getByIds(
      Collection<? extends Identified<Long>> idProviders) {
    return this.repoService.getByIds(idProviders);
  }

  @Override
  public List<IntegrationCommunicationConfig> getByAccessorIds(
      Collection<? extends IntegrationCommunicationConfigAccessor<?>> accessors) {
    return this.repoService.getByAccessorIds(accessors,
        IntegrationCommunicationConfigAccessor::getIntegrationCommunicationConfig);
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
    return this.delete(Set.of(id));
  }

  @Override
  public List<IntegrationCommunicationConfig> add(
      final List<? extends BaseIntegrationCommunicationConfig<?>> additions) {
    if (additions == null) {
      return null;
    }

    final List<IntegrationCommunicationConfig> entities
        = this.entityMergerService.getAddEntities(additions);

    List<IntegrationCommunicationConfig> configs = this.repoService.saveAll(entities);

    log.info("Added integration communication configs: {}", configs.size());

    return configs;
  }

  @Override
  public List<IntegrationCommunicationConfig> put(
      List<? extends UpdateIntegrationCommunicationConfig<?>> updates) {
    if (updates == null) {
      return null;
    }

    final List<IntegrationCommunicationConfig> existing = this.repoService.getByIds(updates);
    final List<IntegrationCommunicationConfig> updated
        = this.entityMergerService.getPutEntities(existing, updates);

    return this.repoService.saveAll(updated);
  }

  @Override
  public List<IntegrationCommunicationConfig> patch(
      List<? extends UpdateIntegrationCommunicationConfig<?>> patches) {
    if (patches == null) {
      return null;
    }

    final List<IntegrationCommunicationConfig> existing = this.repoService.getByIds(patches);

    if (existing.size() != patches.size()) {
      final Set<Long> existingIds
          = existing.stream().map(Identified::getId).collect(Collectors.toSet());
      final Set<Long> nonExistingIds = patches.stream().map(Identified::getId)
          .filter(patchId -> !existingIds.contains(patchId)).collect(Collectors.toSet());

      throw new EntityNotFoundException(String
          .format("Cannot find integration communication configs with Ids: %s", nonExistingIds));
    }

    final List<IntegrationCommunicationConfig> updated
        = this.entityMergerService.getPatchEntities(existing, patches);

    return this.repoService.saveAll(updated);
  }

  /**
   * Send a message via an integration's communication configuration.
   *
   * @param integrationConfigId the integration communication config ID
   * @param to the recipient address
   * @param body the message body
   * @return the sent message
   */
  public Message sendMessage(Long integrationConfigId, String to, String body) {
    IntegrationCommunicationConfig config = this.get(integrationConfigId);
    EntityNotFoundException.assertion(config != null, "IntegrationCommunicationConfig", "id",
        integrationConfigId.toString());

    Message message = new Message();
    message.setFrom(config.getDefaultFrom());
    message.setTo(to);
    message.setBody(body);
    message.setChannelType(config.getChannelType());

    List<Message> messages = this.communicationMessageService.add(List.of(message));
    return messages != null && !messages.isEmpty() ? messages.get(0) : null;
  }

  private static final String[][] SEARCH_FIELDS = {{"name"}};

  @Override
  public Page<IntegrationCommunicationConfig> search(String query, SortedSet<String> sort,
      boolean orderAscending, int page, int size) {
    return this.repoService.search(query, SEARCH_FIELDS, sort, orderAscending, page, size);
  }
}
