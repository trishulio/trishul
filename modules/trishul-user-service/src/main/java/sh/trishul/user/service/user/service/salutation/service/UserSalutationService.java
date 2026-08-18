package sh.trishul.user.service.user.service.salutation.service;

import java.util.Set;
import java.util.SortedSet;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import sh.trishul.crud.service.BaseService;
import sh.trishul.repo.jpa.query.clause.where.builder.WhereClauseBuilder;
import sh.trishul.repo.jpa.repository.service.RepoService;
import sh.trishul.user.salutation.model.UserSalutation;
import sh.trishul.user.salutation.model.UserSalutationAccessor;

@Transactional
public class UserSalutationService extends BaseService {
  private final RepoService<Long, UserSalutation, UserSalutationAccessor<?>> repoService;

  public UserSalutationService(
      RepoService<Long, UserSalutation, UserSalutationAccessor<?>> repoService) {
    this.repoService = repoService;
  }

  public Page<UserSalutation> getSalutations(Set<Long> ids, SortedSet<String> sort,
      boolean orderAscending, int page, int size) {
    Specification<UserSalutation> spec
        = WhereClauseBuilder.builder().in(UserSalutation.ATTR_ID, ids).build();
    return this.repoService.getAll(spec, sort, orderAscending, page, size);
  }
}
