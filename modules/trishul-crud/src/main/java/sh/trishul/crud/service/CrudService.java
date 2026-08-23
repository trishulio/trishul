package sh.trishul.crud.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import org.springframework.data.domain.Page;
import sh.trishul.base.types.base.pojo.Identified;
import sh.trishul.model.base.pojo.DeleteResult;

public interface CrudService<ID, E, BE, UE extends Identified<ID>, A> {
  boolean exists(Set<ID> ids);

  boolean exist(ID id);

  DeleteResult delete(Set<ID> ids);

  DeleteResult delete(ID id);

  E get(ID id);

  List<E> getByIds(Collection<? extends Identified<ID>> idProviders);

  List<E> getByAccessorIds(Collection<? extends A> accessors);

  List<E> add(List<? extends BE> additions);

  List<E> put(List<? extends UE> updates);

  List<E> patch(List<? extends UE> updates);

  /**
   * Tokenized free-text search. The query is split on whitespace; each non-empty term must match
   * (AND) at least one of the given field paths (OR across paths). Matching uses an ilike
   * (case-insensitive) predicate built from the field paths.
   *
   * @param query the free-text search query, may be blank
   * @param sort the sort properties
   * @param orderAscending the sort direction
   * @param page the zero-based page number
   * @param size the page size
   * @return the matching page of entities
   */
  Page<E> search(String query, SortedSet<String> sort, boolean orderAscending, int page, int size);
}
