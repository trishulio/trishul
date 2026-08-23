package sh.trishul.object.store.service.cors.config.service;

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
import sh.trishul.crud.service.BaseService;
import sh.trishul.crud.service.CrudService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.iaas.repository.IaasRepository;
import sh.trishul.model.base.pojo.DeleteResult;
import sh.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfiguration;
import sh.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfigurationAccessor;

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
    Set<String> ids = idProviders.stream().filter(Objects::nonNull).map(Identified::getId)
        .filter(Objects::nonNull).collect(Collectors.toSet());

    return this.iaasRepo.get(ids);
  }

  @Override
  public List<IaasObjectStoreCorsConfiguration> getByAccessorIds(
      Collection<? extends IaasObjectStoreCorsConfigurationAccessor<?>> accessors) {
    List<IaasObjectStoreCorsConfiguration> idProviders = accessors.stream().filter(Objects::nonNull)
        .map(IaasObjectStoreCorsConfigurationAccessor::getIaasObjectStoreCorsConfiguration)
        .filter(Objects::nonNull).toList();
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

  @Override
  public Page<IaasObjectStoreCorsConfiguration> search(String query, String[][] fieldPaths,
      SortedSet<String> sort, boolean orderAscending, int page, int size) {
    return Page.empty();
  }
}
