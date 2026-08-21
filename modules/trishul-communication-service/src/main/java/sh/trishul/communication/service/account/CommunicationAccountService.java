package sh.trishul.communication.service.account;

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
import sh.trishul.communication.model.account.BaseCommunicationAccount;
import sh.trishul.communication.model.account.CommunicationAccount;
import sh.trishul.communication.model.account.UpdateCommunicationAccount;
import sh.trishul.crud.service.BaseService;
import sh.trishul.crud.service.CrudService;
import sh.trishul.crud.service.EntityMergerService;
import sh.trishul.iaas.repository.IaasRepository;
import sh.trishul.model.base.pojo.DeleteResult;

@Transactional
public class CommunicationAccountService extends BaseService implements
    CrudService<String, CommunicationAccount, BaseCommunicationAccount<?>, UpdateCommunicationAccount<?>, CommunicationAccountAccessor<?>> {
  private static final Logger log = LoggerFactory.getLogger(CommunicationAccountService.class);

  private final IaasRepository<String, CommunicationAccount, BaseCommunicationAccount<?>, UpdateCommunicationAccount<?>> iaasRepo;
  private final EntityMergerService<String, CommunicationAccount, BaseCommunicationAccount<?>, UpdateCommunicationAccount<?>> entityMergerService;

  public CommunicationAccountService(
      EntityMergerService<String, CommunicationAccount, BaseCommunicationAccount<?>, UpdateCommunicationAccount<?>> entityMergerService,
      IaasRepository<String, CommunicationAccount, BaseCommunicationAccount<?>, UpdateCommunicationAccount<?>> iaasRepo) {
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
  public DeleteResult delete(Set<String> ids) {
    return new DeleteResult(this.iaasRepo.delete(ids));
  }

  @Override
  public DeleteResult delete(String id) {
    return new DeleteResult(this.iaasRepo.delete(Set.of(id)));
  }

  @Override
  public CommunicationAccount get(String id) {
    CommunicationAccount account = null;

    List<CommunicationAccount> accounts = this.iaasRepo.get(Set.of(id));
    if (accounts.size() == 1) {
      account = accounts.get(0);
    } else {
      log.debug("Get communication account: '{}' returned {}", id, accounts.size());
    }

    return account;
  }

  @Override
  public List<CommunicationAccount> getByIds(Collection<? extends Identified<String>> idProviders) {
    Set<String> ids = idProviders.stream().filter(Objects::nonNull).map(Identified::getId)
        .filter(Objects::nonNull).collect(Collectors.toSet());

    return this.iaasRepo.get(ids);
  }

  @Override
  public List<CommunicationAccount> getByAccessorIds(
      Collection<? extends CommunicationAccountAccessor<?>> accessors) {
    List<CommunicationAccount> idProviders = accessors.stream().filter(Objects::nonNull)
        .map(CommunicationAccountAccessor::getCommunicationAccount).filter(Objects::nonNull)
        .toList();
    return getByIds(idProviders);
  }

  @Override
  public List<CommunicationAccount> add(List<? extends BaseCommunicationAccount<?>> additions) {
    if (additions == null) {
      return null;
    }

    List<CommunicationAccount> accounts = this.entityMergerService.getAddEntities(additions);

    return iaasRepo.add(accounts);
  }

  @Override
  public List<CommunicationAccount> put(List<? extends UpdateCommunicationAccount<?>> updates) {
    if (updates == null) {
      return null;
    }

    List<CommunicationAccount> updated = this.entityMergerService.getPutEntities(null, updates);

    return iaasRepo.put(updated);
  }

  @Override
  public List<CommunicationAccount> patch(List<? extends UpdateCommunicationAccount<?>> updates) {
    if (updates == null) {
      return null;
    }

    List<CommunicationAccount> existing = this.getByIds(updates);
    List<CommunicationAccount> updated
        = this.entityMergerService.getPatchEntities(existing, updates);

    return iaasRepo.put(updated);
  }

  @Override
  public Page<CommunicationAccount> search(String query, String[][] fieldPaths,
      SortedSet<String> sort, boolean orderAscending, int page, int size) {
    return Page.empty();
  }
}
