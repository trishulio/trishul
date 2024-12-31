package io.trishul.crud.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.trishul.base.types.base.pojo.CrudEntity;
import io.trishul.base.types.base.pojo.UpdatableEntity;
import io.trishul.model.validator.UtilityProvider;
import io.trishul.model.validator.Validator;

public class CrudEntityMergerService<ID, E extends CrudEntity<ID, E>, BE, UE extends UpdatableEntity<ID, ?>>
    extends BaseService implements EntityMergerService<ID, E, BE, UE> {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(CrudEntityMergerService.class);

  private final UtilityProvider utilProvider;
  private final LockService lockService;

  private final Class<BE> baseEntityCls;
  private final Class<UE> updateEntityCls;
  private final Class<E> entityCls;
  private final Set<String> excludeProps;

  @SuppressWarnings("unchecked")
  public CrudEntityMergerService(UtilityProvider utilProvider, LockService lockService,
      Class<? extends BE> baseEntityCls, Class<? extends UE> updateEntityCls, Class<E> entityCls,
      Set<String> excludeProps) {
    this.utilProvider = utilProvider;
    this.baseEntityCls = (Class<BE>) baseEntityCls; // Unsafe cast. If the cast fails, update the
                                                    // passed arguments
    this.updateEntityCls = (Class<UE>) updateEntityCls; // Unsafe cast. If the cast fails, update
                                                        // the passed arguments
    this.entityCls = entityCls;
    this.excludeProps = excludeProps;
    this.lockService = lockService;
  }

  @Override
  public List<E> getAddEntities(List<? extends BE> additions) {
    if (additions == null) {
      return null;
    }

    return additions.stream().map(addition -> {
      final E item = this.newEntity();
      item.override(addition, this.getPropertyNames(this.baseEntityCls, this.excludeProps));
      return item;
    }).toList();
  }

  @Override
  public List<E> getPutEntities(List<E> existingItems, List<? extends UE> updates) {
    if (updates == null) {
      return new ArrayList<>(0);
    }

    final Validator validator = this.utilProvider.getValidator();

    existingItems = existingItems != null ? existingItems : new ArrayList<>(0);
    final Map<ID, E> idToItemLookup
        = existingItems.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));

    final List<E> targetItems = updates.stream().map(update -> {
      final E item = this.newEntity();
      Class<?> itemCls = this.baseEntityCls;
      if (update.getId() != null) {
        final E existing = idToItemLookup.get(update.getId());
        if (existing != null) {
          this.lockService.optimisticLockCheck(existing, update);
          itemCls = this.updateEntityCls;
        }
      }
      item.override(update, this.getPropertyNames(itemCls, this.excludeProps));
      item.setId(update.getId()); // Note: During "creation" for JPA
      // entities, this ID is ignored.
      return item;
    }).toList();

    validator.raiseErrors();
    return targetItems;
  }

  @Override
  public List<E> getPatchEntities(List<E> existingItems, List<? extends UE> patches) {
    final Validator validator = this.utilProvider.getValidator();

    List<E> targetItems = null;
    patches = patches == null ? new ArrayList<>() : patches;

    final Map<ID, UE> idToItemLookup
        = patches.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));

    if (existingItems != null) {
      targetItems = existingItems.stream().map(existing -> {
        final UE patch = idToItemLookup.remove(existing.getId());
        final E item = this.newEntity();
        item.override(existing, this.getPropertyNames(this.entityCls, this.excludeProps));
        if (patch != null) {
          this.lockService.optimisticLockCheck(existing, patch);
          item.outerJoin(patch, this.getPropertyNames(this.updateEntityCls, this.excludeProps));
        }
        item.setId(existing.getId());
        return item;
      }).toList();
    }

    idToItemLookup.forEach((id, patch) -> validator.rule(false,
        "Cannot apply the patch with Id: %s to an existing entity as it does not exist", id));

    validator.raiseErrors();
    return targetItems;
  }

  private E newEntity() {
    return this.util.construct(this.entityCls);
  }
}
