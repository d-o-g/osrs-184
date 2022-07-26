package jag.script;

import jag.commons.collection.*;
import jag.js5.Archive;
import jag.opcode.Buffer;

public class ClientScript extends DoublyLinkedNode {

    public static final ReferenceCache<ClientScript> cache = new ReferenceCache<>(128);

    public int[] anIntArray1574;
    public int[] anIntArray749;
    public int anInt574;
    public int anInt112;
    public int anInt375;
    public String[] aStringArray1645;
    public int anInt372;
    public IterableNodeTable<? super Node>[] tables;

    public ClientScript() {
    }

    public static ClientScript get(int id) {
        ClientScript var2 = cache.get(id << 16);
        if (var2 != null) {
            return var2;
        }
        String var3 = String.valueOf(id);
        int var4 = Archive.cs2.getGroup(var3);
        if (var4 == -1) {
            return null;
        }
        byte[] var5 = Archive.cs2.unpack(var4);
        if (var5 != null) {
            if (var5.length <= 1) {
                return null;
            }

            var2 = decode(var5);
            if (var2 != null) {
                cache.put(var2, id << 16);
                return var2;
            }
        }

        return null;
    }

    public static ClientScript decode(byte[] var0) {
        ClientScript var1 = new ClientScript();
        Buffer var2 = new Buffer(var0);
        var2.pos = var2.payload.length - 2;
        int var3 = var2.g2();
        int var4 = var2.payload.length - 2 - var3 - 12;
        var2.pos = var4;
        int var5 = var2.g4();
        var1.anInt574 = var2.g2();
        var1.anInt112 = var2.g2();
        var1.anInt375 = var2.g2();
        var1.anInt372 = var2.g2();
        int var6 = var2.g1();
        int var7;
        int var8;
        if (var6 > 0) {
            var1.tables = var1.method1189(var6);

            for (var7 = 0; var7 < var6; ++var7) {
                var8 = var2.g2();
                IterableNodeTable<? super Node> var9 = new IterableNodeTable<>(var8 > 0 ? IterableNodeTable.nextPowerOfTwo(var8) : 1);
                var1.tables[var7] = var9;

                while (var8-- > 0) {
                    int var10 = var2.g4();
                    int var11 = var2.g4();
                    var9.put(new IntegerNode(var11), var10);
                }
            }
        }

        var2.pos = 0;
        var2.fastgstr();
        var1.anIntArray1574 = new int[var5];
        var1.anIntArray749 = new int[var5];
        var1.aStringArray1645 = new String[var5];

        for (var7 = 0; var2.pos < var4; var1.anIntArray1574[var7++] = var8) {
            var8 = var2.g2();
            if (var8 == 3) {
                var1.aStringArray1645[var7] = var2.gstr();
            } else if (var8 < 100 && var8 != 21 && var8 != 38 && var8 != 39) {
                var1.anIntArray749[var7] = var2.g4();
            } else {
                var1.anIntArray749[var7] = var2.g1();
            }
        }

        return var1;
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

    public IterableNodeTable<? super Node>[] method1189(int var1) {
        return new IterableNodeTable[var1];
    }
}
