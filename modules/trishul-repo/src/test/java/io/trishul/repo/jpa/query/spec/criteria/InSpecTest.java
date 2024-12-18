package io.trishul.repo.jpa.query.spec.criteria;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InSpecTest {
    private CriteriaSpec<Boolean> spec;

    private CriteriaSpec<String> mDelegate;
    private Expression<String> mExpr;

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
    public void testGetExpression_ReturnsInSpecExpressionOnDelegatePath() {
        Expression<Boolean> mInExpr = mock(Predicate.class);
        doReturn(mInExpr).when(mExpr).in(Set.of("val1", "val2"));

        spec = new InSpec<>(mDelegate, Set.of("val1", "val2"));
        assertSame(mInExpr, spec.getExpression(mRoot, mCq, mCb));
    }
}
