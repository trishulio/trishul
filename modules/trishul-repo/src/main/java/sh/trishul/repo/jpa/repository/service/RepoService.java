package sh.trishul.repo.jpa.repository.service;

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
import sh.trishul.base.types.base.pojo.Identified;
import sh.trishul.model.base.pojo.DeleteResult;
import sh.trishul.repo.jpa.query.clause.where.builder.WhereClauseBuilder;

public interface RepoService<ID, E extends Identified<ID>, A> {
  public static PageRequest pageRequest(SortedSet<String> sort, boolean orderAscending, int page,
      int size) {
    Sort sortBy = Sort.unsorted();
    if (sort != null && !sort.isEmpty()) {
      sortBy
          = Sort.by(orderAscending ? Direction.ASC : Direction.DESC, sort.toArray(String[]::new));
    }

    return PageRequest.of(page, size, sortBy);
  }

  boolean exists(Set<ID> ids);

  boolean exists(ID id);

  E get(ID id);

  Page<E> getAll(Specification<E> spec, SortedSet<String> sort, boolean orderAscending, int page,
      int size);

  List<E> getAll(Specification<E> spec);

  List<E> getByIds(Collection<? extends Identified<ID>> idProviders);

  /**
   * Tokenized free-text search. The query is split on whitespace; each non-empty term must match
   * (AND) at least one of the given field paths (OR across paths). Matching uses an ilike
   * (case-insensitive) predicate built from the field paths.
   *
   * <p>
   * This is a default method so existing {@code RepoService} implementations keep working; callers
   * that do not opt in should override it.
   *
   * @param query the free-text search query, may be blank
   * @param fieldPaths the field (possibly dotted/nested) paths to match against
   * @param sort the sort properties
   * @param orderAscending the sort direction
   * @param page the zero-based page number
   * @param size the page size
   * @return the matching page of entities
   */
  default Page<E> search(String query, String[][] fieldPaths, SortedSet<String> sort,
      boolean orderAscending, int page, int size) {
    Specification<E> spec = WhereClauseBuilder.<E>builder().build();
    if (query != null && !query.isBlank()) {
      for (String term : query.split("\\s+")) {
        if (term.isBlank()) {
          continue;
        }
        Specification<E> termSpec = null;
        for (String[] path : fieldPaths) {
          final Specification<E> pathSpec
              = WhereClauseBuilder.<E>builder().ilike(path, Set.of(term)).build();
          termSpec = termSpec == null ? pathSpec : termSpec.or(pathSpec);
        }
        spec = spec.and(termSpec);
      }
    }
    return getAll(spec, sort, orderAscending, page, size);
  }

  List<E> getByAccessorIds(Collection<? extends A> accessors,
      Function<A, ? extends Identified<ID>> entityGetter);

  List<E> saveAll(List<E> entities);

  DeleteResult delete(Set<ID> ids);

  DeleteResult delete(ID id);
}
