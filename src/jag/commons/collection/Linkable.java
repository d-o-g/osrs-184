package jag.commons.collection;

public class Linkable {

    public Linkable previous;
    public Linkable next;

    public void unlink() {
        if (previous != null) {
            previous.next = next;
            next.previous = previous;
            next = null;
            previous = null;
        }
    }
}
