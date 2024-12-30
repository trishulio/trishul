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

public class NullSpecTest {
  private CriteriaSpec<?> spec;

  private Root<?> mRoot;
  private CriteriaBuilder mCb;
  private CriteriaQuery<?> mCq;

  @BeforeEach
  public void init() {
    mRoot = mock(Root.class);
    mCb = mock(CriteriaBuilder.class);
    mCq = mock(CriteriaQuery.class);
  }

  @Test
  public void testGetAggregation_ReturnsNullLiteralWithIntegerClass() {
    Expression<Integer> mExpr = mock(Expression.class);
    doReturn(mExpr).when(mCb).nullLiteral(Integer.class);

    spec = new NullSpec();

    assertSame(mExpr, spec.getExpression(mRoot, mCq, mCb));
  }
}
