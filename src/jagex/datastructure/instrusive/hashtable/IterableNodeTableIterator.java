package jagex.datastructure.instrusive.hashtable;

import jagex.datastructure.Node;

import java.util.Iterator;

public class IterableNodeTableIterator<T extends Node> implements Iterator<T> {

  public final IterableNodeTable<T> table;
  public Node mru;
  public Node head;
  public int ptr;

  public IterableNodeTableIterator(IterableNodeTable<T> table) {
    this.table = table;
    mru = null;
    reset();
  }

  public void reset() {
    head = table.buckets[0].next;
    ptr = 1;
    mru = null;
  }

  public void remove() {
    if (mru == null) {
      throw new IllegalStateException();
    }
    mru.unlink();
    mru = null;
  }

  public T next() {
    Node node;
    if (table.buckets[ptr - 1] != head) {
      node = head;
      head = node.next;
      mru = node;
      return (T) node;
    }

    do {
      if (ptr >= table.size) {
        return null;
      }

      node = table.buckets[ptr++].next;
    } while (node == table.buckets[ptr - 1]);

    head = node.next;
    mru = node;
    return (T) node;
  }

  public boolean hasNext() {
    if (table.buckets[ptr - 1] != head) {
      return true;
    }

    while (ptr < table.size) {
      if (table.buckets[ptr++].next != table.buckets[ptr - 1]) {
        head = table.buckets[ptr - 1].next;
        return true;
      }
      head = table.buckets[ptr - 1];
    }

    return false;
  }
}
