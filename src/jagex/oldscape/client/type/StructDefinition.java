package jagex.oldscape.client.type;

import jagex.datastructure.DoublyLinkedNode;
import jagex.datastructure.Node;
import jagex.datastructure.instrusive.cache.ReferenceCache;
import jagex.datastructure.instrusive.hashtable.IterableNodeTable;
import jagex.jagex3.graphics.Sprite;
import jagex.jagex3.js5.ReferenceTable;
import jagex.messaging.Buffer;

public class StructDefinition extends DoublyLinkedNode {

  public static final ReferenceCache<StructDefinition> cache = new ReferenceCache<>(64);
  public static ReferenceTable table;
  public static Sprite[] skullIconSprites;

  IterableNodeTable<? super Node> data;

  public StructDefinition() {

  }

  public static StructDefinition get(int id) {
    StructDefinition var1 = cache.get(id);
    if (var1 != null) {
      return var1;
    }
    byte[] var2 = table.unpack(34, id);
    var1 = new StructDefinition();
    if (var2 != null) {
      var1.decode(new Buffer(var2));
    }

    var1.onDecodeEnd();
    cache.put(var1, id);
    return var1;
  }

  void decode(Buffer buffer, int opcode) {
    if (opcode == 249) {
      data = IterableNodeTable.decode(buffer, data);
    }

  }

  public String getString(int var1, String var2) {
    return IterableNodeTable.getStringParameter(data, var1, var2);
  }

  public int getInteger(int var1, int var2) {
    return IterableNodeTable.getIntParameter(data, var1, var2);
  }

  public void onDecodeEnd() {
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
