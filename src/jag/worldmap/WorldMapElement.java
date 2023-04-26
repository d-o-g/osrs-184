package jag.worldmap;

import jag.game.client;
import jag.graphics.*;

public final class WorldMapElement {

    public static IndexedSprite aDoublyNode_Sub24_Sub4_363;
    final int[] tileColors;

    public WorldMapElement() {
        tileColors = new int[4096];
    }

    public WorldMapElement(int[] tileColors) {
        this.tileColors = tileColors;
    }

    public static boolean method243() {
        return client.aBoolean1037;
    }

    public int getTileColor(int x, int y) {
        return tileColors[x + y * 64];
    }
}
