package io.trishul.iaas.access.service.policy.service;

import io.trishul.base.types.base.pojo.Identified;
import io.trishul.crud.service.BaseService;
import io.trishul.crud.service.CrudService;
import io.trishul.crud.service.UpdateService;
import io.trishul.iaas.access.policy.model.BaseIaasPolicy;
import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.access.policy.model.IaasPolicyAccessor;
import io.trishul.iaas.access.policy.model.UpdateIaasPolicy;
import io.trishul.iaas.repository.IaasRepository;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
public class IaasPolicyService extends BaseService
        implements CrudService<
                String, IaasPolicy, BaseIaasPolicy, UpdateIaasPolicy, IaasPolicyAccessor> {
    private static final Logger log = LoggerFactory.getLogger(IaasPolicyService.class);

    private final IaasRepository<String, IaasPolicy, BaseIaasPolicy, UpdateIaasPolicy> iaasRepo;
    private final UpdateService<String, IaasPolicy, BaseIaasPolicy, UpdateIaasPolicy> updateService;

    public IaasPolicyService(
            UpdateService<String, IaasPolicy, BaseIaasPolicy, UpdateIaasPolicy> updateService,
            IaasRepository<String, IaasPolicy, BaseIaasPolicy, UpdateIaasPolicy> iaasRepo) {
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
    public IaasPolicy get(String id) {
        IaasPolicy policy = null;

        List<IaasPolicy> policies = this.iaasRepo.get(Set.of(id));
        if (policies.size() == 1) {
            policy = policies.get(0);
        } else {
            log.debug("Get policy: '{}' returned {}", policies);
        }

        return policy;
    }

    public List<IaasPolicy> getAll(Set<String> ids) {
        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasPolicy> getByIds(Collection<? extends Identified<String>> idProviders) {
        Set<String> ids =
                idProviders.stream()
                        .filter(Objects::nonNull)
                        .map(provider -> provider.getId())
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());

        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasPolicy> getByAccessorIds(Collection<? extends IaasPolicyAccessor> accessors) {
        List<IaasPolicy> idProviders =
                accessors.stream()
                        .filter(Objects::nonNull)
                        .map(accessor -> accessor.getIaasPolicy())
                        .filter(Objects::nonNull)
                        .toList();
        return getByIds(idProviders);
    }

    @Override
    public List<IaasPolicy> add(List<BaseIaasPolicy> additions) {
        if (additions == null) {
            return null;
        }

        List<IaasPolicy> policies = this.updateService.getAddEntities(additions);

        return iaasRepo.add(policies);
    }

    @Override
    public List<IaasPolicy> put(List<UpdateIaasPolicy> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasPolicy> updated = this.updateService.getPutEntities(null, updates);

        return iaasRepo.put(updated);
    }

    @Override
    public List<IaasPolicy> patch(List<UpdateIaasPolicy> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasPolicy> existing = this.getByIds(updates);

        List<IaasPolicy> updated = this.updateService.getPatchEntities(existing, updates);

        return iaasRepo.put(updated);
    }
}
