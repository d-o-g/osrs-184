package jag.game.relationship;

import jag.js5.ReferenceTable;
import jag.statics.Statics51;

import java.util.Comparator;

public class Class114 implements Comparator {

    public static String[] cacheDirectories;
    final boolean aBoolean813;

    public Class114(boolean var1) {
        this.aBoolean813 = var1;
    }

    public static void method674(ReferenceTable var0, String var1, String var2, int var3, boolean var4) {
        int var5 = var0.getGroup(var1);
        int var6 = var0.getFile(var5, var2);
        Statics51.method344(var0, var5, var6, var3, var4);
    }

    int method673(Associate var1, Associate var2) {
        return this.aBoolean813 ? var1.index - var2.index : var2.index - var1.index;
    }

    public int compare(Object var1, Object var2) {
        return this.method673((Associate) var1, (Associate) var2);
    }

    public boolean equals(Object var1) {
        return super.equals(var1);
    }
}
