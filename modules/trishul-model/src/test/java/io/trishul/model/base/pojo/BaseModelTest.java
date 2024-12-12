package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import io.company.brewcraft.data.CheckedFunction;
import io.company.brewcraft.util.JsonMapper;
import io.company.brewcraft.util.entity.ReflectionManipulator;

@SuppressWarnings("unchecked")
public class BaseModelTest {
    class TestBaseModel extends BaseModel {
        private Integer x;
        public TestBaseModel(Integer x, ReflectionManipulator util, JsonMapper jsonMapper) {
            super(util, jsonMapper);
            setX(x);
        }

        public Integer getX() {
            return this.x;
        }

        public void setX(Integer x) {
            this.x = x;
        }
    }

    private TestBaseModel model;
    private TestBaseModel other;
    private ReflectionManipulator util;
    private JsonMapper jsonMapper;

    @BeforeEach
    public void init() {
        util = spy(ReflectionManipulator.INSTANCE);
        jsonMapper = spy(JsonMapper.INSTANCE);

        model = new TestBaseModel(0, util, jsonMapper);
        other = new TestBaseModel(12345, util, jsonMapper);
    }

    @AfterAll
    public static void tearDown() {
        BaseModel.util = ReflectionManipulator.INSTANCE;
        BaseModel.jsonMapper = JsonMapper.INSTANCE;
    }

    @Test
    public void testEquals_ReturnsTrue_WhenUtilReturnsTrue() {
        doReturn(true).when(util).equals(model, other);
        assertTrue(model.equals(other));
    }

    @Test
    public void testEquals_ReturnsFalse_WhenUtilReturnsFalse() {
        doReturn(false).when(util).equals(model, other);
        assertFalse(model.equals(other));
    }

    @Test
    public void testOuterJoin_CallsUtilOuterJoinWithPredicate_ThatReturnsFalseWhenGetterReturnsNull() throws ReflectiveOperationException {
        ArgumentCaptor<CheckedFunction<Boolean, PropertyDescriptor, ReflectiveOperationException>> captor = ArgumentCaptor.forClass(CheckedFunction.class);
        doNothing().when(util).copy(eq(model), eq(other), captor.capture());
        model.outerJoin(other);

        Method idGetter = TestBaseModel.class.getDeclaredMethod("getX");
        PropertyDescriptor mPd = mock(PropertyDescriptor.class);
        doReturn(idGetter).when(mPd).getReadMethod();
        other.setX(null);
        assertFalse(captor.getValue().apply(mPd));
    }

    @Test
    public void testOuterJoin_CallsUtilOuterJoinWithPredicate_ThatReturnsTrueWhenGetterReturnsNonNull() throws ReflectiveOperationException {
        ArgumentCaptor<CheckedFunction<Boolean, PropertyDescriptor, ReflectiveOperationException>> captor = ArgumentCaptor.forClass(CheckedFunction.class);
        doNothing().when(util).copy(eq(model), eq(other), captor.capture());
        model.outerJoin(other);

        Method idGetter = TestBaseModel.class.getDeclaredMethod("getX");
        PropertyDescriptor mPd = mock(PropertyDescriptor.class);
        doReturn(idGetter).when(mPd).getReadMethod();
        other.setX(12345);
        assertTrue(captor.getValue().apply(mPd));
    }

    @Test
    public void testToString_ReturnsJsonifiedStringObject() {
        doReturn("{\"key\": \"value\"}").when(jsonMapper).writeString(model);

        String str = model.toString();

        assertEquals("{\"key\": \"value\"}", str);
    }

    @Test
    public void testHashCode_DelegatesCallToReflectionUtil() {
        doReturn(100).when(util).hashCode(model);

        assertEquals(100, model.hashCode());
    }
}
