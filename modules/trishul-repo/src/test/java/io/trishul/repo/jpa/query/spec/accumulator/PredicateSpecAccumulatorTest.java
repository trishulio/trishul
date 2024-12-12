package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.repository.PredicateSpecAccumulator;
import io.company.brewcraft.service.AndSpec;
import io.company.brewcraft.service.CriteriaSpec;
import io.company.brewcraft.service.NotSpec;

public class PredicateSpecAccumulatorTest {
    private PredicateSpecAccumulator accumulator;

    private List<CriteriaSpec<Boolean>> mAggregations;

    @BeforeEach
    public void init() {
        mAggregations = new ArrayList<>();
        accumulator = new PredicateSpecAccumulator(mAggregations);
    }

    @Test
    public void testAdd_WrapsAggregationInAndSpecAndAddsItToTheList_WhenNotFlagIsNotSet() {
        CriteriaSpec<Boolean> spec = mock(CriteriaSpec.class);

        accumulator.setIsNot(false);
        accumulator.add(spec);

        CriteriaSpec<Boolean> expected = new AndSpec(spec);
        assertEquals(List.of(expected), mAggregations);
    }

    @Test
    public void testAdd_WrapsAggregationInNotAndSpecAndAddsItToTheList_WhenNotFlagIsSet() {
        CriteriaSpec<Boolean> spec = mock(CriteriaSpec.class);

        accumulator.setIsNot(true);
        accumulator.add(spec);

        CriteriaSpec<Boolean> expected = new AndSpec(new NotSpec(spec));
        assertEquals(List.of(expected), mAggregations);
    }

    @Test
    public void testGetPredicates_CollectsAllSpecExpressionsAndReturnsArray() {
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

        assertArrayEquals(new Predicate[] { mExpr1, mExpr2 }, accumulator.getPredicates(mRoot, mCq, mCb));
    }
}
