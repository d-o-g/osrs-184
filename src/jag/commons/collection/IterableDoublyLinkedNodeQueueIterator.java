package jag.commons.collection;

import java.util.Iterator;

public class IterableDoublyLinkedNodeQueueIterator<T extends DoublyLinkedNode> implements Iterator<T> {

    public final IterableDoublyLinkedNodeQueue<T> queue;
    public DoublyLinkedNode head;
    public DoublyLinkedNode mru;

    public IterableDoublyLinkedNodeQueueIterator(IterableDoublyLinkedNodeQueue<T> queue) {
        this.queue = queue;
        head = queue.sentinel.nextDoubly;
        mru = null;
    }

    public void remove() {
        if (mru == null) {
            throw new IllegalStateException();
        }
        mru.unlinkDoubly();
        mru = null;
    }

    public boolean hasNext() {
        return queue.sentinel != head;
    }

    public T next() {
        DoublyLinkedNode node = head;
        if (node == queue.sentinel) {
            node = null;
            head = null;
        } else {
            head = node.nextDoubly;
        }

        mru = node;
        return (T) node;
    }
}
