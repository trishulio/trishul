package io.company.brewcraft.service.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EntityNotFoundExceptionTest {
    @Test
    public void testConstructor_StringString_SetsId() {
        EntityNotFoundException exception = new EntityNotFoundException("EntityTest", "idTest");
        assertEquals("EntityTest not found with id: idTest", exception.getMessage());
    }

    @Test
    public void testConstructor_StringObject_SetsNullIdWhenObjectIsNull() {
        EntityNotFoundException exception = new EntityNotFoundException("EntityTest", null);
        assertEquals("EntityTest not found with id: null", exception.getMessage());
    }

    @Test
    public void testConstructor_StringObject_SetsIdStringWhenObjectIsNotNull() {
        class Id {
            String str;
            public Id(String str) {
                this.str = str;
            }

            @Override
            public String toString() {
                return str;
            }
        }
        EntityNotFoundException exception = new EntityNotFoundException("EntityTest", new Id("ID"));
        assertEquals("EntityTest not found with id: ID", exception.getMessage());
    }

    @Test
    public void testConstructor_StringStringString() {
        EntityNotFoundException exception = new EntityNotFoundException("EntityTest", "FIELD", "ID");
        assertEquals("EntityTest not found with FIELD: ID", exception.getMessage());
    }
}
