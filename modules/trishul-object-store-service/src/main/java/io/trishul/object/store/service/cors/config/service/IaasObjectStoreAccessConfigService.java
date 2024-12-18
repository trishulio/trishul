package io.trishul.object.store.service.cors.config.service;

import io.trishul.base.types.base.pojo.Identified;
import io.trishul.crud.service.BaseService;
import io.trishul.crud.service.CrudService;
import io.trishul.crud.service.UpdateService;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfig;
import io.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfigAccessor;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
public class IaasObjectStoreAccessConfigService extends BaseService
        implements CrudService<
                String,
                IaasObjectStoreAccessConfig,
                IaasObjectStoreAccessConfig,
                IaasObjectStoreAccessConfig,
                IaasObjectStoreAccessConfigAccessor> {
    private static final Logger log =
            LoggerFactory.getLogger(IaasObjectStoreAccessConfigService.class);

    private final IaasRepository<
                    String,
                    IaasObjectStoreAccessConfig,
                    IaasObjectStoreAccessConfig,
                    IaasObjectStoreAccessConfig>
            iaasRepo;

    private final UpdateService<
                    String,
                    IaasObjectStoreAccessConfig,
                    IaasObjectStoreAccessConfig,
                    IaasObjectStoreAccessConfig>
            updateService;

    public IaasObjectStoreAccessConfigService(
            UpdateService<
                            String,
                            IaasObjectStoreAccessConfig,
                            IaasObjectStoreAccessConfig,
                            IaasObjectStoreAccessConfig>
                    updateService,
            IaasRepository<
                            String,
                            IaasObjectStoreAccessConfig,
                            IaasObjectStoreAccessConfig,
                            IaasObjectStoreAccessConfig>
                    iaasRepo) {
        this.updateService = updateService;
        this.iaasRepo = iaasRepo;
    }

    @Override
    public boolean exists(Set<String> ids) {
        return iaasRepo.exists(ids).values().stream()
                .filter(b -> !b)
                .findAny()
                .orElseGet(() -> true);
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
    public IaasObjectStoreAccessConfig get(String id) {
        IaasObjectStoreAccessConfig objectStoreAccessConfig = null;

        List<IaasObjectStoreAccessConfig> objectStoreAccessConfigs = this.iaasRepo.get(Set.of(id));
        if (objectStoreAccessConfigs.size() == 1) {
            objectStoreAccessConfig = objectStoreAccessConfigs.get(0);
        } else {
            log.error(
                    "Unexpectedly returned more than 1 access config for objectStore: {}: results {}",
                    id,
                    objectStoreAccessConfigs);
        }

        return objectStoreAccessConfig;
    }

    public List<IaasObjectStoreAccessConfig> getAll(Set<String> ids) {
        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasObjectStoreAccessConfig> getByIds(
            Collection<? extends Identified<String>> idProviders) {
        Set<String> ids =
                idProviders.stream()
                        .filter(Objects::nonNull)
                        .map(provider -> provider.getId())
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());

        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasObjectStoreAccessConfig> getByAccessorIds(
            Collection<? extends IaasObjectStoreAccessConfigAccessor> accessors) {
        List<IaasObjectStoreAccessConfig> idProviders =
                accessors.stream()
                        .filter(Objects::nonNull)
                        .map(accessor -> accessor.getIaasObjectStoreAccessConfig())
                        .filter(Objects::nonNull)
                        .toList();
        return getByIds(idProviders);
    }

    @Override
    public List<IaasObjectStoreAccessConfig> add(List<IaasObjectStoreAccessConfig> additions) {
        if (additions == null) {
            return null;
        }

        List<IaasObjectStoreAccessConfig> objectStoreAccessConfigs =
                this.updateService.getAddEntities(additions);

        return iaasRepo.add(objectStoreAccessConfigs);
    }

    @Override
    public List<IaasObjectStoreAccessConfig> put(List<IaasObjectStoreAccessConfig> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasObjectStoreAccessConfig> updated =
                this.updateService.getPutEntities(null, updates);

        return iaasRepo.put(updated);
    }

    @Override
    public List<IaasObjectStoreAccessConfig> patch(List<IaasObjectStoreAccessConfig> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasObjectStoreAccessConfig> existing = this.getByIds(updates);

        List<IaasObjectStoreAccessConfig> updated =
                this.updateService.getPatchEntities(existing, updates);

        return iaasRepo.put(updated);
    }
}
