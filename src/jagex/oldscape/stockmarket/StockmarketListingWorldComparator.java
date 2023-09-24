package jagex.oldscape.stockmarket;

import jagex.oldscape.client.InterfaceComponent;
import jagex.oldscape.client.client;
import jagex.oldscape.client.chat.ChatLine;
import jagex.jagex3.js5.ReferenceTable;
import jagex.statics.Statics53;

import java.io.File;
import java.util.Comparator;

public class StockmarketListingWorldComparator implements Comparator {
  public static File cachePathFile;
  public static ReferenceTable aReferenceTable350;
  public static InterfaceComponent anInterfaceComponent351;
  public static int anInt347;
  public boolean aBoolean349;

  public StockmarketListingWorldComparator() {

  }

  public static ChatLine method246(int var0) {
    return Statics53.CHAT_LINE_TABLE.lookup(var0);
  }

  int method247(StockmarketEvent var1, StockmarketEvent var2) {
    if (var2.world == var1.world) {
      return 0;
    }
    if (this.aBoolean349) {
      if (client.currentWorld == var1.world) {
        return -1;
      }

      if (var2.world == client.currentWorld) {
        return 1;
      }
    }

    return var1.world < var2.world ? -1 : 1;
  }

  public int compare(Object var1, Object var2) {
    return this.method247((StockmarketEvent) var1, (StockmarketEvent) var2);
  }

  public boolean equals(Object var1) {
    return super.equals(var1);
  }
}
