package jagex.oldscape.stockmarket;

import jagex.oldscape.client.client;
import jagex.oldscape.client.scene.entity.EntityUID;
import jagex.oldscape.client.scene.entity.PendingSpawn;
import jagex.oldscape.client.type.HitsplatDefinition;
import jagex.messaging.Buffer;

public class StockmarketEvent {

  public final StockmarketListing aStockmarketListing551;

  public final long age;

  public final int world;

  final String aString549;
  final String aString550;

  StockmarketEvent(Buffer var1, byte var2, int var3) {
    this.aString550 = var1.gstr();
    this.aString549 = var1.gstr();
    this.world = var1.g2();
    this.age = var1.g8();
    int var4 = var1.g4();
    int var5 = var1.g4();
    this.aStockmarketListing551 = new StockmarketListing();
    this.aStockmarketListing551.setInitialState();
    this.aStockmarketListing551.setType(var2);
    this.aStockmarketListing551.unitPrice = var4;
    this.aStockmarketListing551.quantity = var5;
    this.aStockmarketListing551.transferred = 0;
    this.aStockmarketListing551.spent = 0;
    this.aStockmarketListing551.itemId = var3;
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
