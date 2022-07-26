package jag.commons.collection;

public class DoublyLinkedNode extends Node {

    public DoublyLinkedNode previousDoubly;
    public DoublyLinkedNode nextDoubly;
    public long doublyKey;

    public void unlinkDoubly() {
        if (previousDoubly != null) {
            previousDoubly.nextDoubly = nextDoubly;
            nextDoubly.previousDoubly = previousDoubly;
            nextDoubly = null;
            previousDoubly = null;
        }
    }
}
