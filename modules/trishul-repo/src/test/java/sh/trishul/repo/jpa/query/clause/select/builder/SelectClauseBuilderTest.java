package sh.trishul.repo.jpa.query.clause.select.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import sh.trishul.repo.jpa.query.path.provider.PathProvider;
import sh.trishul.repo.jpa.query.spec.accumulator.ColumnSpecAccumulator;
import sh.trishul.repo.jpa.query.spec.criteria.ColumnSpec;
import sh.trishul.repo.jpa.query.spec.criteria.CriteriaSpec;

class SelectClauseBuilderTest {
  private SelectClauseBuilder selector;
  private ColumnSpecAccumulator mAccumulator;

  @BeforeEach
  void init() {
    mAccumulator = mock(ColumnSpecAccumulator.class);
    selector = new SelectClauseBuilder(mAccumulator);
  }

  @Test
  void testSelect_PathProvider_AddsAColumnSpecWithPath_WhenProviderIsNotNull() {
    ArgumentCaptor<ColumnSpec<?>> captor = ArgumentCaptor.forClass(ColumnSpec.class);

    PathProvider mProvider = mock(PathProvider.class);
    doReturn(new String[] {"PATH_1", "PATH_2"}).when(mProvider).getPath();

    assertSame(selector, selector.select(mProvider));

    verify(mAccumulator).add(captor.capture());
    assertEquals(new ColumnSpec<>(new String[] {"PATH_1", "PATH_2"}), captor.getValue());
  }

  @Test
  void testSelect_PathProvider_DoesNothign_WhenProviderIsNull() {
    assertSame(selector, selector.select((PathProvider) null));
    verifyNoInteractions(mAccumulator);
  }

  @Test
  void testSelect_StringArray_AddsAColumnSpecWithPath_WhenProviderIsNotNull() {
    ArgumentCaptor<ColumnSpec<?>> captor = ArgumentCaptor.forClass(ColumnSpec.class);

    assertSame(selector, selector.select(new String[] {"PATH_1", "PATH_2"}));

    verify(mAccumulator).add(captor.capture());
    assertEquals(new ColumnSpec<>(new String[] {"PATH_1", "PATH_2"}), captor.getValue());
  }

  @Test
  void testSelect_StringArray_DoesNothing_WhenStringArrayIsNull() {
    assertSame(selector, selector.select((String[]) null));
    verifyNoInteractions(mAccumulator);
  }

  @Test
  void testSelect_CriteriaSpec_AddsTheSpec_WhenSpecIsNotNull() {
    ArgumentCaptor<CriteriaSpec<?>> captor = ArgumentCaptor.forClass(CriteriaSpec.class);

    CriteriaSpec<?> mSpec = mock(CriteriaSpec.class);
    assertSame(selector, selector.select(mSpec));

    verify(mAccumulator).add(captor.capture());
    assertEquals(mSpec, captor.getValue());
  }

  @Test
  void testSelect_CriteriaSpec_DoesNothing_WhenSpecIsNull() {
    assertSame(selector, selector.select((CriteriaSpec<?>) null));
    verifyNoInteractions(mAccumulator);
  }

  @Test
  void testGetSelectClause_ReturnsListOfSelectionFromAccumulator() {
    Root<?> mRoot = mock(Root.class);
    CriteriaBuilder mCb = mock(CriteriaBuilder.class);
    CriteriaQuery<?> mCq = mock(CriteriaQuery.class);

    List<Expression<?>> mExprs = List.of(mock(Expression.class));
    doReturn(mExprs).when(mAccumulator).getColumns(mRoot, mCq, mCb);

    assertEquals(mExprs, selector.getSelectClause(mRoot, mCq, mCb));
  }

  @Test
  void testDefaultConstructor() {
    SelectClauseBuilder builder = new SelectClauseBuilder();
    assertNotNull(builder);
  }
}
