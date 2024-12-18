package io.trishul.model.reflection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.trishul.test.bom.model.Dummy;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReflectionManipulatorTest {
    public static class TestData {
        public TestData() {}

        public TestData(int x) {
            this.setX(x);
        }

        int x;

        public int getX() {
            return this.x;
        }

        public final void setX(int x) {
            this.x = x;
        }

        int y;

        public int getY() {
            return this.y;
        }

        public final void setY(int y) {
            this.y = y;
        }
    }

    private ReflectionManipulator util;

    @BeforeEach
    public void init() {
        this.util = ReflectionManipulator.INSTANCE;
    }

    @Test
    public void testEquals_ReturnsTrue_WhenBothArgsAreNull() {
        assertTrue(this.util.equals(null, null));
    }

    @Test
    public void testEquals_ReturnsFalse_WhenArgIsNullAndTestDataIsNot() {
        assertFalse(this.util.equals(new Object(), null));
        assertFalse(this.util.equals(null, new Object()));
    }

    @Test
    public void testEquals_ReturnsTrue_WhenObjectsAreSame() {
        final Object o = new Object();
        assertTrue(this.util.equals(o, o));
    }

    @Test
    public void testEquals_ReturnsFalse_WhenObjectsAreNotEquals() {
        final TestData a = new TestData();
        final TestData b = new TestData();
        a.x = 10;
        b.x = 20;
        assertFalse(this.util.equals(a, b));
    }

    @Test
    public void testHashCode_ReturnsSameHashCode_WhenObjectIsUnchanged() {
        final TestData a = new TestData();

        int hashCode = util.hashCode(a);
        assertEquals(hashCode, util.hashCode(a));
        assertEquals(hashCode, util.hashCode(a));
        assertEquals(hashCode, util.hashCode(a));
    }

    @Test
    public void testHashCode_ReturnsDifferentHashCode_WhenObjectPropertiesChange() {
        final TestData a = new TestData();

        a.x = 0;
        int hashCode0 = util.hashCode(a);

        a.x = 1;
        int hashCode1 = util.hashCode(a);

        a.x = 2;
        int hashCode2 = util.hashCode(a);

        assertNotEquals(hashCode0, hashCode1);
        assertNotEquals(hashCode1, hashCode2);
        assertNotEquals(hashCode2, hashCode0);
    }

    @Test
    public void testHashCode_ReturnsSameHash_WhenDifferentObjectsHaveSamePropertyValues() {
        final TestData a = new TestData();
        final TestData b = new TestData();
        assertEquals(util.hashCode(a), util.hashCode(b));

        a.x = 10;
        b.x = 10;
        assertEquals(util.hashCode(a), util.hashCode(b));

        a.y = 20;
        b.y = 20;
        assertEquals(util.hashCode(a), util.hashCode(b));
    }

    @Test
    public void testHashCode_ReturnsDifferentHashCode_WhenObjectsHaveDifferentPropertyValues() {
        final TestData a = new TestData();
        final TestData b = new TestData();

        a.x = 1;
        b.x = 2;
        assertNotEquals(util.hashCode(a), util.hashCode(b));

        a.y = 2;
        b.y = 1;
        assertNotEquals(util.hashCode(a), util.hashCode(b));
    }

    @Test
    public void testOuterJoin_ThrowsException_WhenEitherObjectIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> this.util.copy(null, null, pd -> true),
                "Outer Joins can not be on null objects");
        assertThrows(
                NullPointerException.class,
                () -> this.util.copy(null, new Dummy(), pd -> true),
                "Outer Joins can not be on null objects");
        assertThrows(
                NullPointerException.class,
                () -> this.util.copy(new Dummy(), null, pd -> true),
                "Outer Joins can not be on null objects");
    }

    @Test
    public void testOuterJoin_SkipsProperty_WhenGetterIsMissing() {
        class TestDataWithoutGetter {
            TestDataWithoutGetter(int x) {
                this.x = x;
            }

            int x;

            public final void setX(int x) {
                this.x = x;
            }
        }
        final TestDataWithoutGetter o1 = new TestDataWithoutGetter(10);
        final TestDataWithoutGetter o2 = new TestDataWithoutGetter(20);

        this.util.copy(o1, o2, pd -> true);

        assertEquals(10, o1.x);
        assertEquals(20, o2.x);
    }

    @Test
    public void testOuterJoin_SkipsProperty_WhenSetterIsMissing() {
        class TestDataWithoutSetter {
            TestDataWithoutSetter(int x) {
                this.x = x;
            }

            int x;

            public int getX() {
                return this.x;
            }
        }
        final TestDataWithoutSetter o1 = new TestDataWithoutSetter(10);
        final TestDataWithoutSetter o2 = new TestDataWithoutSetter(20);

        this.util.copy(o1, o2, pd -> true);

        assertEquals(10, o1.getX());
        assertEquals(20, o2.getX());
    }

    @Test
    public void testOuterJoin_SkipsProperty_WhenPredicateReturnsFalse() {
        final TestData o1 = new TestData(10);
        final TestData o2 = new TestData(20);

        this.util.copy(o1, o2, pd -> false);

        assertEquals(10, o1.getX());
        assertEquals(20, o2.getX());
    }

    @Test
    public void
            testOuterJoin_CopiesProperty_WhenGetterAndSetterAreBothPresentAndPredicateReturnsTrue() {
        final TestData o1 = new TestData(10);
        final TestData o2 = new TestData(20);

        this.util.copy(o1, o2, pd -> true);

        assertEquals(20, o1.getX());
        assertEquals(20, o2.getX());
    }

    @Test
    public void testGetPropertyNames_ReturnsNull_WhenClassIsNull() {
        assertNull(this.util.getPropertyNames(null, null));
    }

    @Test
    public void testGetPropertyNames_ReturnsListOfPropertyNamesForTheGivenClass() {
        final Set<String> props = this.util.getPropertyNames(TestData.class, null);

        assertEquals(Set.of("x", "y", "class"), props);
    }

    @Test
    public void
            testGetPropertyNames_ReturnsListOfPropertyNamesWithExclusions_WhenExcludedContainsClassProps() {
        Set<String> props = this.util.getPropertyNames(TestData.class, Set.of("y", "z"));

        props = this.util.getPropertyNames(TestData.class, Set.of("y", "z"));

        assertEquals(Set.of("x", "class"), props);
    }

    @Test
    public void testConstruct_ReturnsNull_WhenClassIsNull() {
        assertNull(this.util.construct(null));
    }

    @Test
    public void testConstruct_ReturnsObjectOfGivenClass_WhenClassIsNotNull() {
        final TestData o = this.util.construct(TestData.class);

        assertNotNull(o);
    }

    @Test
    public void testConstruct_ReturnsObjectsWithValues_WhenClassAndPropertiesAreNotNull() {
        final TestData o = this.util.construct(TestData.class, Map.of("x", 1, "y", 2));

        assertEquals(1, o.getX());
        assertEquals(2, o.getY());
    }
}
