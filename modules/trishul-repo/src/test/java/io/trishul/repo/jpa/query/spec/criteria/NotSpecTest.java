package io.trishul.repo.jpa.query.spec.criteria;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NotSpecTest {
    private CriteriaSpec<Boolean> spec;

    private CriteriaSpec<Boolean> mDelegate;
    private Expression<Boolean> mExpr;

    private CriteriaBuilder mCb;
    private CriteriaQuery<?> mCq;
    private Root<?> mRoot;

    @BeforeEach
    public void init() {
        mCb = mock(CriteriaBuilder.class);
        mCq = mock(CriteriaQuery.class);
        mRoot = mock(Root.class);

        mDelegate = mock(CriteriaSpec.class);
        mExpr = mock(Predicate.class);
        doReturn(mExpr).when(mDelegate).getExpression(mRoot, mCq, mCb);
    }

    @Test
    public void testGetExpression_ReturnsNotExpressionOnDelegatePath() {
        Predicate mNotExpr = mock(Predicate.class);
        doReturn(mNotExpr).when(mCb).not(mExpr);

        spec = new NotSpec(mDelegate);
        assertSame(mNotExpr, spec.getExpression(mRoot, mCq, mCb));
    }
}
