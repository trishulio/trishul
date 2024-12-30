package io.trishul.repo.jpa.query.spec.criteria;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("unchecked")
public class DiffSpecTest {
  private CriteriaSpec<?> spec;

  private CriteriaSpec<Double> mDelegateX;
  private Expression<Double> mExprX;

  private CriteriaSpec<Double> mDelegateY;
  private Expression<Double> mExprY;

  private CriteriaBuilder mCb;
  private CriteriaQuery<?> mCq;
  private Root<?> mRoot;

  @BeforeEach
  public void init() {
    mCb = mock(CriteriaBuilder.class);
    mCq = mock(CriteriaQuery.class);
    mRoot = mock(Root.class);

    mDelegateX = mock(CriteriaSpec.class);
    mExprX = mock(Expression.class);
    doReturn(mExprX).when(mDelegateX).getExpression(mRoot, mCq, mCb);

    mDelegateY = mock(CriteriaSpec.class);
    mExprY = mock(Expression.class);
    doReturn(mExprY).when(mDelegateY).getExpression(mRoot, mCq, mCb);
  }

  @Test
  public void testGetExpression_ReturnsDiffExpressionOnDelegatePath() {
    Expression<Double> mDiffExpr = mock(Expression.class);
    doReturn(mDiffExpr).when(mCb).diff(mExprX, mExprY);

    spec = new DiffSpec<>(mDelegateX, mDelegateY);
    assertSame(mDiffExpr, spec.getExpression(mRoot, mCq, mCb));
  }
}
