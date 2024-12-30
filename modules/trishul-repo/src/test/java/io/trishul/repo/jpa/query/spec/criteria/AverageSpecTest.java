package io.trishul.repo.jpa.query.spec.criteria;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("unchecked")
public class AverageSpecTest {
  private CriteriaSpec<Double> spec;

  private CriteriaSpec<Double> mDelegate;
  private Expression<Double> mExpr;

  private CriteriaBuilder mCb;
  private CriteriaQuery<?> mCq;
  private Root<?> mRoot;

  @BeforeEach
  public void init() {
    mCb = mock(CriteriaBuilder.class);
    mCq = mock(CriteriaQuery.class);
    mRoot = mock(Root.class);

    mDelegate = mock(CriteriaSpec.class);
    mExpr = mock(Expression.class);
    doReturn(mExpr).when(mDelegate).getExpression(mRoot, mCq, mCb);
  }

  @Test
  public void testGetExpression_ReturnsAverageExpressionOnDelegatePath() {
    Predicate mAvgExpr = mock(Predicate.class);
    doReturn(mAvgExpr).when(mCb).avg(mExpr);

    spec = new AverageSpec<>(mDelegate);
    assertSame(mAvgExpr, spec.getExpression(mRoot, mCq, mCb));
  }
}
