package jag.game.stockmarket;

import jag.game.type.Varbit;
import jag.js5.ReferenceTable;

import java.util.Comparator;

public final class StockmarketListingNameComparator implements Comparator<StockmarketEvent> {

    public static ReferenceTable aReferenceTable480;

    public static void method327(ReferenceTable var0) {
        Varbit.table = var0;
    }

    public static void method328(String[] var0, short[] var1, int var2, int var3) {
        if (var2 < var3) {
            int var4 = (var3 + var2) / 2;
            int var5 = var2;
            String var6 = var0[var4];
            var0[var4] = var0[var3];
            var0[var3] = var6;
            short var7 = var1[var4];
            var1[var4] = var1[var3];
            var1[var3] = var7;

            for (int var8 = var2; var8 < var3; ++var8) {
                if (var6 == null || var0[var8] != null && var0[var8].compareTo(var6) < (var8 & 1)) {
                    String var9 = var0[var8];
                    var0[var8] = var0[var5];
                    var0[var5] = var9;
                    short var10 = var1[var8];
                    var1[var8] = var1[var5];
                    var1[var5++] = var10;
                }
            }

            var0[var3] = var0[var5];
            var0[var5] = var6;
            var1[var3] = var1[var5];
            var1[var5] = var7;
            method328(var0, var1, var2, var5 - 1);
            method328(var0, var1, var5 + 1, var3);
        }

    }

    int method326(StockmarketEvent var1, StockmarketEvent var2) {
        return var1.method390().compareTo(var2.method390());
    }

    public int compare(StockmarketEvent var1, StockmarketEvent var2) {
        return this.method326(var1, var2);
    }

    public boolean equals(Object var1) {
        return super.equals(var1);
    }
}
