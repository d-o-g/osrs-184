package jag.game.type;

import jag.commons.collection.DoublyLinkedNode;
import jag.commons.collection.ReferenceCache;
import jag.js5.ReferenceTable;
import jag.opcode.Buffer;

public class InventoryDefinition extends DoublyLinkedNode {

    public static final ReferenceCache<InventoryDefinition> cache = new ReferenceCache<>(64);

    public static ReferenceTable table;

    public int capacity;

    public InventoryDefinition() {
        capacity = 0;
    }

    public static InventoryDefinition lookup(int key) {
        InventoryDefinition definition = cache.get(key);
        if (definition != null) {
            return definition;
        }

        byte[] buffer = table.unpack(5, key);
        definition = new InventoryDefinition();
        if (buffer != null) {
            definition.decode(new Buffer(buffer));
        }

        cache.put(definition, key);
        return definition;
    }

    public static void setTable(ReferenceTable table) {
        InventoryDefinition.table = table;
    }

    public void decode(Buffer buffer, int opcode) {
        if (opcode == 2) {
            capacity = buffer.g2();
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
