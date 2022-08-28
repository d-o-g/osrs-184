package jag.worldmap;

import jag.graphics.IndexedSprite;
import jag.graphics.SpriteProvider;
import jag.js5.ReferenceTable;

public final class WorldMapArea {

    final WorldMapController controller;

    int x;
    int width;
    int y;
    int height;

    WorldMapArea(WorldMapController controller) {
        this.controller = controller;
    }

    public static IndexedSprite loadIndexedSprite(ReferenceTable var0, String var1, String var2) {
        int var3 = var0.getGroup(var1);
        int var4 = var0.getFile(var3, var2);
        IndexedSprite var5;
        if (!SpriteProvider.loadSprites(var0, var3, var4)) {
            var5 = null;
        } else {
            IndexedSprite var6 = new IndexedSprite();
            var6.anInt375 = SpriteProvider.width;
            var6.anInt372 = SpriteProvider.height;
            var6.insetX = SpriteProvider.offsetsX[0];
            var6.insetY = SpriteProvider.offsetsY[0];
            var6.anInt378 = SpriteProvider.sizesX[0];
            var6.anInt377 = SpriteProvider.sizesY[0];
            var6.palette = SpriteProvider.palette;
            var6.indices = SpriteProvider.pixels[0];
            SpriteProvider.offsetsX = null;
            SpriteProvider.offsetsY = null;
            SpriteProvider.sizesX = null;
            SpriteProvider.sizesY = null;
            SpriteProvider.palette = null;
            SpriteProvider.pixels = null;
            var5 = var6;
        }

        return var5;
    }
}
