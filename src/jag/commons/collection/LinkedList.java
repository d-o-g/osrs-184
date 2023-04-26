package jag.commons.collection;

import java.util.Collection;
import java.util.Iterator;

public class LinkedList<T extends Node> implements Iterable<T>, Collection<T> {

    public final Node sentinel;

    public Node tail;

    public LinkedList() {
        sentinel = new Node();
        sentinel.next = sentinel;
        sentinel.previous = sentinel;
    }

    public static void insertBefore(Node n1, Node n2) {
        if (n1.previous != null) {
            n1.unlink();
        }

        n1.previous = n2;
        n1.next = n2.next;
        n1.previous.next = n1;
        n1.next.previous = n1;
    }

    public void pushBack(Node node) {
        if (node.previous != null) {
            node.unlink();
        }

        node.previous = sentinel.previous;
        node.next = sentinel;
        node.previous.next = node;
        node.next.previous = node;
    }

    public T head() {
        Node node = sentinel.next;

        if (node == sentinel) {
            tail = null;
            return null;
        }
        tail = node.next;
        return (T) node;
    }

    public T next() {
        Node node = tail;
        if (node == sentinel) {
            tail = null;
            return null;
        }
        tail = node.next;
        return (T) node;
    }

    public void addLast(Node node) {
        if (node.previous != null) {
            node.unlink();
        }

        node.previous = sentinel;
        node.next = sentinel.next;
        node.previous.next = node;
        node.next.previous = node;
    }

    @Override
    public T[] toArray() {
        Node[] nodes = new Node[size()];

        int ptr = 0;
        for (Node node = sentinel.next; node != sentinel; node = node.next) {
            nodes[ptr++] = node;
        }

        return (T[]) nodes;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator<>(this);
    }

    @Override
    public int size() {
        int i = 0;
        for (Node current = sentinel.next; current != sentinel; current = current.next) {
            ++i;
        }
        return i;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    @Override
    public boolean contains(Object o) {
        throw new RuntimeException();
    }

    @Override
    public T[] toArray(Object[] dst) {
        int current = 0;
        Node node = sentinel.next;
        while (node != sentinel) {
            dst[current++] = node;
            node = node.next;
        }
        return (T[]) dst;
    }

    @Override
    public void clear() {
        while (sentinel.next != sentinel) {
            sentinel.next.unlink();
        }
    }

    @Override
    public boolean add(T node) {
        pushBack(node);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new RuntimeException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new RuntimeException();
    }

    @Override
    public boolean remove(Object o) {
        throw new RuntimeException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new RuntimeException();
    }
}
