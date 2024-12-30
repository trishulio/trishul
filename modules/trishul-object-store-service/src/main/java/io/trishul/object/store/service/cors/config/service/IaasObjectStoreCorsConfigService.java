package io.trishul.object.store.service.cors.config.service;

import io.trishul.base.types.base.pojo.Identified;
import io.trishul.crud.service.BaseService;
import io.trishul.crud.service.CrudService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfiguration;
import io.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfigurationAccessor;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
public class IaasObjectStoreCorsConfigService extends BaseService implements
    CrudService<String, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfigurationAccessor<?>> {
  private static final Logger log = LoggerFactory.getLogger(IaasObjectStoreCorsConfigService.class);

  private final IaasRepository<String, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration> iaasRepo;

  private final EntityMergerService<String, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration> entityMergerService;

  public IaasObjectStoreCorsConfigService(
      EntityMergerService<String, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration> entityMergerService,
      IaasRepository<String, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration> iaasRepo) {
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
  public IaasObjectStoreCorsConfiguration get(String id) {
    IaasObjectStoreCorsConfiguration bucketCrossOriginConfig = null;

    List<IaasObjectStoreCorsConfiguration> bucketCrossOriginConfigs = this.iaasRepo.get(Set.of(id));
    if (bucketCrossOriginConfigs.size() == 1) {
      bucketCrossOriginConfig = bucketCrossOriginConfigs.get(0);
    } else {
      log.error("Unexpectedly returned more than 1 config for objectStore: {}: results {}", id,
          bucketCrossOriginConfigs);
    }

    return bucketCrossOriginConfig;
  }

  public List<IaasObjectStoreCorsConfiguration> getAll(Set<String> ids) {
    return this.iaasRepo.get(ids);
  }

  @Override
  public List<IaasObjectStoreCorsConfiguration> getByIds(
      Collection<? extends Identified<String>> idProviders) {
    Set<String> ids = idProviders.stream().filter(Objects::nonNull)
        .map(provider -> provider.getId()).filter(Objects::nonNull).collect(Collectors.toSet());

    return this.iaasRepo.get(ids);
  }

  @Override
  public List<IaasObjectStoreCorsConfiguration> getByAccessorIds(
      Collection<? extends IaasObjectStoreCorsConfigurationAccessor<?>> accessors) {
    List<IaasObjectStoreCorsConfiguration> idProviders = accessors.stream().filter(Objects::nonNull)
        .map(accessor -> accessor.getIaasObjectStoreCorsConfiguration()).filter(Objects::nonNull)
        .toList();
    return getByIds(idProviders);
  }

  @Override
  public List<IaasObjectStoreCorsConfiguration> add(
      List<? extends IaasObjectStoreCorsConfiguration> additions) {
    if (additions == null) {
      return null;
    }

    List<IaasObjectStoreCorsConfiguration> objectStoreCorsConfigs
        = this.entityMergerService.getAddEntities(additions);

    return iaasRepo.add(objectStoreCorsConfigs);
  }

  @Override
  public List<IaasObjectStoreCorsConfiguration> put(
      List<? extends IaasObjectStoreCorsConfiguration> updates) {
    if (updates == null) {
      return null;
    }

    List<IaasObjectStoreCorsConfiguration> updated
        = this.entityMergerService.getPutEntities(null, updates);

    return iaasRepo.put(updated);
  }

  @Override
  public List<IaasObjectStoreCorsConfiguration> patch(
      List<? extends IaasObjectStoreCorsConfiguration> updates) {
    if (updates == null) {
      return null;
    }

    List<IaasObjectStoreCorsConfiguration> existing = this.getByIds(updates);

    List<IaasObjectStoreCorsConfiguration> updated
        = this.entityMergerService.getPatchEntities(existing, updates);

    return iaasRepo.put(updated);
  }
}
