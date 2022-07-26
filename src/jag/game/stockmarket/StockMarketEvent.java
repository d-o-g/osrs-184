package jag.game.stockmarket;

import jag.game.client;
import jag.game.scene.entity.EntityUID;
import jag.game.scene.entity.PendingSpawn;
import jag.game.type.HitsplatDefinition;
import jag.opcode.Buffer;

public class StockMarketEvent {

    public final StockMarketOffer aStockMarketOffer551;

    public final long age;

    public final int world;

    final String aString549;
    final String aString550;

    StockMarketEvent(Buffer var1, byte var2, int var3) {
        this.aString550 = var1.gstr();
        this.aString549 = var1.gstr();
        this.world = var1.g2();
        this.age = var1.g8();
        int var4 = var1.g4();
        int var5 = var1.g4();
        this.aStockMarketOffer551 = new StockMarketOffer();
        this.aStockMarketOffer551.method228();
        this.aStockMarketOffer551.method227(var2);
        this.aStockMarketOffer551.itemPrice = var4;
        this.aStockMarketOffer551.itemQuantity = var5;
        this.aStockMarketOffer551.transferred = 0;
        this.aStockMarketOffer551.spent = 0;
        this.aStockMarketOffer551.itemId = var3;
    }

    public static void method388(PendingSpawn var0) {
        long var1 = 0L;
        int var3 = -1;
        int var4 = 0;
        int var5 = 0;
        if (var0.type == 0) {
            var1 = client.sceneGraph.getBoundaryUidAt(var0.floorLevel, var0.sceneX, var0.sceneY);
        }

        if (var0.type == 1) {
            var1 = client.sceneGraph.getBoundaryDecorUidAt(var0.floorLevel, var0.sceneX, var0.sceneY);
        }

        if (var0.type == 2) {
            var1 = client.sceneGraph.method1461(var0.floorLevel, var0.sceneX, var0.sceneY);
        }

        if (var0.type == 3) {
            var1 = client.sceneGraph.method1457(var0.floorLevel, var0.sceneX, var0.sceneY);
        }

        if (0L != var1) {
            int var6 = client.sceneGraph.getConfigAt(var0.floorLevel, var0.sceneX, var0.sceneY, var1);
            var3 = EntityUID.getObjectId(var1);
            var4 = var6 & 31;
            var5 = var6 >> 6 & 3;
        }

        var0.anInt380 = var3;
        var0.anInt375 = var4;
        var0.anInt112 = var5;
    }

    public static void clear() {
        HitsplatDefinition.cache.clear();
        HitsplatDefinition.sprites.clear();
        HitsplatDefinition.fonts.clear();
    }

    public String method390() {
        return this.aString550;
    }

    public String method392() {
        return this.aString549;
    }
}
