package jag.worldmap;

import jag.audi.AudioSystem;
import jag.audi.AudioSystemProvider;
import jag.game.scene.entity.EntityUID;
import jag.js5.Archive;
import jag.js5.Js5Worker;
import jag.js5.ResourceRequest;

public class WorldMapDecor {
    final int objectId;
    final int decorationId;
    final int rotation;

    WorldMapDecor(int objectId, int decorationId, int rotation) {
        this.objectId = objectId;
        this.decorationId = decorationId;
        this.rotation = rotation;
    }

    public static boolean method379(long var0) {
        return EntityUID.getObjectType(var0) == 2;
    }

    public static void method382(AudioSystemProvider var0) {
        AudioSystem.provider = var0;
    }

    public static int method378(int var0, int var1) {
        int var2 = WorldMapLabelSize.method363(45365 + var0, var1 + 91923, 4) - 128 + (WorldMapLabelSize.method363(10294 + var0, 37821 + var1, 2) - 128 >> 1) + (WorldMapLabelSize.method363(var0, var1, 1) - 128 >> 2);
        var2 = (int) ((double) var2 * 0.3D) + 35;
        if (var2 < 10) {
            var2 = 10;
        } else if (var2 > 60) {
            var2 = 60;
        }

        return var2;
    }

}
