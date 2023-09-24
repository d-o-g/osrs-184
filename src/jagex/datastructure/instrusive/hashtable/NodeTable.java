package jagex.datastructure.instrusive.hashtable;

import jagex.datastructure.Node;

public final class NodeTable<T extends Node> {

  public final Node[] buckets;
  public final int size;
  public int index;
  public Node current;
  public Node query;

  public NodeTable(int capacity) {
    index = 0;
    size = capacity;
    buckets = new Node[capacity];

    for (int i = 0; i < capacity; ++i) {
      Node node = buckets[i] = new Node();
      node.next = node;
      node.previous = node;
    }
  }

  public T head() {
    index = 0;
    return next();
  }

  public T lookup(long key) {
    Node node = buckets[(int) (key & (long) (size - 1))];

    for (query = node.next; node != query; query = query.next) {
      if (query.key == key) {
        Node match = query;
        query = query.next;
        return (T) match;
      }
    }

    query = null;
    return null;
  }

  public T next() {
    Node node;
    if (index > 0 && buckets[index - 1] != current) {
      node = current;
      current = node.next;
      return (T) node;
    }
    do {
      if (index >= size) {
        return null;
      }

      node = buckets[index++].next;
    } while (node == buckets[index - 1]);

    current = node.next;
    return (T) node;
  }

  public void put(T v, long key) {
    if (v.previous != null) {
      v.unlink();
    }

    Node node = buckets[(int) (key & (long) (size - 1))];
    v.previous = node.previous;
    v.next = node;
    v.previous.next = v;
    v.next.previous = v;
    v.key = key;
  }
}
