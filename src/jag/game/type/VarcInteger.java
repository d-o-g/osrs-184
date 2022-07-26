package jag.game.type;

import jag.commons.collection.DoublyLinkedNode;
import jag.commons.collection.ReferenceCache;
import jag.js5.ReferenceTable;
import jag.opcode.Buffer;

public class VarcInteger extends DoublyLinkedNode {

    public static final ReferenceCache<VarcInteger> cache = new ReferenceCache<>(64);
    public static ReferenceTable table;

    public boolean persists;

    public VarcInteger() {
        persists = false;
    }

    void decode(int opcode) {
        if (opcode == Opcode.PERSIST) {
            persists = true;
        }
    }

    public void decode(Buffer buffer) {
        while (true) {
            int opcode = buffer.g1();
            if (opcode == 0) {
                return;
            }

            decode(opcode);
        }
    }

    public interface Opcode {
        int PERSIST = 2;
    }
}
