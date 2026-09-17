package sh.trishul.repo.filter;

import jakarta.persistence.EntityManager;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ArchiveFilterEnabler {
  private static final Logger log = LoggerFactory.getLogger(ArchiveFilterEnabler.class);

  private ArchiveFilterEnabler() {}

  /**
   * Enables the Hibernate archive filter on the current session so that archived entities are
   * excluded from queries.
   *
   * @param entityManager the entity manager
   * @return true if the filter was successfully enabled, false otherwise
   */
  public static boolean enableFilter(EntityManager entityManager) {
    try {
      Session session = entityManager.unwrap(Session.class);
      if (session != null) {
        Filter filter = session.enableFilter(ArchiveFilter.NAME);
        filter.setParameter(ArchiveFilter.PARAM_IS_ARCHIVED, false);
        filter.validate();
        log.debug("Enabled {}", ArchiveFilter.NAME);
        return true;
      }
    } catch (Exception e) {
      log.debug("Could not enable {}: {}", ArchiveFilter.NAME, e.getMessage());
    }
    return false;
  }
}
