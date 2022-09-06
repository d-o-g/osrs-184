package jag.game.type;

import jag.commons.collection.DoublyLinkedNode;
import jag.commons.collection.ReferenceCache;
import jag.game.Vars;
import jag.js5.ReferenceTable;
import jag.opcode.Buffer;

public class Varbit extends DoublyLinkedNode {

    public static final ReferenceCache<Varbit> cache = new ReferenceCache<>(64);
    public static ReferenceTable table;

    public int parent;
    public int left;
    public int right;

    public static Varbit computeIfAbsent(int index) {
        Varbit varbit = cache.get(index);
        if (varbit == null) {
            byte[] buffer = table.unpack(14, index);
            varbit = new Varbit();
            if (buffer != null) {
                varbit.decode(new Buffer(buffer));
            }

            cache.put(varbit, index);
        }

        return varbit;
    }

    public static void setValue(int index, int value) {
        Varbit varbit = computeIfAbsent(index);
        int varp = varbit.parent;
        int left = varbit.left;
        int right = varbit.right;
        int mask = Vars.masks[right - left];
        if (value < 0 || value > mask) {
            value = 0;
        }

        mask <<= left;
        Vars.values[varp] = Vars.values[varp] & ~mask | value << left & mask;
    }

    public static int getValue(int index) {
        Varbit varbit = computeIfAbsent(index);
        int varp = varbit.parent;
        int left = varbit.left;
        int right = varbit.right;
        int mask = Vars.masks[right - left];
        return Vars.values[varp] >> left & mask;
    }

    public void decode(Buffer buffer, int opcode) {
        if (opcode == 1) {
            parent = buffer.g2();
            left = buffer.g1();
            right = buffer.g1();
        }
    }

    public void decode(Buffer buffer) {
        while (true) {
            int opcode = buffer.g1();
            if (opcode == 0) {
                return;
            }
            decode(buffer, opcode);
        }
    }
}
