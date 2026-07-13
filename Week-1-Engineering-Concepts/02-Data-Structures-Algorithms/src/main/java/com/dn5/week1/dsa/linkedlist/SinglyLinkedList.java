package com.dn5.week1.dsa.linkedlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classic singly-linked list supporting head/tail insertion, deletion of the
 * first matching element, search, and forward traversal.
 */
public class SinglyLinkedList<T> {

    private Node<T> head;
    private int size;

    public void insertFirst(T data) {
        Node<T> node = new Node<>(data);
        node.next = head;
        head = node;
        size++;
    }

    public void insertLast(T data) {
        Node<T> node = new Node<>(data);
        if (head == null) {
            head = node;
        } else {
            Node<T> cur = head;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = node;
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
        if (Objects.equals(head.data, data)) {
            head = head.next;
            size--;
            return true;
        }
        Node<T> cur = head;
        while (cur.next != null) {
            if (Objects.equals(cur.next.data, data)) {
                cur.next = cur.next.next;
                size--;
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    public boolean search(T data) {
        Node<T> cur = head;
        while (cur != null) {
            if (Objects.equals(cur.data, data)) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    public List<T> traverse() {
        List<T> result = new ArrayList<>();
        Node<T> cur = head;
        while (cur != null) {
            result.add(cur.data);
            cur = cur.next;
        }
        return result;
    }

    public int size() {
        return size;
    }
}
