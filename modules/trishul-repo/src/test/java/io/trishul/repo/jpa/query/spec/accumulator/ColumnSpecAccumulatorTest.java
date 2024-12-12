package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.repository.ColumnSpecAccumulator;
import io.company.brewcraft.service.CriteriaSpec;

public class ColumnSpecAccumulatorTest {
    private ColumnSpecAccumulator accumulator;

    private Root<?> mRoot = mock(Root.class);
    private CriteriaBuilder mCb = mock(CriteriaBuilder.class);
    private CriteriaQuery<?> mCq = mock(CriteriaQuery.class);

    @BeforeEach
    public void init() {
        accumulator = new ColumnSpecAccumulator();
    }

    @Test
    public void testAdd_AddsSpecsToTheAccumulator_WhenSpecIsNotNull() {
        CriteriaSpec<?> mSpec1 = mock(CriteriaSpec.class);
        Expression<?> mSelection1 = mock(Expression.class);
        doReturn(mSelection1).when(mSpec1).getExpression(mRoot, mCq, mCb);

        accumulator.add(mSpec1);

        List<Selection<?>> selection = accumulator.getColumns(mRoot, mCq, mCb);

        assertEquals(List.of(mSelection1), selection);
    }

    @Test
    public void testAddSpec_DoesNotAddSpec_WhenSpecIsNull() {
        accumulator.add(null);
        accumulator.add(null);
        accumulator.add(null);

        List<Selection<?>> selection = accumulator.getColumns(null, null, null);

        assertEquals(List.of(), selection);
    }
}
