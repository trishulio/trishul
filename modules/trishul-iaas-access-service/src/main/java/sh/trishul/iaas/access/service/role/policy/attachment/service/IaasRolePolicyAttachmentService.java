package sh.trishul.iaas.access.service.role.policy.attachment.service;

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
import sh.trishul.iaas.access.role.attachment.policy.BaseIaasRolePolicyAttachment;
import sh.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachment;
import sh.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentAccessor;
import sh.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentId;
import sh.trishul.iaas.access.role.attachment.policy.UpdateIaasRolePolicyAttachment;
import sh.trishul.iaas.repository.IaasRepository;
import sh.trishul.model.base.pojo.DeleteResult;

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
    return !iaasRepo.exists(ids).containsValue(false);
  }

  @Override
  public boolean exist(IaasRolePolicyAttachmentId id) {
    return exists(Set.of(id));
  }

  @Override
  public DeleteResult delete(Set<IaasRolePolicyAttachmentId> ids) {
    return new DeleteResult(this.iaasRepo.delete(ids));
  }

  @Override
  public DeleteResult delete(IaasRolePolicyAttachmentId id) {
    return new DeleteResult(this.iaasRepo.delete(Set.of(id)));
  }

  @Override
  public IaasRolePolicyAttachment get(IaasRolePolicyAttachmentId id) {
    IaasRolePolicyAttachment attachment = null;

    List<IaasRolePolicyAttachment> attachments = this.iaasRepo.get(Set.of(id));
    if (attachments.size() == 1) {
      attachment = attachments.get(0);
    } else {
      log.debug("Get policy: '{}' returned {}", id, attachments.size());
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
        .map(Identified::getId).filter(Objects::nonNull).collect(Collectors.toSet());

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

  @Override
  public Page<IaasRolePolicyAttachment> search(String query, String[][] fieldPaths,
      SortedSet<String> sort, boolean orderAscending, int page, int size) {
    return Page.empty();
  }
}
