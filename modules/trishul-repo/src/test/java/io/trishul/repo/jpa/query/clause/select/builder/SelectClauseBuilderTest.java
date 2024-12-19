package io.trishul.repo.jpa.query.clause.select.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import io.trishul.repo.jpa.query.path.provider.PathProvider;
import io.trishul.repo.jpa.query.spec.accumulator.ColumnSpecAccumulator;
import io.trishul.repo.jpa.query.spec.criteria.ColumnSpec;
import io.trishul.repo.jpa.query.spec.criteria.CriteriaSpec;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class SelectClauseBuilderTest {
    private SelectClauseBuilder selector;
    private ColumnSpecAccumulator mAccumulator;

    @BeforeEach
    public void init() {
        mAccumulator = mock(ColumnSpecAccumulator.class);
        selector = new SelectClauseBuilder(mAccumulator);
    }

    @Test
    public void testSelect_PathProvider_AddsAColumnSpecWithPath_WhenProviderIsNotNull() {
        ArgumentCaptor<ColumnSpec<?>> captor = ArgumentCaptor.forClass(ColumnSpec.class);

        PathProvider mProvider = mock(PathProvider.class);
        doReturn(new String[] {"PATH_1", "PATH_2"}).when(mProvider).getPath();

        selector.select(mProvider);

        verify(mAccumulator).add(captor.capture());
        assertEquals(new ColumnSpec<>(new String[] {"PATH_1", "PATH_2"}), captor.getValue());
    }

    @Test
    public void testSelect_PathProvider_DoesNothign_WhenProviderIsNull() {
        selector.select((PathProvider) null);
        verifyNoInteractions(mAccumulator);
    }

    @Test
    public void testSelect_StringArray_AddsAColumnSpecWithPath_WhenProviderIsNotNull() {
        ArgumentCaptor<ColumnSpec<?>> captor = ArgumentCaptor.forClass(ColumnSpec.class);

        selector.select(new String[] {"PATH_1", "PATH_2"});

        verify(mAccumulator).add(captor.capture());
        assertEquals(new ColumnSpec<>(new String[] {"PATH_1", "PATH_2"}), captor.getValue());
    }

    @Test
    public void testSelect_StringArray_DoesNothing_WhenStringArrayIsNull() {
        selector.select((String[]) null);
        verifyNoInteractions(mAccumulator);
    }

    @Test
    public void testSelect_CriteriaSpec_AddsTheSpec_WhenSpecIsNotNull() {
        ArgumentCaptor<CriteriaSpec<?>> captor = ArgumentCaptor.forClass(CriteriaSpec.class);

        CriteriaSpec<?> mSpec = mock(CriteriaSpec.class);
        selector.select(mSpec);

        verify(mAccumulator).add(captor.capture());
        assertEquals(mSpec, captor.getValue());
    }

    @Test
    public void testSelect_CriteriaSpec_DoesNothing_WhenSpecIsNull() {
        selector.select((CriteriaSpec<?>) null);
        verifyNoInteractions(mAccumulator);
    }

    @Test
    public void testGetSelectClause_ReturnsListOfSelectionFromAccumulator() {
        Root<?> mRoot = mock(Root.class);
        CriteriaBuilder mCb = mock(CriteriaBuilder.class);
        CriteriaQuery<?> mCq = mock(CriteriaQuery.class);

        List<Expression<?>> mExprs = List.of(mock(Expression.class));
        doReturn(mExprs).when(mAccumulator).getColumns(mRoot, mCq, mCb);

        assertEquals(mExprs, selector.getSelectClause(mRoot, mCq, mCb));
    }
}
