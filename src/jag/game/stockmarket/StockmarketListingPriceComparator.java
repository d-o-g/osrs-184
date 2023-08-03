package jag.game.stockmarket;

import jag.ByteBufferProvider;
import jag.game.client;
import jag.game.scene.SceneGraph;

import java.util.Comparator;

public final class StockmarketListingPriceComparator implements Comparator<StockmarketEvent> {

    public static void method330() {
        SceneGraph.lowMemory = false;
        client.lowMemory = false;
    }

    public static byte[] method333(Object var0, boolean var1) {
        if (var0 == null) {
            return null;
        }
        if (var0 instanceof byte[]) {
            byte[] var6 = (byte[]) var0;
            if (var1) {
                int var3 = var6.length;
                byte[] var4 = new byte[var3];
                System.arraycopy(var6, 0, var4, 0, var3);
                return var4;
            }
            return var6;
        }
        if (var0 instanceof ByteBufferProvider) {
            ByteBufferProvider var2 = (ByteBufferProvider) var0;
            return var2.get();
        }
        throw new IllegalArgumentException();
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
