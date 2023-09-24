package jagex.datastructure.instrusive.linklist;

import jagex.datastructure.DoublyLinkedNode;

import java.util.Iterator;

public class IterableDoublyLinkedNodeQueue<T extends DoublyLinkedNode> implements Iterable<T> {

  public final DoublyLinkedNode sentinel;
  public DoublyLinkedNode current;

  public IterableDoublyLinkedNodeQueue() {
    sentinel = new DoublyLinkedNode();
    sentinel.nextDoubly = sentinel;
    sentinel.previousDoubly = sentinel;
  }

  public static void insertBefore(DoublyLinkedNode n1, DoublyLinkedNode n2) {
    if (n1.previousDoubly != null) {
      n1.unlinkDoubly();
    }

    n1.previousDoubly = n2;
    n1.nextDoubly = n2.nextDoubly;
    n1.previousDoubly.nextDoubly = n1;
    n1.nextDoubly.previousDoubly = n1;
  }

  public T head() {
    DoublyLinkedNode head = sentinel.nextDoubly;

    if (head == sentinel) {
      current = null;
      return null;
    }
    current = head.nextDoubly;
    return (T) head;
  }

  public T first() {
    return head();
  }

  public void clear() {
    while (sentinel.nextDoubly != sentinel) {
      sentinel.nextDoubly.unlinkDoubly();
    }
  }

  public T pop() {
    DoublyLinkedNode node = sentinel.nextDoubly;
    if (node == sentinel) {
      return null;
    }
    node.unlinkDoubly();
    return (T) node;
  }

  public void insert(T node) {
    if (node.previousDoubly != null) {
      node.unlinkDoubly();
    }

    node.previousDoubly = sentinel.previousDoubly;
    node.nextDoubly = sentinel;
    node.previousDoubly.nextDoubly = node;
    node.nextDoubly.previousDoubly = node;
  }

  public T next() {
    DoublyLinkedNode node = current;
    if (node == sentinel) {
      current = null;
      return null;
    }
    current = node.nextDoubly;
    return (T) node;
  }

  public Iterator<T> iterator() {
    return new IterableDoublyLinkedNodeQueueIterator<>(this);
  }
}
