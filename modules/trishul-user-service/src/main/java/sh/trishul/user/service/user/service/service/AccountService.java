package sh.trishul.user.service.user.service.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.SortedSet;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import sh.trishul.auth.session.context.PrincipalContext;
import sh.trishul.auth.session.context.holder.ContextHolder;
import sh.trishul.model.base.exception.EntityNotFoundException;
import sh.trishul.repo.jpa.query.clause.where.builder.WhereClauseBuilder;
import sh.trishul.user.model.User;
import sh.trishul.user.service.user.service.repository.UserRepository;

@Transactional
public class AccountService {
  private final UserRepository userRepository;
  private final ContextHolder contextHolder;

  public AccountService(UserRepository userRepository, ContextHolder contextHolder) {
    this.userRepository = userRepository;
    this.contextHolder = contextHolder;
  }

  /**
   * Get the current user from the context holder.
   *
   * @return User entity for the current authenticated user
   * @throws EntityNotFoundException if the user is not found or not authenticated
   */
  public User getCurrentUser() {
    PrincipalContext principalContext = contextHolder.getPrincipalContext();

    if (principalContext == null) {
      throw new EntityNotFoundException("User", "context", "current");
    }

    String username = principalContext.getUsername();

    if (username == null || username.isEmpty()) {
      throw new EntityNotFoundException("User", "username", "current");
    }

    final Specification<User> spec
        = WhereClauseBuilder.builder().is(User.ATTR_IAAS_USERNAME, username).build();

    List<User> users = userRepository.findAll(spec);

    if (users.isEmpty()) {
      throw new EntityNotFoundException("User", "userName or iaasUsername", username);
    }

    return users.get(0);
  }

  public Page<User> search(String query, SortedSet<String> sort, boolean orderAscending, int page,
      int size) {
    return Page.empty();
  }
}
