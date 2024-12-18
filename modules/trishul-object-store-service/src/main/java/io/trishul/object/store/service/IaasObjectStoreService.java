package io.trishul.object.store.service;

import io.trishul.base.types.base.pojo.Identified;
import io.trishul.crud.service.BaseService;
import io.trishul.crud.service.CrudService;
import io.trishul.crud.service.UpdateService;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.object.store.model.BaseIaasObjectStore;
import io.trishul.object.store.model.IaasObjectStore;
import io.trishul.object.store.model.IaasObjectStoreAccessor;
import io.trishul.object.store.model.UpdateIaasObjectStore;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
public class IaasObjectStoreService extends BaseService
        implements CrudService<
                String,
                IaasObjectStore,
                BaseIaasObjectStore,
                UpdateIaasObjectStore,
                IaasObjectStoreAccessor> {
    private static final Logger log = LoggerFactory.getLogger(IaasObjectStoreService.class);

    private final IaasRepository<
                    String, IaasObjectStore, BaseIaasObjectStore, UpdateIaasObjectStore>
            iaasRepo;

    private final UpdateService<String, IaasObjectStore, BaseIaasObjectStore, UpdateIaasObjectStore>
            updateService;

    public IaasObjectStoreService(
            UpdateService<String, IaasObjectStore, BaseIaasObjectStore, UpdateIaasObjectStore>
                    updateService,
            IaasRepository<String, IaasObjectStore, BaseIaasObjectStore, UpdateIaasObjectStore>
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
    public IaasObjectStore get(String id) {
        IaasObjectStore objectStore = null;

        List<IaasObjectStore> policies = this.iaasRepo.get(Set.of(id));
        if (policies.size() == 1) {
            objectStore = policies.get(0);
        } else {
            log.debug("Get objectStore: '{}' returned {}", policies);
        }

        return objectStore;
    }

    public List<IaasObjectStore> getAll(Set<String> ids) {
        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasObjectStore> getByIds(Collection<? extends Identified<String>> idProviders) {
        Set<String> ids =
                idProviders.stream()
                        .filter(Objects::nonNull)
                        .map(provider -> provider.getId())
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());

        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasObjectStore> getByAccessorIds(
            Collection<? extends IaasObjectStoreAccessor> accessors) {
        List<IaasObjectStore> idProviders =
                accessors.stream()
                        .filter(Objects::nonNull)
                        .map(accessor -> accessor.getIaasObjectStore())
                        .filter(Objects::nonNull)
                        .toList();
        return getByIds(idProviders);
    }

    @Override
    public List<IaasObjectStore> add(List<BaseIaasObjectStore> additions) {
        if (additions == null) {
            return null;
        }

        List<IaasObjectStore> objectStores = this.updateService.getAddEntities(additions);

        return iaasRepo.add(objectStores);
    }

    @Override
    public List<IaasObjectStore> put(List<UpdateIaasObjectStore> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasObjectStore> updated = this.updateService.getPutEntities(null, updates);

        return iaasRepo.put(updated);
    }

    @Override
    public List<IaasObjectStore> patch(List<UpdateIaasObjectStore> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasObjectStore> existing = this.getByIds(updates);

        List<IaasObjectStore> updated = this.updateService.getPatchEntities(existing, updates);

        return iaasRepo.put(updated);
    }
}
