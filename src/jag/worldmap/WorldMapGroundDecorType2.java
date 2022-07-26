package jag.worldmap;

import jag.opcode.Connection;

public class WorldMapGroundDecorType2 {

    public static final WorldMapGroundDecorType2 A_WORLD_MAP_GROUND_DECOR_TYPE_2___301 = new WorldMapGroundDecorType2(0);
    public static final WorldMapGroundDecorType2 A_WORLD_MAP_GROUND_DECOR_TYPE_2___302 = new WorldMapGroundDecorType2(1);

    public static Connection aConnection299;

    public final int index;

    WorldMapGroundDecorType2(int index) {
        this.index = index;
    }

    public static int method183(int var0, int var1) {
        int var2;
        for (var2 = 1; var1 > 1; var1 >>= 1) {
            if ((var1 & 1) != 0) {
                var2 = var0 * var2;
            }

            var0 *= var0;
        }

        if (var1 == 1) {
            return var0 * var2;
        }
        return var2;
    }

}
