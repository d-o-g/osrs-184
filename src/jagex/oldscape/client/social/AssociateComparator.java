package jagex.oldscape.client.social;

import java.lang.management.GarbageCollectorMXBean;
import java.util.Comparator;

public abstract class AssociateComparator<T extends Associate<T>> implements Comparator<T> {

  public static GarbageCollectorMXBean aGarbageCollectorMXBean1556;

  public Comparator<T> delegate;

  protected AssociateComparator() {

  }

  protected final int method1135(T c1, T c2) {
    return delegate == null ? 0 : delegate.compare(c1, c2);
  }

  public final void set(Comparator<T> delegate) {
    if (this.delegate == null) {
      this.delegate = delegate;
    } else if (this.delegate instanceof AssociateComparator) {
      ((AssociateComparator<T>) this.delegate).set(delegate);
    }
  }

  public boolean equals(Object o) {
    return super.equals(o);
  }
}
