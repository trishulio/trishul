package sh.trishul.repo.jpa.query.spec.accumulator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.trishul.repo.jpa.query.spec.criteria.AndSpec;
import sh.trishul.repo.jpa.query.spec.criteria.CriteriaSpec;
import sh.trishul.repo.jpa.query.spec.criteria.NotSpec;

class PredicateSpecAccumulatorTest {
  private PredicateSpecAccumulator accumulator;

  private List<CriteriaSpec<Boolean>> mAggregations;

  @BeforeEach
  void init() {
    mAggregations = new ArrayList<>();
    accumulator = new PredicateSpecAccumulator(mAggregations);
  }

  @Test
  void testAdd_WrapsAggregationInAndSpecAndAddsItToTheList_WhenNotFlagIsNotSet() {
    CriteriaSpec<Boolean> spec = mock(CriteriaSpec.class);

    assertSame(accumulator, accumulator.setIsNot(false));
    accumulator.add(spec);

    CriteriaSpec<Boolean> expected = new AndSpec(spec);
    assertEquals(List.of(expected), mAggregations);
  }

  @Test
  void testAdd_WrapsAggregationInNotAndSpecAndAddsItToTheList_WhenNotFlagIsSet() {
    CriteriaSpec<Boolean> spec = mock(CriteriaSpec.class);

    assertSame(accumulator, accumulator.setIsNot(true));
    accumulator.add(spec);

    CriteriaSpec<Boolean> expected = new AndSpec(new NotSpec(spec));
    assertEquals(List.of(expected), mAggregations);
  }

  @Test
  void testGetPredicates_CollectsAllSpecExpressionsAndReturnsArray() {
    CriteriaBuilder mCb = mock(CriteriaBuilder.class);
    CriteriaQuery<?> mCq = mock(CriteriaQuery.class);
    Root<?> mRoot = mock(Root.class);

    doAnswer(i -> i.getArgument(0, Predicate.class)).when(mCb).and(any(Predicate.class));

    CriteriaSpec<Boolean> spec1 = mock(CriteriaSpec.class);
    Predicate mExpr1 = mock(Predicate.class);
    doReturn(mExpr1).when(spec1).getExpression(mRoot, mCq, mCb);

    CriteriaSpec<Boolean> spec2 = mock(CriteriaSpec.class);
    Predicate mExpr2 = mock(Predicate.class);
    doReturn(mExpr2).when(spec2).getExpression(mRoot, mCq, mCb);

    accumulator.add(spec1);
    accumulator.add(spec2);

    assertArrayEquals(new Predicate[] {mExpr1, mExpr2}, accumulator.getPredicates(mRoot, mCq, mCb));
  }

  @Test
  void testNoArgConstructor() {
    PredicateSpecAccumulator acc = new PredicateSpecAccumulator();
    assertNotNull(acc);
  }

  @Test
  void testAdd_ResetsFlagsAndReturns_WhenIsPredicateIsNull() {
    CriteriaSpec<Boolean> spec = mock(CriteriaSpec.class);
    assertSame(accumulator, accumulator.setIsPredicate(null));
    accumulator.setIsNot(true);

    accumulator.add(spec);

    assertEquals(0, mAggregations.size());
  }

  @Test
  void testAdd_ResetsFlagsAndReturns_WhenIsPredicateIsFalse() {
    CriteriaSpec<Boolean> spec = mock(CriteriaSpec.class);
    assertSame(accumulator, accumulator.setIsPredicate(false));
    accumulator.setIsNot(true);

    accumulator.add(spec);

    assertEquals(0, mAggregations.size());
  }
}
