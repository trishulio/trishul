package io.trishul.iaas.access.service.role.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.trishul.crud.service.BaseService;
import io.trishul.crud.service.CrudService;
import io.trishul.crud.service.UpdateService;
import io.trishul.iaas.access.role.model.BaseIaasRole;
import io.trishul.iaas.access.role.model.IaasRole;
import io.trishul.iaas.access.role.model.IaasRoleAccessor;
import io.trishul.iaas.access.role.model.UpdateIaasRole;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.model.base.pojo.Identified;

@Transactional
public class IaasRoleService extends BaseService implements CrudService<String, IaasRole, BaseIaasRole, UpdateIaasRole, IaasRoleAccessor> {
    private static final Logger log = LoggerFactory.getLogger(IaasRoleService.class);

    private final IaasRepository<String, IaasRole, BaseIaasRole, UpdateIaasRole> iaasRepo;

    private final UpdateService<String, IaasRole, BaseIaasRole, UpdateIaasRole> updateService;

    public IaasRoleService(UpdateService<String, IaasRole, BaseIaasRole, UpdateIaasRole> updateService, IaasRepository<String, IaasRole, BaseIaasRole, UpdateIaasRole> iaasRepo) {
        this.updateService = updateService;
        this.iaasRepo = iaasRepo;
    }

    @Override
    public boolean exists(Set<String> ids) {
        return iaasRepo.exists(ids).values()
                .stream().filter(b -> !b)
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
    public IaasRole get(String id) {
        IaasRole role = null;

        List<IaasRole> roles = this.iaasRepo.get(Set.of(id));
        if (roles.size() == 1) {
            role = roles.get(0);
        } else {
            log.debug("Get IaasRole: '{}' returned {}", roles);
        }

        return role;
    }

    public List<IaasRole> getAll(Set<String> ids) {
        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasRole> getByIds(Collection<? extends Identified<String>> idProviders) {
        Set<String> ids = idProviders.stream()
                    .filter(Objects::nonNull)
                    .map(provider -> provider.getId())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasRole> getByAccessorIds(Collection<? extends IaasRoleAccessor> accessors) {
        List<IaasRole> idProviders = accessors.stream()
                                    .filter(Objects::nonNull)
                                    .map(accessor -> accessor.getIaasRole())
                                    .filter(Objects::nonNull)
                                    .toList();
        return getByIds(idProviders);
    }

    @Override
    public List<IaasRole> add(List<BaseIaasRole> additions) {
        if (additions == null) {
            return null;
        }

        List<IaasRole> roles = this.updateService.getAddEntities(additions);

        return iaasRepo.add(roles);
    }

    @Override
    public List<IaasRole> put(List<UpdateIaasRole> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasRole> updated = this.updateService.getPutEntities(null, updates);

        return iaasRepo.put(updated);
    }

    @Override
    public List<IaasRole> patch(List<UpdateIaasRole> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasRole> existing = this.getByIds(updates);

        List<IaasRole> updated = this.updateService.getPatchEntities(existing, updates);

        return iaasRepo.put(updated);
    }
}
