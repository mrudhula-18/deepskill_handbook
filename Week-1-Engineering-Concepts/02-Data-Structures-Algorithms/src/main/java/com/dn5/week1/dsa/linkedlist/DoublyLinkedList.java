package com.dn5.week1.dsa.linkedlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Doubly-linked list with its own {@code prev}/{@code next} node type, supporting
 * head/tail insertion, deletion of the first matching element, and both forward
 * and backward traversal.
 */
public class DoublyLinkedList<T> {

    private static final class DNode<T> {
        T data;
        DNode<T> prev;
        DNode<T> next;

        DNode(T data) {
            this.data = data;
        }
    }

    private DNode<T> head;
    private DNode<T> tail;
    private int size;

    public void insertFirst(T data) {
        DNode<T> node = new DNode<>(data);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        size++;
    }

    public void insertLast(T data) {
        DNode<T> node = new DNode<>(data);
        if (tail == null) {
            head = node;
            tail = node;
        } else {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        size++;
    }

    /**
     * Removes the first node whose data matches {@code data}, fixing up head/tail
     * and neighbouring links as needed.
     *
     * @return true if a node was removed, false if no match was found
     */
    public boolean delete(T data) {
        DNode<T> cur = head;
        while (cur != null) {
            if (Objects.equals(cur.data, data)) {
                if (cur.prev != null) {
                    cur.prev.next = cur.next;
                } else {
                    head = cur.next;
                }
                if (cur.next != null) {
                    cur.next.prev = cur.prev;
                } else {
                    tail = cur.prev;
                }
                size--;
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    public List<T> traverseForward() {
        List<T> result = new ArrayList<>();
        DNode<T> cur = head;
        while (cur != null) {
            result.add(cur.data);
            cur = cur.next;
        }
        return result;
    }

    public List<T> traverseBackward() {
        List<T> result = new ArrayList<>();
        DNode<T> cur = tail;
        while (cur != null) {
            result.add(cur.data);
            cur = cur.prev;
        }
        return result;
    }

    public int size() {
        return size;
    }
}
