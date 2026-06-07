package io.trishul.communication.service.channel;

import io.trishul.base.types.base.pojo.Identified;
import io.trishul.communication.model.channel.BaseCommunicationChannel;
import io.trishul.communication.model.channel.CommunicationChannel;
import io.trishul.communication.model.channel.UpdateCommunicationChannel;
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
public class CommunicationChannelService extends BaseService implements
    CrudService<String, CommunicationChannel, BaseCommunicationChannel<?>, UpdateCommunicationChannel<?>, CommunicationChannelAccessor<?>> {
  private static final Logger log = LoggerFactory.getLogger(CommunicationChannelService.class);

  private final IaasRepository<String, CommunicationChannel, BaseCommunicationChannel<?>, UpdateCommunicationChannel<?>> iaasRepo;
  private final EntityMergerService<String, CommunicationChannel, BaseCommunicationChannel<?>, UpdateCommunicationChannel<?>> entityMergerService;

  public CommunicationChannelService(
      EntityMergerService<String, CommunicationChannel, BaseCommunicationChannel<?>, UpdateCommunicationChannel<?>> entityMergerService,
      IaasRepository<String, CommunicationChannel, BaseCommunicationChannel<?>, UpdateCommunicationChannel<?>> iaasRepo) {
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
  public CommunicationChannel get(String id) {
    CommunicationChannel channel = null;

    List<CommunicationChannel> channels = this.iaasRepo.get(Set.of(id));
    if (channels.size() == 1) {
      channel = channels.get(0);
    } else {
      log.debug("Get channel: '{}' returned {}", id, channels.size());
    }

    return channel;
  }

  public List<CommunicationChannel> getAll(Set<String> ids) {
    return this.iaasRepo.get(ids);
  }

  @Override
  public List<CommunicationChannel> getByIds(Collection<? extends Identified<String>> idProviders) {
    Set<String> ids = idProviders.stream().filter(Objects::nonNull).map(Identified::getId)
        .filter(Objects::nonNull).collect(Collectors.toSet());

    return this.iaasRepo.get(ids);
  }

  @Override
  public List<CommunicationChannel> getByAccessorIds(
      Collection<? extends CommunicationChannelAccessor<?>> accessors) {
    List<CommunicationChannel> idProviders = accessors.stream().filter(Objects::nonNull)
        .map(CommunicationChannelAccessor::getCommunicationChannel).filter(Objects::nonNull)
        .toList();
    return getByIds(idProviders);
  }

  @Override
  public List<CommunicationChannel> add(List<? extends BaseCommunicationChannel<?>> additions) {
    if (additions == null) {
      return null;
    }

    List<CommunicationChannel> channels = this.entityMergerService.getAddEntities(additions);

    return iaasRepo.add(channels);
  }

  @Override
  public List<CommunicationChannel> put(List<? extends UpdateCommunicationChannel<?>> updates) {
    if (updates == null) {
      return null;
    }

    List<CommunicationChannel> updated = this.entityMergerService.getPutEntities(null, updates);

    return iaasRepo.put(updated);
  }

  @Override
  public List<CommunicationChannel> patch(List<? extends UpdateCommunicationChannel<?>> updates) {
    if (updates == null) {
      return null;
    }

    List<CommunicationChannel> existing = this.getByIds(updates);
    List<CommunicationChannel> updated
        = this.entityMergerService.getPatchEntities(existing, updates);

    return iaasRepo.put(updated);
  }
}
