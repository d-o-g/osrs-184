package jagex.oldscape.stockmarket;

import jagex.oldscape.client.client;
import jagex.oldscape.client.scene.SceneGraph;
import jagex.jagex3.graphics.IndexedSprite;
import jagex.jagex3.graphics.JagGraphics3D;

import java.util.Comparator;

public final class StockmarketListingQuantityComparator implements Comparator<StockmarketEvent> {

  public static IndexedSprite[] aDoublyNode_Sub24_Sub4Array653;

  public static boolean method477(int var0) {
    return (var0 >> 20 & 1) != 0;
  }

  public static void method480(int var0, int var1) {
    int[] var2 = new int[9];

    for (int var3 = 0; var3 < var2.length; ++var3) {
      int var4 = var3 * 32 + 15 + 128;
      int var5 = var4 * 3 + 600;
      int var7 = JagGraphics3D.SIN_TABLE[var4];
      int var8 = var1 - 334;
      if (var8 < 0) {
        var8 = 0;
      } else if (var8 > 100) {
        var8 = 100;
      }

      int var9 = (client.aShort920 - client.aShort913) * var8 / 100 + client.aShort913;
      int var10 = var9 * var5 / 256;
      var2[var3] = var7 * var10 >> 16;
    }

    SceneGraph.defineVisibilityMap(var2, 500, 800, var0 * 334 / var1, 334);
  }

  public static int method476() {
    if (client.archives != null && client.anInt926 < client.archives.size()) {
      int var0 = 0;

      for (int var1 = 0; var1 <= client.anInt926; ++var1) {
        var0 += client.archives.get(var1).validEntryCount;
      }

      return var0 * 10000 / client.archiveEntryCount;
    }
    return 10000;
  }

  int method478(StockmarketEvent var1, StockmarketEvent var2) {
    return Integer.compare(var1.aStockmarketListing551.quantity, var2.aStockmarketListing551.quantity);
  }

  public int compare(StockmarketEvent var1, StockmarketEvent var2) {
    return this.method478(var1, var2);
  }

  public boolean equals(Object var1) {
    return super.equals(var1);
  }
}
