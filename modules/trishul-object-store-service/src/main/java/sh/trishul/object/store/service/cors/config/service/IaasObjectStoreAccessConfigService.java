package sh.trishul.object.store.service.cors.config.service;

import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sh.trishul.base.types.base.pojo.Identified;
import sh.trishul.crud.service.BaseService;
import sh.trishul.crud.service.CrudService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.iaas.repository.IaasRepository;
import sh.trishul.model.base.pojo.DeleteResult;
import sh.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfig;
import sh.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfigAccessor;

@Transactional
public class IaasObjectStoreAccessConfigService extends BaseService implements
    CrudService<String, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfigAccessor<?>> {
  private static final Logger log
      = LoggerFactory.getLogger(IaasObjectStoreAccessConfigService.class);

  private final IaasRepository<String, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig> iaasRepo;

  private final EntityMergerService<String, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig> entityMergerService;

  public IaasObjectStoreAccessConfigService(
      EntityMergerService<String, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig> entityMergerService,
      IaasRepository<String, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig, IaasObjectStoreAccessConfig> iaasRepo) {
    this.entityMergerService = entityMergerService;
    this.iaasRepo = iaasRepo;
  }

  @Override
  public boolean exists(Set<String> ids) {
    return !iaasRepo.exists(ids).containsValue(false);
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
  public IaasObjectStoreAccessConfig get(String id) {
    IaasObjectStoreAccessConfig objectStoreAccessConfig = null;

    List<IaasObjectStoreAccessConfig> objectStoreAccessConfigs = this.iaasRepo.get(Set.of(id));
    if (objectStoreAccessConfigs.size() == 1) {
      objectStoreAccessConfig = objectStoreAccessConfigs.get(0);
    } else {
      log.error("Unexpectedly returned more than 1 access config for objectStore: {}: results {}",
          id, objectStoreAccessConfigs);
    }

    return objectStoreAccessConfig;
  }

  public List<IaasObjectStoreAccessConfig> getAll(Set<String> ids) {
    return this.iaasRepo.get(ids);
  }

  @Override
  public List<IaasObjectStoreAccessConfig> getByIds(
      Collection<? extends Identified<String>> idProviders) {
    Set<String> ids = idProviders.stream().filter(Objects::nonNull).map(Identified::getId)
        .filter(Objects::nonNull).collect(Collectors.toSet());

    return this.iaasRepo.get(ids);
  }

  @Override
  public List<IaasObjectStoreAccessConfig> getByAccessorIds(
      Collection<? extends IaasObjectStoreAccessConfigAccessor<?>> accessors) {
    List<IaasObjectStoreAccessConfig> idProviders = accessors.stream().filter(Objects::nonNull)
        .map(IaasObjectStoreAccessConfigAccessor::getIaasObjectStoreAccessConfig)
        .filter(Objects::nonNull).toList();
    return getByIds(idProviders);
  }

  @Override
  public List<IaasObjectStoreAccessConfig> add(
      List<? extends IaasObjectStoreAccessConfig> additions) {
    if (additions == null) {
      return null;
    }

    List<IaasObjectStoreAccessConfig> objectStoreAccessConfigs
        = this.entityMergerService.getAddEntities(additions);

    return iaasRepo.add(objectStoreAccessConfigs);
  }

  @Override
  public List<IaasObjectStoreAccessConfig> put(
      List<? extends IaasObjectStoreAccessConfig> updates) {
    if (updates == null) {
      return null;
    }

    List<IaasObjectStoreAccessConfig> updated
        = this.entityMergerService.getPutEntities(null, updates);

    return iaasRepo.put(updated);
  }

  @Override
  public List<IaasObjectStoreAccessConfig> patch(
      List<? extends IaasObjectStoreAccessConfig> updates) {
    if (updates == null) {
      return null;
    }

    List<IaasObjectStoreAccessConfig> existing = this.getByIds(updates);

    List<IaasObjectStoreAccessConfig> updated
        = this.entityMergerService.getPatchEntities(existing, updates);

    return iaasRepo.put(updated);
  }
}
