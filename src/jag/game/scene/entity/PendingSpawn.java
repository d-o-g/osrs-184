package jag.game.scene.entity;

import jag.commons.collection.Node;
import jag.game.client;
import jag.game.stockmarket.StockMarketEvent;
import jag.graphics.Sprite;

public final class PendingSpawn extends Node {

    public static Sprite mapedge;

    public int delay;
    public int hitpoints;
    public int floorLevel;
    public int sceneX;
    public int sceneY;
    public int type;
    public int anInt693;
    public int anInt564;
    public int orientation;
    public int anInt380;
    public int anInt375;
    public int anInt112;

    public PendingSpawn() {
        delay = 0;
        hitpoints = -1;
    }

    public static void pushLater(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
        PendingSpawn var10 = null;

        for (PendingSpawn spawn = client.pendingSpawns.head(); spawn != null; spawn = client.pendingSpawns.next()) {
            if (var0 == spawn.floorLevel && spawn.sceneX == var1 && var2 == spawn.sceneY && var3 == spawn.type) {
                var10 = spawn;
                break;
            }
        }

        if (var10 == null) {
            var10 = new PendingSpawn();
            var10.floorLevel = var0;
            var10.type = var3;
            var10.sceneX = var1;
            var10.sceneY = var2;
            StockMarketEvent.method388(var10);
            client.pendingSpawns.add(var10);
        }

        var10.anInt693 = var4;
        var10.anInt564 = var5;
        var10.orientation = var6;
        var10.delay = var7;
        var10.hitpoints = var8;
    }
}
