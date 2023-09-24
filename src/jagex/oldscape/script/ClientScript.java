package jagex.oldscape.script;

import jagex.datastructure.DoublyLinkedNode;
import jagex.datastructure.Node;
import jagex.datastructure.instrusive.cache.ReferenceCache;
import jagex.datastructure.instrusive.hashtable.IntegerNode;
import jagex.datastructure.instrusive.hashtable.IterableNodeTable;
import jagex.jagex3.js5.Archive;
import jagex.messaging.Buffer;

public class ClientScript extends DoublyLinkedNode {

  public static final ReferenceCache<ClientScript> cache = new ReferenceCache<>(128);

  public int[] opcodes;
  public int[] intOperands;
  public int intLocals;
  public int stringLocals;
  public int intArgs;
  public String[] stringOperands;
  public int stringArgs;
  public IterableNodeTable<? super Node>[] tables;

  public ClientScript() {

  }

  public static ClientScript get(int id) {
    ClientScript script = cache.get((long) id << 16);
    if (script != null) {
      return script;
    }

    String name = String.valueOf(id);
    int group = Archive.cs2.getGroup(name);
    if (group == -1) {
      return null;
    }

    byte[] buffer = Archive.cs2.unpack(group);
    if (buffer != null) {
      if (buffer.length <= 1) {
        return null;
      }

      script = decode(buffer);
      cache.put(script, (long) id << 16);
      return script;
    }

    return null;
  }

  public static ClientScript decode(byte[] data) {
    ClientScript script = new ClientScript();
    Buffer buffer = new Buffer(data);
    buffer.pos = buffer.payload.length - 2;
    int var3 = buffer.g2();
    int var4 = buffer.payload.length - 2 - var3 - 12;
    buffer.pos = var4;
    int var5 = buffer.g4();
    script.intLocals = buffer.g2();
    script.stringLocals = buffer.g2();
    script.intArgs = buffer.g2();
    script.stringArgs = buffer.g2();
    int tables = buffer.g1();
    int var8;
    if (tables > 0) {
      script.tables = script.createTableArray(tables);

      for (int i = 0; i < tables; ++i) {
        var8 = buffer.g2();
        IterableNodeTable<? super Node> table = new IterableNodeTable<>(var8 > 0 ? IterableNodeTable.nextPowerOfTwo(var8) : 1);
        script.tables[i] = table;

        while (var8-- > 0) {
          int var10 = buffer.g4();
          int var11 = buffer.g4();
          table.put(new IntegerNode(var11), var10);
        }
      }
    }

    buffer.pos = 0;
    buffer.fastgstr();
    script.opcodes = new int[var5];
    script.intOperands = new int[var5];
    script.stringOperands = new String[var5];

    for (int i = 0; buffer.pos < var4; script.opcodes[i++] = var8) {
      var8 = buffer.g2();
      if (var8 == 3) {
        script.stringOperands[i] = buffer.gstr();
      } else if (var8 < 100 && var8 != 21 && var8 != 38 && var8 != 39) {
        script.intOperands[i] = buffer.g4();
      } else {
        script.intOperands[i] = buffer.g1();
      }
    }

    return script;
  }

  public static ClientScript decode(int id) {
    ClientScript script = cache.get(id);
    if (script != null) {
      return script;
    }

    byte[] data = Archive.cs2.unpack(id, 0);
    if (data == null) {
      return null;
    }

    script = decode(data);
    cache.put(script, id);
    return script;
  }

  public IterableNodeTable<? super Node>[] createTableArray(int len) {
    return new IterableNodeTable[len];
  }
}
