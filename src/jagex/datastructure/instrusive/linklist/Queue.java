package jagex.datastructure.instrusive.linklist;

import jagex.datastructure.DoublyLinkedNode;

public final class Queue<T extends DoublyLinkedNode> {

  public final DoublyLinkedNode root;

  public Queue() {
    root = new DoublyLinkedNode();
    root.nextDoubly = root;
    root.previousDoubly = root;
  }

  public T head() {
    DoublyLinkedNode node = root.nextDoubly;
    return node == root ? null : (T) node;
  }

  public void add(T node) {
    if (node.previousDoubly != null) {
      node.unlinkDoubly();
    }

    node.previousDoubly = root;
    node.nextDoubly = root.nextDoubly;
    node.previousDoubly.nextDoubly = node;
    node.nextDoubly.previousDoubly = node;
  }

  public void insert(T node) {
    if (node.previousDoubly != null) {
      node.unlinkDoubly();
    }

    node.previousDoubly = root.previousDoubly;
    node.nextDoubly = root;
    node.previousDoubly.nextDoubly = node;
    node.nextDoubly.previousDoubly = node;
  }
}
