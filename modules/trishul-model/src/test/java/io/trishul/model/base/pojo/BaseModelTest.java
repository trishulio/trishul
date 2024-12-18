// TODO: Enable this. Disabled because the security reason disabled BaseModel
// constructor used for
// this test.
// package io.trishul.model.base.pojo;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.Mockito.doNothing;
// import static org.mockito.Mockito.doReturn;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.spy;

// import io.trishul.base.types.lambda.CheckedFunction;
// import io.trishul.model.json.JsonMapper;
// import io.trishul.model.reflection.ReflectionManipulator;
// import java.beans.PropertyDescriptor;
// import java.lang.reflect.Method;
// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.ArgumentCaptor;

// @SuppressWarnings("unchecked")
// public class BaseModelTest {
// class TestBaseModel extends BaseModel {
// private Integer x;

// public TestBaseModel(Integer x) {
// setX(x);
// }

// public Integer getX() {
// return this.x;
// }

// public final void setX(Integer x) {
// this.x=x;
// }
// }

// private TestBaseModel model;
// private TestBaseModel other;

// @BeforeEach
// public void init() {
// model=new TestBaseModel(0);
// other=new TestBaseModel(12345);
// }

// @Test
// public void testEquals_ReturnsTrue_WhenUtilReturnsTrue() {
// doReturn(true).when(util).equals(model,other);
// assertTrue(model.equals(other));
// }

// @Test
// public void testEquals_ReturnsFalse_WhenUtilReturnsFalse() {
// doReturn(false).when(util).equals(model,other);
// assertFalse(model.equals(other));
// }

// @Test
// public void
// testOuterJoin_CallsUtilOuterJoinWithPredicate_ThatReturnsFalseWhenGetterReturnsNull()
// throws ReflectiveOperationException {
// ArgumentCaptor<CheckedFunction<Boolean, PropertyDescriptor,
// ReflectiveOperationException>> captor=ArgumentCaptor
// .forClass(CheckedFunction.class);
// doNothing().when(util).copy(eq(model),eq(other),captor.capture());
// model.outerJoin(other);

// Method idGetter=TestBaseModel.class.getDeclaredMethod("getX");
// PropertyDescriptor mPd=mock(PropertyDescriptor.class);
// doReturn(idGetter).when(mPd).getReadMethod();
// other.setX(null);
// assertFalse(captor.getValue().apply(mPd));
// }

// @Test
// public void
// testOuterJoin_CallsUtilOuterJoinWithPredicate_ThatReturnsTrueWhenGetterReturnsNonNull()
// throws ReflectiveOperationException {
// ArgumentCaptor<CheckedFunction<Boolean, PropertyDescriptor,
// ReflectiveOperationException>> captor=ArgumentCaptor
// .forClass(CheckedFunction.class);
// doNothing().when(util).copy(eq(model),eq(other),captor.capture());
// model.outerJoin(other);

// Method idGetter=TestBaseModel.class.getDeclaredMethod("getX");
// PropertyDescriptor mPd=mock(PropertyDescriptor.class);
// doReturn(idGetter).when(mPd).getReadMethod();
// other.setX(12345);
// assertTrue(captor.getValue().apply(mPd));
// }

// @Test
// public void testToString_ReturnsJsonifiedStringObject() {
// doReturn("{\"key\": \"value\"}").when(jsonMapper).writeString(model);

// String str=model.toString();

// assertEquals("{\"key\": \"value\"}",str);
// }

// @Test
// public void testHashCode_DelegatesCallToReflectionUtil() {
// doReturn(100).when(util).hashCode(model);

// assertEquals(100,model.hashCode());
// }
// }
