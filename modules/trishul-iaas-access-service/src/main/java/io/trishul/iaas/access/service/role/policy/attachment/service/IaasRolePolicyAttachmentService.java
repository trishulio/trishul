package io.trishul.iaas.access.service.role.policy.attachment.service;

import io.trishul.base.types.base.pojo.Identified;
import io.trishul.crud.service.BaseService;
import io.trishul.crud.service.CrudService;
import io.trishul.crud.service.EntityMergerService;
import io.trishul.iaas.access.role.attachment.policy.BaseIaasRolePolicyAttachment;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachment;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentAccessor;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentId;
import io.trishul.iaas.access.role.attachment.policy.UpdateIaasRolePolicyAttachment;
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
public class IaasRolePolicyAttachmentService extends BaseService implements
    CrudService<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment<?>, UpdateIaasRolePolicyAttachment<?>, IaasRolePolicyAttachmentAccessor<?>> {
  private static final Logger log = LoggerFactory.getLogger(IaasRolePolicyAttachmentService.class);

  private final IaasRepository<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment<?>, UpdateIaasRolePolicyAttachment<?>> iaasRepo;

  private final EntityMergerService<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment<?>, UpdateIaasRolePolicyAttachment<?>> entityMergerService;

  public IaasRolePolicyAttachmentService(
      EntityMergerService<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment<?>, UpdateIaasRolePolicyAttachment<?>> entityMergerService,
      IaasRepository<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment<?>, UpdateIaasRolePolicyAttachment<?>> iaasRepo) {
    this.entityMergerService = entityMergerService;
    this.iaasRepo = iaasRepo;
  }

  @Override
  public boolean exists(Set<IaasRolePolicyAttachmentId> ids) {
    return iaasRepo.exists(ids).values().stream().filter(b -> !b).findAny().orElseGet(() -> true);
  }

  @Override
  public boolean exist(IaasRolePolicyAttachmentId id) {
    return exists(Set.of(id));
  }

  @Override
  public long delete(Set<IaasRolePolicyAttachmentId> ids) {
    return this.iaasRepo.delete(ids);
  }

  @Override
  public long delete(IaasRolePolicyAttachmentId id) {
    return this.iaasRepo.delete(Set.of(id));
  }

  @Override
  public IaasRolePolicyAttachment get(IaasRolePolicyAttachmentId id) {
    IaasRolePolicyAttachment attachment = null;

    List<IaasRolePolicyAttachment> attachments = this.iaasRepo.get(Set.of(id));
    if (attachments.size() == 1) {
      attachment = attachments.get(0);
    } else {
      log.debug("Get policy: '{}' returned {}", attachments);
    }

    return attachment;
  }

  public List<IaasRolePolicyAttachment> getAll(Set<IaasRolePolicyAttachmentId> ids) {
    return this.iaasRepo.get(ids);
  }

  @Override
  public List<IaasRolePolicyAttachment> getByIds(
      Collection<? extends Identified<IaasRolePolicyAttachmentId>> idProviders) {
    Set<IaasRolePolicyAttachmentId> ids = idProviders.stream().filter(Objects::nonNull)
        .map(provider -> provider.getId()).filter(Objects::nonNull).collect(Collectors.toSet());

    return this.iaasRepo.get(ids);
  }

  @Override
  public List<IaasRolePolicyAttachment> getByAccessorIds(
      Collection<? extends IaasRolePolicyAttachmentAccessor<?>> accessors) {
    List<IaasRolePolicyAttachment> idProviders = accessors.stream().filter(Objects::nonNull)
        .map(accessor -> accessor.getIaasRolePolicyAttachment()).filter(Objects::nonNull).toList();
    return getByIds(idProviders);
  }

  @Override
  public List<IaasRolePolicyAttachment> add(
      List<? extends BaseIaasRolePolicyAttachment<?>> additions) {
    if (additions == null) {
      return null;
    }

    List<IaasRolePolicyAttachment> rolePolicies
        = this.entityMergerService.getAddEntities(additions);

    return iaasRepo.add(rolePolicies);
  }

  @Override
  public List<IaasRolePolicyAttachment> put(
      List<? extends UpdateIaasRolePolicyAttachment<?>> updates) {
    if (updates == null) {
      return null;
    }

    List<IaasRolePolicyAttachment> updated = this.entityMergerService.getPutEntities(null, updates);

    return iaasRepo.put(updated);
  }

  @Override
  public List<IaasRolePolicyAttachment> patch(
      List<? extends UpdateIaasRolePolicyAttachment<?>> updates) {
    if (updates == null) {
      return null;
    }

    List<IaasRolePolicyAttachment> existing = this.getByIds(updates);

    List<IaasRolePolicyAttachment> updated
        = this.entityMergerService.getPatchEntities(existing, updates);

    return iaasRepo.put(updated);
  }
}
