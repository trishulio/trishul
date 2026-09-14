package sh.trishul.user.service.filter;

import jakarta.persistence.EntityManager;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sh.trishul.auth.session.context.holder.ContextHolder;
import sh.trishul.user.filter.OwnerFilter;

public final class OwnerFilterEnabler {
  private static final Logger log = LoggerFactory.getLogger(OwnerFilterEnabler.class);

  private OwnerFilterEnabler() {}

  /**
   * Enables the Hibernate owner filter on the current session if a valid username is present in the
   * security context.
   *
   * @return true if the filter was successfully enabled, false otherwise
   */
  public static boolean enableFilter(EntityManager entityManager, ContextHolder contextHolder) {
    try {
      String username = contextHolder.getPrincipalContext() != null
          ? contextHolder.getPrincipalContext().getUsername()
          : null;
      if (username != null && !username.isBlank()) {
        Session session = entityManager.unwrap(Session.class);
        if (session != null) {
          Filter filter = session.enableFilter(OwnerFilter.NAME);
          filter.setParameter(OwnerFilter.PARAM_OWNER_USERNAME, username);
          filter.validate();
          log.debug("Enabled {} with username: {}", OwnerFilter.NAME, username);
          return true;
        }
      }
    } catch (Exception e) {
      log.warn("Could not enable ownerFilter: {}", e.getMessage());
    }
    return false;
  }
}
