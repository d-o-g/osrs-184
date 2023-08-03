package jag.game.scene.entity;

import jag.commons.collection.Node;
import jag.game.client;
import jag.game.stockmarket.StockmarketEvent;
import jag.graphics.Sprite;

public final class PendingSpawn extends Node {

    public static Sprite mapedge;

    public int startCycle;
    public int endCycle;
    public int floorLevel;
    public int sceneX;
    public int sceneY;
    public int type;
    public int id;
    public int objectType;
    public int orientation;
    public int anInt380;
    public int anInt375;
    public int anInt112;

    public PendingSpawn() {
        startCycle = 0;
        endCycle = -1;
    }

    public static void pushLater(int floorLevel, int sceneX, int sceneY, int stubType, int id, int objectType, int orientation, int startCycle, int endCycle) {
        PendingSpawn var10 = null;

        for (PendingSpawn spawn = client.pendingSpawns.head(); spawn != null; spawn = client.pendingSpawns.next()) {
            if (floorLevel == spawn.floorLevel && spawn.sceneX == sceneX && sceneY == spawn.sceneY && stubType == spawn.type) {
                var10 = spawn;
                break;
            }
        }

        if (var10 == null) {
            var10 = new PendingSpawn();
            var10.floorLevel = floorLevel;
            var10.type = stubType;
            var10.sceneX = sceneX;
            var10.sceneY = sceneY;
            StockmarketEvent.method388(var10);
            client.pendingSpawns.add(var10);
        }

        var10.id = id;
        var10.objectType = objectType;
        var10.orientation = orientation;
        var10.startCycle = startCycle;
        var10.endCycle = endCycle;
    }
}
