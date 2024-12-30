package io.trishul.repo.aggregation.service.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import io.trishul.repo.jpa.query.path.provider.PathProvider;
import io.trishul.repo.jpa.query.spec.criteria.AverageSpec;
import io.trishul.repo.jpa.query.spec.criteria.ColumnSpec;
import io.trishul.repo.jpa.query.spec.criteria.CountSpec;
import io.trishul.repo.jpa.query.spec.criteria.CriteriaSpec;
import io.trishul.repo.jpa.query.spec.criteria.MaxSpec;
import io.trishul.repo.jpa.query.spec.criteria.MinSpec;
import io.trishul.repo.jpa.query.spec.criteria.SumSpec;
import org.junit.jupiter.api.Test;

public class AggregationFunctionTest {
  @Test
  public void testGetAggregation_SumFunctionReturnsSumAggregation_WhenPathProviderIsNotNull() {
    PathProvider mProvider = mock(PathProvider.class);
    doReturn(new String[] {"FIELD_1", "FIELD_2"}).when(mProvider).getPath();

    CriteriaSpec<? extends Number> spec = AggregationFunction.SUM.getAggregation(mProvider);

    assertEquals(new SumSpec<>(new ColumnSpec<>(new String[] {"FIELD_1", "FIELD_2"})), spec);
  }

  @Test
  public void testGetAggregation_SumFunctionReturnsSumAggregation_WhenPathsArrayIsNotNull() {
    CriteriaSpec<? extends Number> spec
        = AggregationFunction.SUM.getAggregation("FIELD_1", "FIELD_2");

    assertEquals(new SumSpec<>(new ColumnSpec<>(new String[] {"FIELD_1", "FIELD_2"})), spec);
  }

  @Test
  public void testGetAggregation_CountFunctionReturnsCountAggregation_WhenPathProviderIsNotNull() {
    PathProvider mProvider = mock(PathProvider.class);
    doReturn(new String[] {"FIELD_1", "FIELD_2"}).when(mProvider).getPath();

    CriteriaSpec<? extends Number> spec = AggregationFunction.COUNT.getAggregation(mProvider);

    assertEquals(new CountSpec<>(new ColumnSpec<>(new String[] {"FIELD_1", "FIELD_2"})), spec);
  }

  @Test
  public void testGetAggregation_CountFunctionReturnsCountAggregation_WhenPathsArrayIsNotNull() {
    CriteriaSpec<? extends Number> spec
        = AggregationFunction.COUNT.getAggregation("FIELD_1", "FIELD_2");

    assertEquals(new CountSpec<>(new ColumnSpec<>(new String[] {"FIELD_1", "FIELD_2"})), spec);
  }

  @Test
  public void testGetAggregation_AvgFunctionReturnsAvgAggregation_WhenPathProviderIsNotNull() {
    PathProvider mProvider = mock(PathProvider.class);
    doReturn(new String[] {"FIELD_1", "FIELD_2"}).when(mProvider).getPath();

    CriteriaSpec<? extends Number> spec = AggregationFunction.AVG.getAggregation(mProvider);

    assertEquals(new AverageSpec<>(new ColumnSpec<>(new String[] {"FIELD_1", "FIELD_2"})), spec);
  }

  @Test
  public void testGetAggregation_AvgFunctionReturnsAvgAggregation_WhenPathsArrayIsNotNull() {
    CriteriaSpec<? extends Number> spec
        = AggregationFunction.AVG.getAggregation("FIELD_1", "FIELD_2");

    assertEquals(new AverageSpec<>(new ColumnSpec<>(new String[] {"FIELD_1", "FIELD_2"})), spec);
  }

  @Test
  public void testGetAggregation_MaxFunctionReturnsMaxAggregation_WhenPathProviderIsNotNull() {
    PathProvider mProvider = mock(PathProvider.class);
    doReturn(new String[] {"FIELD_1", "FIELD_2"}).when(mProvider).getPath();

    CriteriaSpec<? extends Number> spec = AggregationFunction.MAX.getAggregation(mProvider);

    assertEquals(new MaxSpec<>(new ColumnSpec<>(new String[] {"FIELD_1", "FIELD_2"})), spec);
  }

  @Test
  public void testGetAggregation_MaxFunctionReturnsMaxAggregation_WhenPathsArrayIsNotNull() {
    CriteriaSpec<? extends Number> spec
        = AggregationFunction.MAX.getAggregation("FIELD_1", "FIELD_2");

    assertEquals(new MaxSpec<>(new ColumnSpec<>(new String[] {"FIELD_1", "FIELD_2"})), spec);
  }

  @Test
  public void testGetAggregation_MinFunctionReturnsMinAggregation_WhenPathProviderIsNotNull() {
    PathProvider mProvider = mock(PathProvider.class);
    doReturn(new String[] {"FIELD_1", "FIELD_2"}).when(mProvider).getPath();

    CriteriaSpec<? extends Number> spec = AggregationFunction.MIN.getAggregation(mProvider);

    assertEquals(new MinSpec<>(new ColumnSpec<>(new String[] {"FIELD_1", "FIELD_2"})), spec);
  }

  @Test
  public void testGetAggregation_MinFunctionReturnsMinAggregation_WhenPathsArrayIsNotNull() {
    CriteriaSpec<? extends Number> spec
        = AggregationFunction.MIN.getAggregation("FIELD_1", "FIELD_2");

    assertEquals(new MinSpec<>(new ColumnSpec<>(new String[] {"FIELD_1", "FIELD_2"})), spec);
  }
}
