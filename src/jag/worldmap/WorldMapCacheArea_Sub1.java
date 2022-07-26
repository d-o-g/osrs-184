package jag.worldmap;

import jag.opcode.Buffer;

import java.util.HashSet;
import java.util.List;

public class WorldMapCacheArea_Sub1 extends WorldMapCacheArea {

    List<WorldMapIcon> icons;
    HashSet<WorldMapTileDecor> aHashSet291;
    HashSet<WorldMapTileDecor> aHashSet289;

    WorldMapCacheArea_Sub1() {

    }

    public void decode(Buffer var1, boolean var2) {
        this.icons = new java.util.LinkedList<>();
        int var3 = var1.g2();

        for (int var4 = 0; var4 < var3; ++var4) {
            int var5 = var1.method1051();
            WorldMapPosition var6 = new WorldMapPosition(var1.g4());
            boolean var7 = var1.g1() == 1;
            if (var2 || !var7) {
                this.icons.add(new WorldMapLabelIcon(null, var6, var5, null));
            }
        }

    }

    public void decode(Buffer var1, Buffer var2, int var3, boolean var4) {
        this.decode(var1, var3);
        int var5 = var2.g2();
        this.aHashSet291 = new HashSet<>(var5);

        for (int var6 = 0; var6 < var5; ++var6) {
            WorldMapTileDecor_Sub2 var7 = new WorldMapTileDecor_Sub2();

            try {
                var7.method109(var2);
            } catch (IllegalStateException var12) {
                continue;
            }

            this.aHashSet291.add(var7);
        }

        int var6 = var2.g2();
        this.aHashSet289 = new HashSet<>(var6);

        for (int var9 = 0; var9 < var6; ++var9) {
            WorldMapTileDecor_Sub1 var8 = new WorldMapTileDecor_Sub1();

            try {
                var8.method109(var2);
            } catch (IllegalStateException var11) {
                continue;
            }

            this.aHashSet289.add(var8);
        }

        this.decode(var2, var4);
    }
}
