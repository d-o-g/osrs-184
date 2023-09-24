package jagex.oldscape.stockmarket;

import jagex.oldscape.client.client;
import jagex.oldscape.client.scene.SceneGraph;

import java.util.Comparator;

public final class StockmarketListingPriceComparator implements Comparator<StockmarketEvent> {

  public static void method330() {
    SceneGraph.lowMemory = false;
    client.lowMemory = false;
  }

  int method332(StockmarketEvent var1, StockmarketEvent var2) {
    return Integer.compare(var1.aStockmarketListing551.unitPrice, var2.aStockmarketListing551.unitPrice);
  }

  public int compare(StockmarketEvent var1, StockmarketEvent var2) {
    return this.method332(var1, var2);
  }

  public boolean equals(Object var1) {
    return super.equals(var1);
  }
}
