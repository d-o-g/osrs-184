package jag.game.type;

import jag.commons.collection.DoublyLinkedNode;
import jag.commons.collection.ReferenceCache;
import jag.js5.ReferenceTable;
import jag.opcode.Buffer;

public class TileUnderlay extends DoublyLinkedNode {

    public static final ReferenceCache<TileUnderlay> cache = new ReferenceCache<>(64);
    public static ReferenceTable table;

    public int blendSaturation;
    public int blendLightness;
    public int blendHueWeight;
    public int blendHue;
    int rgb;

    public TileUnderlay() {
        rgb = 0;
    }

    public static TileUnderlay get(int id) {
        TileUnderlay var1 = cache.get(id);
        if (var1 != null) {
            return var1;
        }
        byte[] var2 = table.unpack(1, id);
        var1 = new TileUnderlay();
        if (var2 != null) {
            var1.decode(new Buffer(var2));
        }

        var1.updateHsl();
        cache.put(var1, id);
        return var1;
    }

    void updateHsl(int hsl) {
        double var2 = (double) (hsl >> 16 & 255) / 256.0D;
        double var4 = (double) (hsl >> 8 & 255) / 256.0D;
        double var6 = (double) (hsl & 255) / 256.0D;
        double var8 = var2;
        if (var4 < var2) {
            var8 = var4;
        }

        if (var6 < var8) {
            var8 = var6;
        }

        double var10 = var2;
        if (var4 > var2) {
            var10 = var4;
        }

        if (var6 > var10) {
            var10 = var6;
        }

        double var12 = 0.0D;
        double var14 = 0.0D;
        double var16 = (var10 + var8) / 2.0D;
        if (var10 != var8) {
            if (var16 < 0.5D) {
                var14 = (var10 - var8) / (var8 + var10);
            }

            if (var16 >= 0.5D) {
                var14 = (var10 - var8) / (2.0D - var10 - var8);
            }

            if (var10 == var2) {
                var12 = (var4 - var6) / (var10 - var8);
            } else if (var10 == var4) {
                var12 = 2.0D + (var6 - var2) / (var10 - var8);
            } else if (var6 == var10) {
                var12 = 4.0D + (var2 - var4) / (var10 - var8);
            }
        }

        var12 /= 6.0D;
        blendSaturation = (int) (256.0D * var14);
        blendLightness = (int) (var16 * 256.0D);
        if (blendSaturation < 0) {
            blendSaturation = 0;
        } else if (blendSaturation > 255) {
            blendSaturation = 255;
        }

        if (blendLightness < 0) {
            blendLightness = 0;
        } else if (blendLightness > 255) {
            blendLightness = 255;
        }

        if (var16 > 0.5D) {
            blendHueWeight = (int) (512.0D * var14 * (1.0D - var16));
        } else {
            blendHueWeight = (int) (512.0D * var16 * var14);
        }

        if (blendHueWeight < 1) {
            blendHueWeight = 1;
        }

        blendHue = (int) ((double) blendHueWeight * var12);
    }

    void decode(Buffer var1, int var2) {
        if (var2 == 1) {
            rgb = var1.g3();
        }

    }

    public void updateHsl() {
        updateHsl(rgb);
    }

    public void decode(Buffer var1) {
        while (true) {
            int var3 = var1.g1();
            if (var3 == 0) {
                return;
            }

            decode(var1, var3);
        }
    }
}
