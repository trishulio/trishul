package io.trishul.communication.service.account;

import io.trishul.base.types.base.pojo.Identified;
import io.trishul.communication.model.account.BaseCommunicationAccount;
import io.trishul.communication.model.account.CommunicationAccount;
import io.trishul.communication.model.account.UpdateCommunicationAccount;
import io.trishul.crud.service.BaseService;
import io.trishul.crud.service.CrudService;
import io.trishul.crud.service.EntityMergerService;
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
  public long delete(Set<String> ids) {
    return this.iaasRepo.delete(ids);
  }

  @Override
  public long delete(String id) {
    return this.iaasRepo.delete(Set.of(id));
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
}
