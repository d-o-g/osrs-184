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
    public int lower;
    public int upper;

    public static void set(int var0, int var1) {
        Varbit var2 = cache.get(var0);
        Varbit var3;
        if (var2 == null) {
            byte[] var8 = table.unpack(14, var0);
            var2 = new Varbit();
            if (var8 != null) {
                var2.decode(new Buffer(var8));
            }

            cache.put(var2, var0);
        }
        var3 = var2;

        int var4 = var3.parent;
        int var5 = var3.lower;
        int var6 = var3.upper;
        int var7 = Vars.masks[var6 - var5];
        if (var1 < 0 || var1 > var7) {
            var1 = 0;
        }

        var7 <<= var5;
        Vars.values[var4] = Vars.values[var4] & ~var7 | var1 << var5 & var7;
    }

    public static int get(int id) {
        Varbit bit = cache.get(id);
        if (bit == null) {
            byte[] payload = table.unpack(14, id);
            bit = new Varbit();
            if (payload != null) {
                bit.decode(new Buffer(payload));
            }

            cache.put(bit, id);
        }

        int varp = bit.parent;
        int lower = bit.lower;
        int upper = bit.upper;
        int mask = Vars.masks[upper - lower];
        return Vars.values[varp] >> lower & mask;
    }

    public void decode(Buffer buffer, int opcode) {
        if (opcode == 1) {
            parent = buffer.g2();
            lower = buffer.g1();
            upper = buffer.g1();
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
