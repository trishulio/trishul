package io.trishul.repo.jpa.repository.refresher.accessor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.trishul.repo.jpa.repository.exception.EntityNotFoundException;
import io.trishul.repo.jpa.repository.model.pojo.Identified;

public class AccessorRefresher<I, A, V extends Identified<I>> {
    private static final Logger log = LoggerFactory.getLogger(AccessorRefresher.class);

    private final Class<V> clazz;
    private final Function<A, V> getter;
    private final BiConsumer<A, V> setter;
    private final Function<Iterable<I>, List<V>> entityRetriever;

    public AccessorRefresher(Class<V> clazz, Function<A, V> getter, BiConsumer<A, V> setter, Function<Iterable<I>, List<V>> entityRetriever) {
        this.clazz = clazz;
        this.getter = getter;
        this.setter = setter;
        this.entityRetriever = entityRetriever;
    }

    public void refreshAccessors(Collection<? extends A> accessors) {
        if (accessors != null && !accessors.isEmpty()) {
            Map<I, List<A>> lookupAccessorsByValueId = accessors.stream().filter(accessor -> accessor != null && getter.apply(accessor) != null).collect(Collectors.groupingBy(accessor -> getter.apply(accessor).getId()));
            log.debug("accessMap: {}", lookupAccessorsByValueId);

            List<V> entities = entityRetriever.apply(lookupAccessorsByValueId.keySet());

            if (lookupAccessorsByValueId.keySet().size() != entities.size()) {
                List<?> entityIds = entities.stream().map(entity -> entity.getId()).toList();
                throw new EntityNotFoundException(String.format("Cannot find all %ss in Id-Set: %s. Only found the ones with Ids: %s", this.clazz.getSimpleName(), lookupAccessorsByValueId.keySet(), entityIds));
            }

            accessors.forEach(accessor -> setter.accept(accessor, null));
            entities.forEach(value -> lookupAccessorsByValueId.get(value.getId()).forEach(accessor -> setter.accept(accessor, value)));
        }
    }
}
