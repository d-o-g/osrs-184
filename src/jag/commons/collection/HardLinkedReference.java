package jag.commons.collection;

public class HardLinkedReference<T> extends LinkedReference<T> {

    public final T element;

    public HardLinkedReference(T element, int size) {
        super(size);
        this.element = element;
    }

    public boolean isSoft() {
        return false;
    }

    public T getReferent() {
        return element;
    }
}
