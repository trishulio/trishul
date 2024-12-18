package io.trishul.iaas.client;

import io.trishul.base.types.base.pojo.Identified;
import io.trishul.iaas.repository.IaasRepository;
import io.trishul.model.executor.BlockingAsyncExecutor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class BulkIaasClient<ID, Entity extends Identified<ID>, BaseEntity, UpdateEntity>
        implements IaasRepository<ID, Entity, BaseEntity, UpdateEntity> {
    private final BlockingAsyncExecutor executor;
    private final IaasClient<ID, Entity, BaseEntity, UpdateEntity> iaasClient;

    public BulkIaasClient(
            BlockingAsyncExecutor executor,
            IaasClient<ID, Entity, BaseEntity, UpdateEntity> iaasClient) {
        this.executor = executor;
        this.iaasClient = iaasClient;
    }

    @Override
    public List<Entity> get(Set<ID> ids) {
        List<Supplier<Entity>> suppliers =
                ids.stream()
                        .filter(Objects::nonNull)
                        .map(id -> (Supplier<Entity>) () -> iaasClient.get(id))
                        .toList();

        return this.executor.supply(suppliers).stream().filter(Objects::nonNull).toList();
    }

    @Override
    public <BE extends BaseEntity> List<Entity> add(List<BE> entities) {
        List<Supplier<Entity>> suppliers =
                entities.stream()
                        .filter(Objects::nonNull)
                        .map(entity -> (Supplier<Entity>) () -> iaasClient.add(entity))
                        .toList();

        return this.executor.supply(suppliers);
    }

    @Override
    public <UE extends UpdateEntity> List<Entity> put(List<UE> entities) {
        List<Supplier<Entity>> suppliers =
                entities.stream()
                        .filter(Objects::nonNull)
                        .map(entity -> (Supplier<Entity>) () -> iaasClient.put(entity))
                        .toList();

        return this.executor.supply(suppliers);
    }

    @Override
    public long delete(Set<ID> ids) {
        List<Supplier<Boolean>> suppliers =
                ids.stream()
                        .filter(Objects::nonNull)
                        .map(id -> (Supplier<Boolean>) () -> iaasClient.delete(id))
                        .toList();

        return this.executor.supply(suppliers).stream().filter(b -> b).count();
    }

    @Override
    public Map<ID, Boolean> exists(Set<ID> ids) {
        Map<ID, Boolean> exists = new HashMap<>();

        this.get(ids).stream()
                .filter(Objects::nonNull)
                .map(Identified::getId)
                .forEach(existingId -> exists.put(existingId, true));
        ids.forEach(id -> exists.putIfAbsent(id, false));

        return exists;
    }
}
