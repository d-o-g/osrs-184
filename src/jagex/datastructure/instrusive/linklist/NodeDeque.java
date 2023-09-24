package jagex.datastructure.instrusive.linklist;

import jagex.datastructure.Node;

public class NodeDeque<T extends Node> {

  public final Node sentinel;
  public Node current;

  public NodeDeque() {
    sentinel = new Node();
    sentinel.next = sentinel;
    sentinel.previous = sentinel;
  }

  public static void replace(Node a, Node b) {
    if (a.previous != null) {
      a.unlink();
    }

    a.previous = b.previous;
    a.next = b;
    a.previous.next = a;
    a.next.previous = a;
  }

  public void insert(T node) {
    if (node.previous != null) {
      node.unlink();
    }

    node.previous = sentinel;
    node.next = sentinel.next;
    node.previous.next = node;
    node.next.previous = node;
  }

  public T head() {
    Node first = sentinel.next;
    if (first == sentinel) {
      current = null;
      return null;
    }
    current = first.next;
    return (T) first;
  }

  public T next() {
    Node next = current;
    if (next == sentinel) {
      current = null;
      return null;
    }
    current = next.next;
    return (T) next;
  }

  public void add(T node) {
    if (node.previous != null) {
      node.unlink();
    }

    node.previous = sentinel.previous;
    node.next = sentinel;
    node.previous.next = node;
    node.next.previous = node;
  }

  public T popFirst() {
    Node node = sentinel.next;
    if (node == sentinel) {
      return null;
    }
    node.unlink();
    return (T) node;
  }

  public T tail() {
    Node node = sentinel.previous;
    if (node == sentinel) {
      current = null;
      return null;
    }
    current = node.previous;
    return (T) node;
  }

  public T popLast() {
    Node node = sentinel.previous;
    if (node == sentinel) {
      return null;
    }
    node.unlink();
    return (T) node;
  }

  public T previous() {
    Node node = current;
    if (node == sentinel) {
      current = null;
      return null;
    }
    current = node.previous;
    return (T) node;
  }

  public void clear() {
    while (true) {
      Node remove = sentinel.next;
      if (remove == sentinel) {
        current = null;
        return;
      }
      remove.unlink();
    }
  }
}
