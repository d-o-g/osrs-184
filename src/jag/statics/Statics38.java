package jag.statics;

import jag.graphics.IndexedSprite;
import jag.graphics.SpriteProvider;
import jag.js5.ReferenceTable;

public class Statics38 {
    public static IndexedSprite[] method1194(ReferenceTable var0, int var1, int var2) {
        if (!SpriteProvider.loadSprites(var0, var1, var2)) {
            return null;
        }
        IndexedSprite[] var3 = new IndexedSprite[SpriteProvider.count];

        for (int var4 = 0; var4 < SpriteProvider.count; ++var4) {
            IndexedSprite var5 = var3[var4] = new IndexedSprite();
            var5.anInt375 = SpriteProvider.width;
            var5.anInt372 = SpriteProvider.height;
            var5.insetX = SpriteProvider.offsetsX[var4];
            var5.insetY = SpriteProvider.offsetsY[var4];
            var5.anInt378 = SpriteProvider.sizesX[var4];
            var5.anInt377 = SpriteProvider.sizesY[var4];
            var5.palette = SpriteProvider.palette;
            var5.indices = SpriteProvider.pixels[var4];
        }

        SpriteProvider.offsetsX = null;
        SpriteProvider.offsetsY = null;
        SpriteProvider.sizesX = null;
        SpriteProvider.sizesY = null;
        SpriteProvider.palette = null;
        SpriteProvider.pixels = null;
        return var3;
    }
}
