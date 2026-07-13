package com.dn5.week1.dsa.linkedlist;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SinglyLinkedListTest {

    @Test
    void insertFirstAndTraverse() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.insertFirst(2);
        list.insertFirst(1);
        assertEquals(List.of(1, 2), list.traverse());
        assertEquals(2, list.size());
    }

    @Test
    void insertLastAndTraverse() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.insertLast(1);
        list.insertLast(2);
        list.insertLast(3);
        assertEquals(List.of(1, 2, 3), list.traverse());
        assertEquals(3, list.size());
    }

    @Test
    void deleteExistingMiddleElement() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.insertLast("a");
        list.insertLast("b");
        list.insertLast("c");
        assertTrue(list.delete("b"));
        assertEquals(List.of("a", "c"), list.traverse());
    }

    @Test
    void deleteHeadElement() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.insertLast("a");
        list.insertLast("b");
        assertTrue(list.delete("a"));
        assertEquals(List.of("b"), list.traverse());
    }

    @Test
    void deleteMissingElementReturnsFalse() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.insertLast("a");
        assertFalse(list.delete("z"));
        assertEquals(1, list.size());
    }

    @Test
    void searchFindsAndMisses() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.insertLast(10);
        list.insertLast(20);
        assertTrue(list.search(20));
        assertFalse(list.search(99));
    }

    @Test
    void emptyListTraverseAndSize() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        assertEquals(List.of(), list.traverse());
        assertEquals(0, list.size());
        assertFalse(list.search(1));
        assertFalse(list.delete(1));
    }

    @Test
    void singleElementList() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.insertFirst(1);
        assertEquals(List.of(1), list.traverse());
        assertTrue(list.delete(1));
        assertEquals(List.of(), list.traverse());
    }

    @Test
    void duplicateValuesHandled() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.insertLast(5);
        list.insertLast(5);
        list.insertLast(5);
        assertTrue(list.delete(5));
        assertEquals(List.of(5, 5), list.traverse());
        assertEquals(2, list.size());
    }
}
