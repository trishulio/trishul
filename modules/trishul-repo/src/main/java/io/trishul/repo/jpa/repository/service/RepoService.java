package io.trishul.repo.jpa.repository.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import io.trishul.base.types.base.pojo.Identified;

public interface RepoService<ID, E extends Identified<ID>, A> {
    public static PageRequest pageRequest(SortedSet<String> sort, boolean orderAscending, int page, int size) {
        Sort sortBy = Sort.unsorted();
        if (sort != null && !sort.isEmpty()) {
            sortBy = Sort.by(orderAscending ? Direction.ASC : Direction.DESC, sort.toArray(String[]::new));
        }

        return PageRequest.of(page, size, sortBy);
    }

    boolean exists(Set<ID> ids);

    boolean exists(ID id);

    E get(ID id);

    Page<E> getAll(Specification<E> spec, SortedSet<String> sort, boolean orderAscending, int page, int size);

    List<E> getAll(Specification<E> spec);

    List<E> getByIds(Collection<? extends Identified<ID>> idProviders);

    List<E> getByAccessorIds(Collection<? extends A> accessors, Function<A, ? extends Identified<ID>> entityGetter);

    List<E> saveAll(List<E> entities);

    long delete(Set<ID> ids);

    long delete(ID id);
}
