package jagex.datastructure;

public class Node {

  public Node previous;
  public Node next;
  public long key;

  public void unlink() {
    if (previous != null) {
      previous.next = next;
      next.previous = previous;
      next = null;
      previous = null;
    }
  }

  public boolean isLinked() {
    return previous != null;
  }
}
