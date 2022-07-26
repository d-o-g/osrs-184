package jag.game.relationship;

import java.lang.management.GarbageCollectorMXBean;
import java.util.Comparator;

public abstract class AssociateComparator implements Comparator<Associate> {

    public static GarbageCollectorMXBean aGarbageCollectorMXBean1556;

    public Comparator<Associate> delegate;

    protected AssociateComparator() {

    }

    protected final int method1135(Associate c1, Associate c2) {
        return delegate == null ? 0 : delegate.compare(c1, c2);
    }

    public final void set(Comparator<Associate> delegate) {
        if (this.delegate == null) {
            this.delegate = delegate;
        } else if (this.delegate instanceof AssociateComparator) {
            ((AssociateComparator) this.delegate).set(delegate);
        }
    }

    public boolean equals(Object o) {
        return super.equals(o);
    }
}
