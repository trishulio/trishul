package io.trishul.communication.service.message;

import io.trishul.base.types.base.pojo.Identified;
import io.trishul.communication.model.message.BaseMessage;
import io.trishul.communication.model.message.Message;
import io.trishul.communication.model.message.UpdateMessage;
import io.trishul.crud.service.BaseService;
import io.trishul.crud.service.CrudService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.iaas.repository.IaasRepository;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
public class CommunicationMessageService extends BaseService
    implements CrudService<String, Message, BaseMessage<?>, UpdateMessage<?>, MessageAccessor<?>> {
  private static final Logger log = LoggerFactory.getLogger(CommunicationMessageService.class);

  private final IaasRepository<String, Message, BaseMessage<?>, UpdateMessage<?>> iaasRepo;
  private final EntityMergerService<String, Message, BaseMessage<?>, UpdateMessage<?>> entityMergerService;

  public CommunicationMessageService(
      EntityMergerService<String, Message, BaseMessage<?>, UpdateMessage<?>> entityMergerService,
      IaasRepository<String, Message, BaseMessage<?>, UpdateMessage<?>> iaasRepo) {
    this.entityMergerService = entityMergerService;
    this.iaasRepo = iaasRepo;
  }

  @Override
  public boolean exists(Set<String> ids) {
    return iaasRepo.exists(ids).values().stream().filter(b -> !b).findAny().orElseGet(() -> true);
  }

  @Override
  public boolean exist(String id) {
    return exists(Set.of(id));
  }

  @Override
  public long delete(Set<String> ids) {
    return this.iaasRepo.delete(ids);
  }

  @Override
  public long delete(String id) {
    return this.iaasRepo.delete(Set.of(id));
  }

  @Override
  public Message get(String id) {
    Message message = null;

    List<Message> messages = this.iaasRepo.get(Set.of(id));
    if (messages.size() == 1) {
      message = messages.get(0);
    } else {
      log.debug("Get message: '{}' returned {}", id, messages.size());
    }

    return message;
  }

  @Override
  public List<Message> getByIds(Collection<? extends Identified<String>> idProviders) {
    Set<String> ids = idProviders.stream().filter(Objects::nonNull).map(Identified::getId)
        .filter(Objects::nonNull).collect(Collectors.toSet());

    return this.iaasRepo.get(ids);
  }

  @Override
  public List<Message> getByAccessorIds(Collection<? extends MessageAccessor<?>> accessors) {
    List<Message> idProviders = accessors.stream().filter(Objects::nonNull)
        .map(MessageAccessor::getMessage).filter(Objects::nonNull).toList();
    return getByIds(idProviders);
  }

  @Override
  public List<Message> add(List<? extends BaseMessage<?>> additions) {
    if (additions == null) {
      return null;
    }

    List<Message> messages = this.entityMergerService.getAddEntities(additions);

    return iaasRepo.add(messages);
  }

  @Override
  public List<Message> put(List<? extends UpdateMessage<?>> updates) {
    if (updates == null) {
      return null;
    }

    List<Message> updated = this.entityMergerService.getPutEntities(null, updates);

    return iaasRepo.put(updated);
  }

  @Override
  public List<Message> patch(List<? extends UpdateMessage<?>> updates) {
    if (updates == null) {
      return null;
    }

    List<Message> existing = this.getByIds(updates);
    List<Message> updated = this.entityMergerService.getPatchEntities(existing, updates);

    return iaasRepo.put(updated);
  }
}
