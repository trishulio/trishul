package io.trishul.repo.jpa.query.join.joiner;

import com.google.common.collect.ImmutableSet;
import io.trishul.model.base.entity.CriteriaJoin;
import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CriteriaJoinAnnotationJoiner implements JpaJoiner {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(CriteriaJoinAnnotationJoiner.class);

  public static final Set<Class<?>> COMPOUND_ENTITY_ANNOTATIONS = ImmutableSet.of(OneToMany.class,
      ManyToOne.class, Embedded.class, JoinColumn.class, CriteriaJoin.class);

  @Override
  public <X, Y> From<X, Y> join(From<X, Y> join, String fieldName) {
    Field field = FieldUtils.getAllFieldsList(join.getJavaType()).stream()
        .filter(f -> f.getName().equals(fieldName)).findFirst().orElseThrow();
    CriteriaJoin cj = field.getAnnotation(CriteriaJoin.class);
    JoinType jt = JoinType.INNER;
    if (cj != null && cj.type() != null) {
      jt = cj.type();
    }

    return join.join(fieldName, jt);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <X, Y> Path<X> get(From<X, Y> join, String fieldName) {
    Path<X> attribute = null;
    Field field = FieldUtils.getAllFieldsList(join.getJavaType()).stream()
        .filter(f -> f.getName().equals(fieldName)).findFirst().orElseThrow();

    if (isBasicField(field)) {
      attribute = join.get(fieldName);
    } else {
      attribute = (Path<X>) this.join(join, fieldName);
    }

    return attribute;
  }

  private boolean isBasicField(Field field) {
    return !Arrays.stream(field.getAnnotations())
        .anyMatch(annotation -> COMPOUND_ENTITY_ANNOTATIONS.contains(annotation.annotationType()));
  }
}
