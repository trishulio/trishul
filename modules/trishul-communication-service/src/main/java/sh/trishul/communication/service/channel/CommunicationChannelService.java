package sh.trishul.communication.service.channel;

import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import sh.trishul.base.types.base.pojo.Identified;
import sh.trishul.communication.model.channel.BaseCommunicationChannel;
import sh.trishul.communication.model.channel.CommunicationChannel;
import sh.trishul.communication.model.channel.UpdateCommunicationChannel;
import sh.trishul.crud.service.BaseService;
import sh.trishul.crud.service.CrudService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.iaas.repository.IaasRepository;
import sh.trishul.model.base.pojo.DeleteResult;

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
  public DeleteResult delete(Set<String> ids) {
    return new DeleteResult(this.iaasRepo.delete(ids));
  }

  @Override
  public DeleteResult delete(String id) {
    return new DeleteResult(this.iaasRepo.delete(Set.of(id)));
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

  @Override
  public Page<CommunicationChannel> search(String query, String[][] fieldPaths,
      SortedSet<String> sort, boolean orderAscending, int page, int size) {
    return Page.empty();
  }
}
