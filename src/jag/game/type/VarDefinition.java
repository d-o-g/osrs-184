package jag.game.type;

import jag.commons.collection.DoublyLinkedNode;
import jag.commons.collection.ReferenceCache;
import jag.js5.ReferenceTable;
import jag.opcode.Buffer;

public class VarDefinition extends DoublyLinkedNode {

    public static final ReferenceCache<VarDefinition> cache = new ReferenceCache<>(64);

    public static ReferenceTable table;

    public static int count;

    public int value;

    public VarDefinition() {
        value = 0;
    }

    public static VarDefinition get(int id) {
        VarDefinition var1 = cache.get(id);
        if (var1 != null) {
            return var1;
        }
        byte[] var2 = table.unpack(16, id);
        var1 = new VarDefinition();
        if (var2 != null) {
            var1.decode(new Buffer(var2));
        }

        cache.put(var1, id);
        return var1;
    }

    void decode(Buffer buffer, int opcode) {
        if (opcode == 5) {
            value = buffer.g2();
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
