package sh.trishul.repo.jpa.query.root;

import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Path;
import sh.trishul.repo.jpa.query.join.joiner.JpaJoiner;

public class RootUtil {
  public static final RootUtil INSTANCE = new RootUtil(JpaJoiner.JPA_JOINER);

  private final JpaJoiner jpaJoiner;

  protected RootUtil(JpaJoiner jpaJoiner) {
    this.jpaJoiner = jpaJoiner;
  }

  public <X, Y> Path<X> getPath(From<?, ?> root, String[] paths) {
    if (paths == null || paths.length <= 0) {
      throw new IllegalArgumentException("Paths cannot be null or empty");
    }

    @SuppressWarnings("unchecked")
    From<X, Y> j = (From<X, Y>) root;

    int i;
    for (i = 0; i < paths.length - 1; i++) {
      j = jpaJoiner.join(j, paths[i]);
    }

    return jpaJoiner.get(j, paths[i]);
  }
}
