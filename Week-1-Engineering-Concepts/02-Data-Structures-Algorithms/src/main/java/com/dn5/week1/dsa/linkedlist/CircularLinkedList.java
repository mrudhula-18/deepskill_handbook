package com.dn5.week1.dsa.linkedlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Singly-linked circular list: the tail's {@code next} always points back to
 * the head. Traversal relies on the tracked {@link #size} (not on comparing
 * back to head mid-loop) so it always terminates after exactly one full pass,
 * even while the list is being mutated.
 */
public class CircularLinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Inserts {@code data} at the end of the list, re-linking the tail's
     * {@code next} back to head so the list stays circular.
     */
    public void insert(T data) {
        Node<T> node = new Node<>(data);
        if (head == null) {
            head = node;
            tail = node;
            node.next = head;
        } else {
            tail.next = node;
            node.next = head;
            tail = node;
        }
        size++;
    }

    /**
     * Removes the first node whose data matches {@code data}.
     *
     * @return true if a node was removed, false if no match was found
     */
    public boolean delete(T data) {
        if (head == null) {
            return false;
        }
        if (size == 1) {
            if (Objects.equals(head.data, data)) {
                head = null;
                tail = null;
                size = 0;
                return true;
            }
            return false;
        }
        Node<T> cur = head;
        Node<T> prev = tail;
        int visited = 0;
        while (visited < size) {
            if (Objects.equals(cur.data, data)) {
                prev.next = cur.next;
                if (cur == head) {
                    head = cur.next;
                }
                if (cur == tail) {
                    tail = prev;
                }
                size--;
                return true;
            }
            prev = cur;
            cur = cur.next;
            visited++;
        }
        return false;
    }

    /**
     * Traverses the list starting at head and stops after exactly {@link #size}
     * elements, guaranteeing termination instead of looping forever around the ring.
     */
    public List<T> traverse() {
        List<T> result = new ArrayList<>();
        if (head == null) {
            return result;
        }
        Node<T> cur = head;
        int count = 0;
        while (count < size) {
            result.add(cur.data);
            cur = cur.next;
            count++;
        }
        return result;
    }

    public int size() {
        return size;
    }
}
