package jag.statics;

import jag.game.client;
import jag.game.scene.SceneGraph;
import jag.game.scene.entity.EntityUID;
import jag.game.type.ObjectDefinition;
import jag.worldmap.WorldMapFunction;

public class Statics44 {

    public static int[] objectFileIds;

    public static void updateMinimapFloorLevel() {
        if (client.minimapFloorLevel != SceneGraph.floorLevel) {
            client.minimapFloorLevel = SceneGraph.floorLevel;
            int var0 = SceneGraph.floorLevel;
            int[] var1 = SceneGraph.minimapSprite.pixels;
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                var1[var3] = 0;
            }

            for (int var3 = 1; var3 < 103; ++var3) {
                int var4 = (103 - var3) * 2048 + 24628;

                for (int var5 = 1; var5 < 103; ++var5) {
                    if ((SceneGraphRenderData.sceneRenderRules[var0][var5][var3] & 24) == 0) {
                        client.sceneGraph.drawMinimapTile(var1, var4, 512, var0, var5, var3);
                    }

                    if (var0 < 3 && (SceneGraphRenderData.sceneRenderRules[var0 + 1][var5][var3] & 8) != 0) {
                        client.sceneGraph.drawMinimapTile(var1, var4, 512, var0 + 1, var5, var3);

                    }


                    var4 += 4;
                }
            }

            int var3 = (238 + (int) (Math.random() * 20.0D) - 10 << 16) + (238 + (int) (Math.random() * 20.0D) - 10 << 8) + (238 + (int) (Math.random() * 20.0D) - 10);
            int var4 = 238 + (int) (Math.random() * 20.0D) - 10 << 16;
            SceneGraph.minimapSprite.method23();

            for (int var5 = 1; var5 < 103; ++var5) {
                for (int var6 = 1; var6 < 103; ++var6) {
                    if ((SceneGraphRenderData.sceneRenderRules[var0][var6][var5] & 24) == 0) {
                        SceneGraph.drawMinimapObjects(var0, var6, var5, var3, var4);
                    }

                    if (var0 < 3 && (SceneGraphRenderData.sceneRenderRules[var0 + 1][var6][var5] & 8) != 0) {
                        SceneGraph.drawMinimapObjects(var0 + 1, var6, var5, var3, var4);
                    }
                }
            }

            client.anInt895 = 0;

            for (int var5 = 0; var5 < 104; ++var5) {
                for (int var6 = 0; var6 < 104; ++var6) {
                    long var7 = client.sceneGraph.method1457(SceneGraph.floorLevel, var5, var6);
                    if (0L != var7) {
                        int var9 = EntityUID.getObjectId(var7);
                        int var10 = ObjectDefinition.get(var9).mapFunction;
                        if (var10 >= 0) {
                            client.minimapFunctions[client.anInt895] = WorldMapFunction.get(var10).getSprite();
                            client.anIntArray892[client.anInt895] = var5;
                            client.anIntArray894[client.anInt895] = var6;
                            ++client.anInt895;
                        }
                    }
                }
            }

            client.graphicsProvider.method1318();
        }

    }
}
