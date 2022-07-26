package jag.game.stockmarket;

import jag.ByteBufferProvider;
import jag.game.client;
import jag.game.scene.SceneGraph;
import jag.game.scene.entity.PlayerEntity;
import jag.statics.Statics55;

import java.util.Comparator;

public final class StockMarketOfferPriceComparator implements Comparator<StockMarketEvent> {

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

    public static boolean displayPlayerName(PlayerEntity player) {
        if (client.displayPlayerNames == 0) {
            return false;
        }
        if (PlayerEntity.local != player) {
            boolean var1 = (client.displayPlayerNames & 4) != 0;
            boolean var2 = var1;
            boolean var3;
            if (!var1) {
                var3 = (client.displayPlayerNames & 1) != 0;
                var2 = var3 && player.method609();
            }

            var3 = var2;
            if (!var2) {
                boolean var4 = (client.displayPlayerNames & 2) != 0;
                var3 = var4 && player.method258();
            }

            return var3;
        }
        return Statics55.method446();
    }

    int method332(StockMarketEvent var1, StockMarketEvent var2) {
        return Integer.compare(var1.aStockMarketOffer551.itemPrice, var2.aStockMarketOffer551.itemPrice);
    }

    public int compare(StockMarketEvent var1, StockMarketEvent var2) {
        return this.method332(var1, var2);
    }

    public boolean equals(Object var1) {
        return super.equals(var1);
    }
}
