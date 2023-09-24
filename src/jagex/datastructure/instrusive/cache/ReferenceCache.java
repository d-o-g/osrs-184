package jagex.datastructure.instrusive.cache;

import jagex.datastructure.DoublyLinkedNode;
import jagex.datastructure.instrusive.hashtable.IterableNodeTable;
import jagex.datastructure.instrusive.linklist.IterableDoublyLinkedNodeQueue;

public final class ReferenceCache<T extends DoublyLinkedNode> {

  public final IterableNodeTable<T> table;
  public final IterableDoublyLinkedNodeQueue<T> queue;
  public final int capacity;
  public int remaining;
  public DoublyLinkedNode sentinel;

  public ReferenceCache(int capacity) {
    this.sentinel = new DoublyLinkedNode();
    this.queue = new IterableDoublyLinkedNodeQueue<>();
    this.capacity = capacity;
    this.remaining = capacity;

    int size = 1;
    while (size + size < capacity) {
      size += size;
    }

    this.table = new IterableNodeTable<>(size);
  }

  public T get(long key) {
    T value = table.lookup(key);
    if (value != null) {
      queue.insert(value);
    }
    return value;
  }

  public void put(T value, long key) {
    if (remaining == 0) {
      DoublyLinkedNode top = queue.pop();
      top.unlink();
      top.unlinkDoubly();
      if (top == sentinel) {
        top = queue.pop();
        top.unlink();
        top.unlinkDoubly();
      }
    } else {
      --remaining;
    }

    table.put(value, key);
    queue.insert(value);
  }

  public void clear() {
    queue.clear();
    table.clear();
    sentinel = new DoublyLinkedNode();
    remaining = capacity;
  }

  public void remove(long key) {
    T value = table.lookup(key);
    if (value != null) {
      value.unlink();
      value.unlinkDoubly();
      ++remaining;
    }
  }
}
