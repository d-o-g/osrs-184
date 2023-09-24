package jagex.datastructure.instrusive.linklist;

import jagex.datastructure.Linkable;

public class LinkableDeque<T extends Linkable> {

  public final Linkable sentinel;
  public Linkable current;

  public LinkableDeque() {
    sentinel = new Linkable();
    sentinel.next = sentinel;
    sentinel.previous = sentinel;
  }

  public T current() {
    Linkable linkable = sentinel.next;
    if (linkable == sentinel) {
      current = null;
      return null;
    }
    current = linkable.next;
    return (T) linkable;
  }

  public T next() {
    Linkable linkable = current;
    if (linkable == sentinel) {
      current = null;
      return null;
    }
    current = linkable.next;
    return (T) linkable;
  }

  public void insert(T linkable) {
    if (linkable.previous != null) {
      linkable.unlink();
    }

    linkable.previous = this.sentinel.previous;
    linkable.next = this.sentinel;
    linkable.previous.next = linkable;
    linkable.next.previous = linkable;
  }
}
