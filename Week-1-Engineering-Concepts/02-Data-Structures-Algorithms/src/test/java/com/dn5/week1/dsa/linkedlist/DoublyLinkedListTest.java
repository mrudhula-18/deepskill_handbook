package com.dn5.week1.dsa.linkedlist;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DoublyLinkedListTest {

    @Test
    void insertFirstAndTraverseForward() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.insertFirst(2);
        list.insertFirst(1);
        assertEquals(List.of(1, 2), list.traverseForward());
    }

    @Test
    void insertLastAndTraverseBackward() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.insertLast(1);
        list.insertLast(2);
        list.insertLast(3);
        assertEquals(List.of(3, 2, 1), list.traverseBackward());
        assertEquals(List.of(1, 2, 3), list.traverseForward());
        assertEquals(3, list.size());
    }

    @Test
    void deleteMiddleElementFixesLinks() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.insertLast("a");
        list.insertLast("b");
        list.insertLast("c");
        assertTrue(list.delete("b"));
        assertEquals(List.of("a", "c"), list.traverseForward());
        assertEquals(List.of("c", "a"), list.traverseBackward());
    }

    @Test
    void deleteHeadAndTailUpdatesBoundaries() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.insertLast(1);
        list.insertLast(2);
        assertTrue(list.delete(1));
        assertTrue(list.delete(2));
        assertEquals(List.of(), list.traverseForward());
        assertEquals(List.of(), list.traverseBackward());
        assertEquals(0, list.size());
    }

    @Test
    void deleteMissingReturnsFalse() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.insertLast(1);
        assertFalse(list.delete(999));
    }

    @Test
    void emptyListTraversals() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        assertEquals(List.of(), list.traverseForward());
        assertEquals(List.of(), list.traverseBackward());
        assertEquals(0, list.size());
    }

    @Test
    void singleElementList() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.insertFirst(7);
        assertEquals(List.of(7), list.traverseForward());
        assertEquals(List.of(7), list.traverseBackward());
    }

    @Test
    void duplicateValuesHandled() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.insertLast(7);
        list.insertLast(7);
        assertEquals(List.of(7, 7), list.traverseForward());
        assertTrue(list.delete(7));
        assertEquals(List.of(7), list.traverseForward());
    }
}
