package jagex.datastructure.wrapper;

import jagex.datastructure.DoublyLinkedNode;

public abstract class LinkedReference<T> extends DoublyLinkedNode {

  public final int size;

  public LinkedReference(int size) {
    this.size = size;
  }

  public abstract boolean isSoft();

  public abstract T getReferent();
}
