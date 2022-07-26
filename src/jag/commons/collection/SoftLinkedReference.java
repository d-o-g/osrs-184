package jag.commons.collection;

import java.lang.ref.SoftReference;

public class SoftLinkedReference<T> extends LinkedReference<T> {

    public final SoftReference<T> referent;

    public SoftLinkedReference(T referent, int size) {
        super(size);
        this.referent = new SoftReference<>(referent);
    }

    public boolean isSoft() {
        return true;
    }

    public T getReferent() {
        return referent.get();
    }
}
