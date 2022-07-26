package jag.statics;

import jag.game.Camera;
import jag.game.client;
import jag.game.scene.SceneGraph;
import jag.graphics.ComponentSprite;
import jag.graphics.JagGraphics3D;
import jag.graphics.Sprite;

public class Statics17 {

    public static void method891(int var0, int var1, int xd, int yd, Sprite var4, ComponentSprite var5) {
        if (var4 != null) {
            int angle = Camera.yOffset & 2047;
            int delta = yd * yd + xd * xd;
            if (delta <= 6400) {
                int sin = JagGraphics3D.SIN_TABLE[angle];
                int cos = JagGraphics3D.COS_TABLE[angle];
                int x = cos * xd + yd * sin >> 16;
                int y = yd * cos - sin * xd >> 16;
                if (delta > 2500) {
                    var4.method808(x + var5.anInt380 / 2 - var4.anInt112 / 2, var5.anInt568 / 2 - y - var4.anInt375 / 2, var0, var1, var5.anInt380, var5.anInt568, var5.anIntArray1108, var5.anIntArray749);
                } else {
                    var4.renderAlphaAt(var0 + x + var5.anInt380 / 2 - var4.anInt112 / 2, var5.anInt568 / 2 + var1 - y - var4.anInt375 / 2);
                }

            }
        }
    }

    public static int method892() {
        if (client.preferences.roofsHidden) {
            return SceneGraph.floorLevel;
        }
        int var0 = SceneGraph.getTileHeight(Camera.x, Camera.y, SceneGraph.floorLevel);
        return var0 - Camera.z < 800 && (Statics45.sceneRenderRules[SceneGraph.floorLevel][Camera.x >> 7][Camera.y >> 7] & 4) != 0 ? SceneGraph.floorLevel : 3;
    }
}
