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

    public boolean _pushBack(Node node) {
        pushBack(node);
        return true;
    }

    public T[] toArray() {
        Node[] nodes = new Node[_size()];

        int ptr = 0;
        for (Node node = sentinel.next; node != sentinel; node = node.next) {
            nodes[ptr++] = node;
        }

        return (T[]) nodes;
    }

    public int _size() {
        int i = 0;
        for (Node current = sentinel.next; current != sentinel; current = current.next) {
            ++i;
        }
        return i;
    }

    public void _clear() {
        while (sentinel.next != sentinel) {
            sentinel.next.unlink();
        }

    }

    public T _head() {
        Node node = sentinel.next;

        if (node == sentinel) {
            tail = null;
            return null;
        }
        tail = node.next;
        return (T) node;
    }

    public boolean _isEmpty() {
        return sentinel.next == sentinel;
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
        return _head();
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

    public Iterator<T> iterator() {
        return new LinkedListIterator<>(this);
    }

    public int size() {
        return _size();
    }

    public boolean isEmpty() {
        return _isEmpty();
    }

    public boolean contains(Object o) {
        throw new RuntimeException();
    }

    public Object[] toArray(Object[] dst) {
        int current = 0;
        Node node = sentinel.next;
        while (node != sentinel) {
            dst[current++] = node;
            node = node.next;
        }
        return dst;
    }

    public void clear() {
        _clear();
    }

    public boolean add(T node) {
        return _pushBack(node);
    }

    public boolean equals(Object o) {
        return super.equals(o);
    }

    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException();
    }

    public int hashCode() {
        return super.hashCode();
    }

    public boolean containsAll(Collection<?> c) {
        throw new RuntimeException();
    }

    public boolean addAll(Collection<? extends T> c) {
        throw new RuntimeException();
    }

    public boolean remove(Object o) {
        throw new RuntimeException();
    }

    public boolean removeAll(Collection<?> c) {
        throw new RuntimeException();
    }
}
