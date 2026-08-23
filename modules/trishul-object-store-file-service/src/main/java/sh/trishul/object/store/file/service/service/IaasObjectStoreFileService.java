package sh.trishul.object.store.file.service.service;

import jakarta.transaction.Transactional;
import java.net.URI;
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
import sh.trishul.object.store.file.model.BaseIaasObjectStoreFile;
import sh.trishul.object.store.file.model.IaasObjectStoreFile;
import sh.trishul.object.store.file.model.UpdateIaasObjectStoreFile;
import sh.trishul.object.store.file.model.accessor.IaasObjectStoreFileAccessor;

@Transactional
public class IaasObjectStoreFileService extends BaseService implements
    CrudService<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>, IaasObjectStoreFileAccessor<?>> {
  private static final Logger log = LoggerFactory.getLogger(IaasObjectStoreFileService.class);

  private final IaasRepository<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>> iaasRepo;

  private final EntityMergerService<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>> entityMergerService;

  public IaasObjectStoreFileService(
      EntityMergerService<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>> entityMergerService,
      IaasRepository<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>> iaasRepo) {
    this.entityMergerService = entityMergerService;
    this.iaasRepo = iaasRepo;
  }

  @Override
  public boolean exists(Set<URI> ids) {
    return !iaasRepo.exists(ids).containsValue(false);
  }

  @Override
  public boolean exist(URI id) {
    return exists(Set.of(id));
  }

  @Override
  public DeleteResult delete(Set<URI> ids) {
    return new DeleteResult(this.iaasRepo.delete(ids));
  }

  @Override
  public DeleteResult delete(URI id) {
    return new DeleteResult(this.iaasRepo.delete(Set.of(id)));
  }

  @Override
  public IaasObjectStoreFile get(URI id) {
    IaasObjectStoreFile file = null;

    List<IaasObjectStoreFile> files = this.iaasRepo.get(Set.of(id));
    if (files.size() == 1) {
      file = files.get(0);
    } else {
      log.debug("Get objectStore: '{}' returned {}", id, files.size());
    }

    return file;
  }

  public List<IaasObjectStoreFile> getAll(Set<URI> ids) {
    return this.iaasRepo.get(ids);
  }

  @Override
  public List<IaasObjectStoreFile> getByIds(Collection<? extends Identified<URI>> idProviders) {
    Set<URI> ids = idProviders.stream().filter(Objects::nonNull).map(Identified::getId)
        .filter(Objects::nonNull).collect(Collectors.toSet());

    return this.iaasRepo.get(ids);
  }

  @Override
  public List<IaasObjectStoreFile> getByAccessorIds(
      Collection<? extends IaasObjectStoreFileAccessor<?>> accessors) {
    List<IaasObjectStoreFile> idProviders = accessors.stream().filter(Objects::nonNull)
        .map(accessor -> accessor.getObjectStoreFile()).filter(Objects::nonNull).toList();
    return getByIds(idProviders);
  }

  @Override
  public List<IaasObjectStoreFile> add(List<? extends BaseIaasObjectStoreFile<?>> additions) {
    if (additions == null) {
      return null;
    }

    List<IaasObjectStoreFile> objectStores = this.entityMergerService.getAddEntities(additions);

    return iaasRepo.add(objectStores);
  }

  @Override
  public List<IaasObjectStoreFile> put(List<? extends UpdateIaasObjectStoreFile<?>> updates) {
    if (updates == null) {
      return null;
    }

    List<IaasObjectStoreFile> updated = this.entityMergerService.getPutEntities(null, updates);

    return iaasRepo.put(updated);
  }

  @Override
  public List<IaasObjectStoreFile> patch(List<? extends UpdateIaasObjectStoreFile<?>> updates) {
    if (updates == null) {
      return null;
    }

    throw new UnsupportedOperationException("Patch is not supported for file urls");
  }

  private static final String[][] SEARCH_FIELDS = {{"fileName"}, {"originalFileName"}};

  @Override
  public Page<IaasObjectStoreFile> search(String query, SortedSet<String> sort,
      boolean orderAscending, int page, int size) {
    return Page.empty();
  }
}
