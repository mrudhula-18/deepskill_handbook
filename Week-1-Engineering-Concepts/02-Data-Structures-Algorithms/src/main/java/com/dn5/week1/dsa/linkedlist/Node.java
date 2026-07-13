package com.dn5.week1.dsa.linkedlist;

/**
 * Simple generic singly-linked node used by {@link SinglyLinkedList} and
 * {@link CircularLinkedList}. {@link DoublyLinkedList} uses its own private
 * node type since it additionally needs a {@code prev} reference.
 */
public class Node<T> {

    T data;
    Node<T> next;

    public Node(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public Node<T> getNext() {
        return next;
    }
}
