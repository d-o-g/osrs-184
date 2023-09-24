package jagex.datastructure.instrusive.linklist;

import jagex.datastructure.Node;

import java.util.Iterator;

public class LinkedListIterator<T extends Node> implements Iterator<T> {

  public LinkedList<T> list;
  public Node head;
  public Node current;

  public LinkedListIterator(LinkedList<T> list) {
    current = null;
    init(list);
  }

  public void reset() {
    head = list != null ? list.sentinel.next : null;
    current = null;
  }

  public void init(LinkedList<T> list) {
    this.list = list;
    reset();
  }

  public void remove() {
    if (current == null) {
      throw new IllegalStateException();
    }
    current.unlink();
    current = null;
  }

  public T next() {
    Node node = head;
    if (node == list.sentinel) {
      node = null;
      head = null;
    } else {
      head = node.next;
    }

    current = node;
    return (T) node;
  }

  public boolean hasNext() {
    return list.sentinel != head;
  }
}
