package io.trishul.repo.jpa.query.spec.criteria;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QueryLiteralTest {
    private CriteriaSpec<String> spec;

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
    public void testGetAggregation_ReturnsStringLiteralFromCb() {
        Expression<String> mExpr = mock(Expression.class);
        doReturn(mExpr).when(mCb).literal("VALUE");

        spec = new QueryLiteral<>("VALUE");

        assertSame(mExpr, spec.getExpression(mRoot, mCq, mCb));
    }
}
