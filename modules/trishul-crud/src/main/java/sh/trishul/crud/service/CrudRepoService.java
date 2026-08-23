package sh.trishul.crud.service;

import static sh.trishul.repo.jpa.repository.service.RepoService.pageRequest;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sh.trishul.base.types.base.pojo.Identified;
import sh.trishul.base.types.base.pojo.Refresher;
import sh.trishul.model.base.pojo.DeleteResult;
import sh.trishul.repo.jpa.query.clause.where.builder.WhereClauseBuilder;
import sh.trishul.repo.jpa.repository.ExtendedRepository;
import sh.trishul.repo.jpa.repository.service.RepoService;

public class CrudRepoService<T extends JpaRepository<E, ID> & JpaSpecificationExecutor<E> & ExtendedRepository<ID>, ID, A, E extends Identified<ID>, U extends Refresher<E, A>>
    implements RepoService<ID, E, A> {
  private final T repo;
  private final U refresher;

  public CrudRepoService(T repo, U refresher) {
    this.repo = repo;
    this.refresher = refresher;
  }

  @Override
  public boolean exists(Set<ID> ids) {
    return this.repo.existsByIds(ids);
  }

  @Override
  public boolean exists(ID id) {
    return this.repo.existsById(id);
  }

  @Override
  public Page<E> getAll(Specification<E> spec, SortedSet<String> sort, boolean orderAscending,
      int page, int size) {
    final PageRequest pageable = pageRequest(sort, orderAscending, page, size);

    final Page<E> entities = this.repo.findAll(spec, pageable);

    return entities;
  }

  @Override
  public List<E> getAll(Specification<E> spec) {
    final List<E> entities = this.repo.findAll(spec);

    return entities;
  }

  @Override
  public List<E> getByIds(Collection<? extends Identified<ID>> idProviders) {
    if (idProviders == null) {
      return Collections.emptyList();
    }

    final Set<ID> ids = idProviders.stream().filter(Objects::nonNull).map(Identified::getId)
        .filter(Objects::nonNull).collect(Collectors.toSet());

    return this.repo.findAllById(ids);
  }

  @Override
  public List<E> getByAccessorIds(Collection<? extends A> accessors,
      Function<A, ? extends Identified<ID>> entityGetter) {
    if (accessors == null) {
      return Collections.emptyList();
    }

    final Set<ID> ids = accessors.stream().filter(Objects::nonNull)
        .map(accessor -> entityGetter.apply(accessor)).filter(Objects::nonNull)
        .map(Identified::getId).filter(Objects::nonNull).collect(Collectors.toSet());
    return this.repo.findAllById(ids);
  }

  @Override
  public Page<E> search(String query, String[][] fieldPaths, SortedSet<String> sort,
      boolean orderAscending, int page, int size) {
    Specification<E> spec = WhereClauseBuilder.builder().build();
    if (query != null && !query.isBlank()) {
      for (String term : query.split("\\s+")) {
        if (term.isBlank()) {
          continue;
        }
        Specification<E> termSpec = null;
        for (String[] path : fieldPaths) {
          final Specification<E> pathSpec
              = WhereClauseBuilder.builder().ilike(path, Set.of(term)).build();
          termSpec = termSpec == null ? pathSpec : termSpec.or(pathSpec);
        }
        spec = spec.and(termSpec);
      }
    }
    return getAll(spec, sort, orderAscending, page, size);
  }

  @Override
  public E get(ID id) {
    E po = null;

    final Optional<E> opt = this.repo.findById(id);
    if (opt.isPresent()) {
      po = opt.get();
    }

    return po;
  }

  @Override
  public List<E> saveAll(List<E> entities) {
    this.refresher.refresh(entities);
    final Iterable<E> saved = this.repo.saveAll(entities);
    this.repo.flush();

    return (List<E>) saved;
  }

  @Override
  public DeleteResult delete(Set<ID> ids) {
    return new DeleteResult(Long.valueOf(this.repo.deleteByIds(ids)));
  }

  @Override
  public DeleteResult delete(ID id) {
    return new DeleteResult(Long.valueOf(this.repo.deleteOneById(id)));
  }
}
