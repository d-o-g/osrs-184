package jag.statics;

import jag.audi.PcmStream_Sub3;
import jag.game.Server;
import jag.graphics.Font;
import jag.graphics.SpriteProvider;
import jag.worldmap.WorldMapTileDecor;

import javax.imageio.ImageIO;

public class Statics50 {
    public static PcmStream_Sub3 aClass5_Sub6_Sub3_326;

    static {
        ImageIO.setUseCache(false);
    }

    public static Font method221(byte[] var0) {
        if (var0 == null) {
            return null;
        }
        Font var1 = new Font(var0, SpriteProvider.offsetsX, SpriteProvider.offsetsY, SpriteProvider.sizesX, SpriteProvider.sizesY, SpriteProvider.pixels);
        SpriteProvider.offsetsX = null;
        SpriteProvider.offsetsY = null;
        SpriteProvider.sizesX = null;
        SpriteProvider.sizesY = null;
        SpriteProvider.palette = null;
        SpriteProvider.pixels = null;
        return var1;
    }

    public static void method220(int var0, boolean var1, int var2, boolean var3) {
        if (Server.servers != null) {
            WorldMapTileDecor.method357(0, Server.servers.length - 1, var0, var1, var2, var3);
        }

    }
}
