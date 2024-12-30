package io.trishul.repo.jpa.query.clause.where.builder;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

@SuppressWarnings({"unchecked"})
public class WhereClauseBuilderTest {
  private WhereClauseBuilder builder;

  private WhereClauseBuilderDelegate mDelegate;

  @BeforeEach
  public void init() {
    mDelegate = mock(WhereClauseBuilderDelegate.class);
    builder = new WhereClauseBuilderWrapper(mDelegate);
  }

  @Test
  public void testBuilder_ReturnsANewInstanceOfCriteriaSpecBuilder() {
    WhereClauseBuilder anotherBuilder = WhereClauseBuilder.builder();

    assertNotSame(builder, anotherBuilder);
    assertTrue(builder instanceof WhereClauseBuilderWrapper,
        String.format("WhereClauseBuilder.builder() unexpectedly returned an instance of class: %s",
            builder.getClass().getSimpleName()));
  }

  @Test
  public void testIsNull_ArrayStringArgs_DelegatesArgumentsByWrappingInArrays() {
    WhereClauseBuilder ret = builder.isNull("PATH");

    assertSame(builder, ret);
    verify(mDelegate, times(1)).isNull(new String[] {"PATH"});
  }

  @Test
  public void testIsNull_ArrayArrayArgs_DelegatesArguments() {
    WhereClauseBuilder ret = builder.isNull(new String[] {"PATH"});

    assertSame(builder, ret);
    verify(mDelegate, times(1)).isNull(new String[] {"PATH"});
  }

  @Test
  public void testIn_ArrayStringArgs_DelegatesArgumentsByWrappingInArrays() {
    WhereClauseBuilder ret = builder.in("PATH", List.of("v1"));

    assertSame(builder, ret);
    verify(mDelegate, times(1)).in(new String[] {"PATH"}, List.of("v1"));
  }

  @Test
  public void testIn_ArrayArrayArgs_DelegatesArguments() {
    WhereClauseBuilder ret = builder.in(new String[] {"PATH"}, List.of("v1"));

    assertSame(builder, ret);
    verify(mDelegate, times(1)).in(new String[] {"PATH"}, List.of("v1"));
  }

  @Test
  public void testLike_ArrayStringArgs_DelegatesArgumentsByWrappingInArrays() {
    WhereClauseBuilder ret = builder.like("PATH", Set.of("v1"));

    assertSame(builder, ret);
    verify(mDelegate, times(1)).like(new String[] {"PATH"}, Set.of("v1"));
  }

  @Test
  public void testLike_ArrayArrayArgs_DelegatesArguments() {
    WhereClauseBuilder ret = builder.like(new String[] {"PATH"}, Set.of("v1"));

    assertSame(builder, ret);
    verify(mDelegate, times(1)).like(new String[] {"PATH"}, Set.of("v1"));
  }

  @Test
  public <T extends Comparable<T>> void testBetween_ArrayStringArgs_DelegatesArgumentsByWrappingInArrays() {
    T c1 = (T) mock(Comparable.class);
    T c2 = (T) mock(Comparable.class);

    WhereClauseBuilder ret = builder.between("PATH", c1, c2);

    assertSame(builder, ret);
    verify(mDelegate, times(1)).between(new String[] {"PATH"}, c1, c2);
  }

  @Test
  public <T extends Comparable<T>> void testBetween_ArrayArrayArgs_DelegatesArguments() {
    T c1 = (T) mock(Comparable.class);
    T c2 = (T) mock(Comparable.class);

    WhereClauseBuilder ret = builder.between(new String[] {"PATH"}, c1, c2);

    assertSame(builder, ret);
    verify(mDelegate, times(1)).between(new String[] {"PATH"}, c1, c2);
  }

  @Test
  public void testNot_CallsNotOnDelegate() {
    WhereClauseBuilder ret = builder.not();

    assertSame(builder, ret);
    verify(mDelegate, times(1)).not();
  }

  @Test
  public void testPredicate_CallsPredicateOnDelegate() {
    WhereClauseBuilder ret = builder.predicate(true);

    assertSame(builder, ret);
    verify(mDelegate, times(1)).predicate(true);
  }

  @Test
  public void testNegatePredicate_CallsDelegateWithNull_WhenPredicateIsNull() {
    WhereClauseBuilder ret = builder.negatePredicate(null);

    assertSame(builder, ret);
    verify(mDelegate, times(1)).predicate(null);
  }

  @Test
  public void testNegatePredicate_CallsDelegateWithFalse_WhenPredicateIsTrue() {
    WhereClauseBuilder ret = builder.negatePredicate(true);

    assertSame(builder, ret);
    verify(mDelegate, times(1)).predicate(false);
  }

  @Test
  public void testNegatePredicate_CallsDelegateWithTrue_WhenPredicateIsFalse() {
    WhereClauseBuilder ret = builder.negatePredicate(false);

    assertSame(builder, ret);
    verify(mDelegate, times(1)).predicate(true);
  }

  @Test
  public void testBuild_ReturnsSpecificationFromDelegate() {
    Specification<Object> mSpec = mock(Specification.class);
    doReturn(mSpec).when(mDelegate).build();

    Specification<Object> spec = builder.build();

    assertSame(mSpec, spec);
  }
}
