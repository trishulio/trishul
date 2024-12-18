package io.trishul.user.service.user.service.salutation.service;

import static io.trishul.repo.jpa.repository.service.RepoService.pageRequest;

import io.trishul.crud.service.BaseService;
import io.trishul.repo.jpa.query.clause.where.builder.WhereClauseBuilder;
import io.trishul.user.model.User;
import io.trishul.user.salutation.model.UserSalutation;
import io.trishul.user.service.user.service.salutation.repository.UserSalutationRepository;
import java.util.Set;
import java.util.SortedSet;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserSalutationService extends BaseService {
    private final UserSalutationRepository userSalutationRepository;

    public UserSalutationService(UserSalutationRepository userSalutationRepository) {
        this.userSalutationRepository = userSalutationRepository;
    }

    public Page<UserSalutation> getSalutations(
            Set<Long> ids, SortedSet<String> sort, boolean orderAscending, int page, int size) {
        Specification<UserSalutation> spec =
                WhereClauseBuilder.builder().in(User.FIELD_ID, ids).build();
        Page<UserSalutation> userSalutations =
                userSalutationRepository.findAll(
                        spec, pageRequest(sort, orderAscending, page, size));

        // TODO: Convert this service to use the RepoService implementation

        return userSalutations;
    }
}
