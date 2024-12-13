package io.trishul.crud.controller.filter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.trishul.test.model.Dummy;

public class AttributeFilterTest {
    private AttributeFilter filter;

    @BeforeEach
    public void init() {
        filter = new AttributeFilter();
    }

    @Test
    public void testRetain_DoesNotSetNullValue_WhenAllPropertiesAreSpecified() {
        Dummy data = new Dummy("value", 12345L, 0.12345, 12345, 'a', true);
        filter.retain(data, Set.of("string", "longg", "doublee", "integer", "character", "bool"));

        assertEquals("value", data.getString());
        assertEquals(12345L, data.getLongg());
        assertEquals(0.12345, data.getDoublee());
        assertEquals(12345, data.getInteger());
        assertEquals('a', data.getCharacter());
        assertEquals(true, data.getBool());
    }

    @Test
    public void testRetain_SetsNullValueOnUnspecifiedProperties() {
        Dummy data = new Dummy("value", 12345L, 0.12345, 12345, 'a', true);
        filter.retain(data, Set.of("string", "integer", "bool"));

        assertEquals("value", data.getString());
        assertEquals(12345, data.getInteger());
        assertEquals(true, data.getBool());
        assertNull(data.getLongg());
        assertNull(data.getDoublee());
        assertNull(data.getCharacter());
    }

    @Test
    public void testRetain_SetsNullValueOnAllProperties_WhenNoPropertiesAreSpecified() {
        Dummy data = new Dummy("value", 12345L, 0.12345, 12345, 'a', true);
        filter.retain(data, Set.of());

        assertNull(data.getString());
        assertNull(data.getLongg());
        assertNull(data.getDoublee());
        assertNull(data.getInteger());
        assertNull(data.getCharacter());
        assertNull(data.getBool());
    }

    @Test
    public void testRemove_SetsNullValueOnAllProps_WhenAllPropertiesAreSpecified() {
        Dummy data = new Dummy("value", 12345L, 0.12345, 12345, 'a', true);
        filter.remove(data, Set.of("string", "longg", "doublee", "integer", "character", "bool"));

        assertNull(data.getString());
        assertNull(data.getLongg());
        assertNull(data.getDoublee());
        assertNull(data.getInteger());
        assertNull(data.getCharacter());
        assertNull(data.getBool());
    }

    @Test
    public void testRemove_SetsNullValueOnSpecifiedProperties() {
        Dummy data = new Dummy("value", 12345L, 0.12345, 12345, 'a', true);
        filter.remove(data, Set.of("string", "integer", "bool"));

        assertNull(data.getString());
        assertNull(data.getInteger());
        assertNull(data.getBool());
        assertEquals(12345L, data.getLongg());
        assertEquals(0.12345, data.getDoublee());
        assertEquals('a', data.getCharacter());
    }

    @Test
    public void testRemove_DoesNotSetsNullValue_WhenNoPropertiesAreSpecified() {
        Dummy data = new Dummy("value", 12345L, 0.12345, 12345, 'a', true);
        filter.remove(data, Set.of());

        assertEquals("value", data.getString());
        assertEquals(12345L, data.getLongg());
        assertEquals(0.12345, data.getDoublee());
        assertEquals(12345, data.getInteger());
        assertEquals('a', data.getCharacter());
        assertEquals(true, data.getBool());
    }
}