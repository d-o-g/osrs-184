package jag.commons.collection;

import jag.opcode.Buffer;

import java.util.Iterator;

public final class IterableNodeTable<T extends Node> implements Iterable<T> {

    public final int size;
    public final Node[] buckets;
    public int index;
    public Node tail;
    public Node head;

    public IterableNodeTable(int capacity) {
        index = 0;
        size = capacity;
        buckets = new Node[capacity];

        for (int i = 0; i < capacity; i++) {
            Node bucket = buckets[i] = new Node();
            bucket.next = bucket;
            bucket.previous = bucket;
        }

    }

    public static String getStringParameter(IterableNodeTable<? super Node> table, int key, String defaultValue) {
        if (table == null) {
            return defaultValue;
        }
        ObjectNode node = (ObjectNode) table.lookup(key);
        return node == null ? defaultValue : (String) node.value;
    }

    public static IterableNodeTable<? super Node> decode(Buffer buffer, IterableNodeTable<? super Node> table) {
        int size = buffer.g1();
        if (table == null) {
            int capacity = nextPowerOfTwo(size);
            table = new IterableNodeTable<>(capacity);
        }

        for (int i = 0; i < size; ++i) {
            boolean object = buffer.g1() == 1;
            int key = buffer.g3();
            Node node;
            if (object) {
                node = new ObjectNode(buffer.gstr());
            } else {
                node = new IntegerNode(buffer.g4());
            }

            table.put(node, key);
        }

        return table;
    }

    public static int nextPowerOfTwo(int src) {
        --src;
        src |= src >>> 1;
        src |= src >>> 2;
        src |= src >>> 4;
        src |= src >>> 8;
        src |= src >>> 16;
        return src + 1;
    }

    public static int getIntParameter(IterableNodeTable<? super Node> table, int key, int defaultValue) {
        if (table == null) {
            return defaultValue;
        }
        IntegerNode node = (IntegerNode) table.lookup(key);
        return node == null ? defaultValue : node.value;
    }

    public T lookup(long key) {
        Node node = buckets[(int) (key & (long) (size - 1))];

        for (head = node.next; node != head; head = head.next) {
            if (head.key == key) {
                Node current = head;
                head = head.next;
                return (T) current;
            }
        }

        head = null;
        return null;
    }

    public T next() {
        Node next;
        if (index > 0 && buckets[index - 1] != tail) {
            next = tail;
            tail = next.next;
            return (T) next;
        }

        do {
            if (index >= size) {
                return null;
            }

            next = buckets[index++].next;
        } while (next == buckets[index - 1]);

        tail = next.next;
        return (T) next;
    }

    public void clear() {
        for (int i = 0; i < size; ++i) {
            Node current = buckets[i];
            while (true) {
                Node linked = current.next;
                if (linked == current) {
                    break;
                }

                linked.unlink();
            }
        }

        head = null;
        tail = null;
    }

    public void put(T entry, long key) {
        if (entry.previous != null) {
            entry.unlink();
        }

        Node last = buckets[(int) (key & (long) (size - 1))];
        entry.previous = last.previous;
        entry.next = last;
        entry.previous.next = entry;
        entry.next.previous = entry;
        entry.key = key;
    }

    public Iterator<T> iterator() {
        return new IterableNodeTableIterator<>(this);
    }

    public T first() {
        index = 0;
        return next();
    }
}
