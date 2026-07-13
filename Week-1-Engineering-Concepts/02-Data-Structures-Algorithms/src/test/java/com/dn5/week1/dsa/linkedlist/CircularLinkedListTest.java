package com.dn5.week1.dsa.linkedlist;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CircularLinkedListTest {

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void insertAndTraverseTerminatesWithExactCount() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        List<Integer> result = list.traverse();
        // Must stop after exactly one loop back to head, not run forever.
        assertEquals(3, result.size());
        assertEquals(List.of(1, 2, 3), result);
        assertEquals(3, list.size());
    }

    @Test
    void emptyListTraverseReturnsEmpty() {
        CircularLinkedList<String> list = new CircularLinkedList<>();
        assertEquals(List.of(), list.traverse());
        assertEquals(0, list.size());
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void singleElementTraverse() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.insert(42);
        List<Integer> result = list.traverse();
        assertEquals(1, result.size());
        assertEquals(List.of(42), result);
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void deleteMaintainsCircularityAndCorrectCount() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        assertTrue(list.delete(2));
        List<Integer> result = list.traverse();
        assertEquals(2, result.size());
        assertEquals(List.of(1, 3), result);
    }

    @Test
    void deleteHeadElement() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.insert(1);
        list.insert(2);
        assertTrue(list.delete(1));
        assertEquals(List.of(2), list.traverse());
    }

    @Test
    void deleteLastRemainingElement() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.insert(1);
        assertTrue(list.delete(1));
        assertEquals(List.of(), list.traverse());
        assertEquals(0, list.size());
    }

    @Test
    void deleteMissingElementReturnsFalse() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.insert(1);
        assertFalse(list.delete(999));
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void duplicateValuesHandled() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.insert(5);
        list.insert(5);
        List<Integer> result = list.traverse();
        assertEquals(2, result.size());
        assertEquals(List.of(5, 5), result);
    }
}
