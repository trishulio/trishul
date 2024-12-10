package io.trishul.repo.jpa.repository;

public interface ExtendedRepository<ID> {
    boolean existsByIds(Iterable<ID> ids);

    int deleteByIds(Iterable<ID> ids);

    int deleteOneById(ID id);
}
